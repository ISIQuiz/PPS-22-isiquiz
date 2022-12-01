package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.StandardGameMenuView.{AnswerFeedbackUpdate, DefaultUpdate, NewQuizUpdate}
import model.Answer.Answer
import model.GameStage
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz
import model.settings.StandardGameSettings
import utils.{TerminalInput, TerminalInputImpl, Timer, TimerImpl}
import view.terminalUI.TerminalStandardGameMenu
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of standard game controller */
object StandardGameController extends BackAction:

  case object TimeExpired extends ParameterlessAction
  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  case object NextQuiz extends ParameterlessAction

  def apply(game: GameStage): StandardGameController =
    val standardGameController = new StandardGameController(game)
    standardGameController.nextQuiz()
    standardGameController

/** Defines the logic of the select page */
class StandardGameController(val game: GameStage) extends PageController, GameController:

  import StandardGameController.*

  val gameStage: GameStage = game
  val timer: Timer = TimerImpl(gameStage.gameSettings.asInstanceOf[StandardGameSettings].quizMaxTime)

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case TimeExpired => println("Time expired")
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)
    case NextQuiz => nextQuiz()

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
    AppController.currentPage.pageView.updateUI(NewQuizUpdate(Option(gameStage)))
//    timer.startTimer()
//    timer.stopTimer()

  def selectAnswer[T](actionParameter: Option[T]): Unit =
    if actionParameter.isDefined then
      val selectedAnswerIndex: Int = actionParameter.get.asInstanceOf[Int]
      if gameStage.quizInGame.answers(selectedAnswerIndex).isCorrect then
        println("Risposta GIUSTA!")
      else
        println("Risposta SBAGLIATA!")
      AppController.currentPage.pageView.updateUI(AnswerFeedbackUpdate(Option((selectedAnswerIndex, gameStage.quizInGame.answers(selectedAnswerIndex).isCorrect))))

  override def nextQuiz(): QuizInGame =
    val selectedCourse = gameStage.coursesInGame(randomNumberGenerator(1, gameStage.coursesInGame.size).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    val quizInGame = QuizInGame.apply(selectedCourse, selectedQuiz, selectedAnswers)
    gameStage.quizInGame_(quizInGame)
    quizInGame

  override def chooseQuiz(course: SavedCourse): Quiz = course.quizList(randomNumberGenerator(1, course.quizList.size).head)

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)

  override def endQuiz(): Unit = ???

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 to range-1).take(quantity).toList