package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.StandardGameMenuView.{AnswerFeedbackUpdate, DefaultUpdate, NewQuizUpdate, TimeExpiredUpdate, TimerUpdate}
import model.Answer.Answer
import model.GameStage
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz
import model.settings.StandardGameSettings
import utils.{TerminalInput, TerminalInputImpl, Timer}
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
    standardGameController.timer.startTimer()
    standardGameController

/** Defines the logic of the select page */
class StandardGameController(val game: GameStage) extends PageController, GameController:

  import StandardGameController.*

  val gameStage: GameStage = game
  val timer: Timer = Timer.apply(gameStage.gameSettings.asInstanceOf[StandardGameSettings].quizMaxTime)

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case TimeExpired =>
      // TODO: Remove this action since timer expired is not an action but a condition checked in every iteration
      println("Time expired")
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)
    case NextQuiz => nextQuiz()

  override def nextIteration(): Unit =
    // TODO: Improve update sending to view page
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
    if timer.isExpired then
      AppController.currentPage.pageView.updateUI(TimeExpiredUpdate)
    else
      AppController.currentPage.pageView.updateUI(NewQuizUpdate(Option(gameStage)))
      if !timer.isStopped then AppController.currentPage.pageView.updateUI(TimerUpdate(Option(timer)))

  def selectAnswer[T](actionParameter: Option[T]): Unit =
    timer.stopTimer()
    if actionParameter.isDefined then
      val selectedAnswerIndex: Int = actionParameter.get.asInstanceOf[Int]
      AppController.currentPage.pageView.updateUI(AnswerFeedbackUpdate(Option((selectedAnswerIndex, gameStage.quizInGame.answers(selectedAnswerIndex).isCorrect))))

  override def nextQuiz(): QuizInGame =
    val selectedCourse = gameStage.coursesInGame(randomNumberGenerator(1, gameStage.coursesInGame.size).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    val quizInGame = QuizInGame.apply(selectedCourse, selectedQuiz, selectedAnswers)
    gameStage.quizInGame_(quizInGame)
    timer.startTimer()
    quizInGame

  override def chooseQuiz(course: SavedCourse): Quiz = course.quizList(randomNumberGenerator(1, course.quizList.size).head)

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)

  override def endQuiz(): Unit = ???

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 until range).take(quantity).toList