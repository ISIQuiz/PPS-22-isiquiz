package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.StandardGameMenuView.{AnswerFeedbackUpdate, CurrentGameUpdate, DefaultUpdate, QuizScoreUpdate, TimeExpiredUpdate, TimerUpdate}
import model.Answer.Answer
import model.GameStage
import model.Review
import model.QuizAnswered
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz
import model.settings.StandardGameSettings
import utils.{TerminalInput, TerminalInputImpl, Timer}
import view.terminalUI.TerminalStandardGameMenu

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of standard game controller */
object StandardGameController extends BackAction :
  /** action to select an answer in the controller of a standard game */
  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  /** action to go to the next quiz in the controller of a standard game */
  case object NextQuiz extends ParameterlessAction

  def apply(game: GameStage): StandardGameController =
    val standardGameController = new StandardGameController(game)
    standardGameController.extractQuizInGame()
    standardGameController.timer.startTimer()
    standardGameController

/** Defines the logic of the select page */
class StandardGameController(val game: GameStage) extends PageController, GameController :

  import StandardGameController.*

  val gameStage: GameStage = game
  val timer: Timer = Timer.apply(gameStage.gameSettings.asInstanceOf[StandardGameSettings].quizMaxTime)
  var currentAnswer: Option[Answer] = None
  var currentScore: Int = 0

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)
    case NextQuiz => nextQuiz()

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if timer.isExpired then
      timer.stopTimer()
      sendUpdate(TimeExpiredUpdate)
    else
      sendUpdate(CurrentGameUpdate(Option(gameStage)))
      if !timer.isStopped then
        sendUpdate(TimerUpdate(Option(timer)))
        currentScore = updateScore(timer.getRemainingTime.toInt, timer.maxTime, gameStage.quizInGame.quiz.maxScore) // update score
        sendUpdate(QuizScoreUpdate(Option(currentScore)))


  def selectAnswer[T](actionParameter: Option[T]): Unit =
    timer.stopTimer()
    if actionParameter.isDefined && !timer.isExpired then
      actionParameter match
        case Some(selectedAnswerIndex:Int) =>
          currentAnswer = Some(gameStage.quizInGame.answers(selectedAnswerIndex))
        case Some(selectedAnswer: Answer) =>
          currentAnswer = Some(selectedAnswer)
        case _ => throw IllegalArgumentException()
      sendUpdate(AnswerFeedbackUpdate(Option((gameStage.quizInGame.answers.indexOf(currentAnswer.get), currentAnswer.get.isCorrect))))

  override def nextQuiz(): QuizInGame =
    currentAnswer match
      case Some(ans) => gameStage.addReviewQuizAnswer(ans)
      case _ => gameStage.addReviewQuizNotAnswered
      // add playerstats
    if gameStage.maxQuizzesReached then endGame()
    currentAnswer = None
    timer.startTimer()
    extractQuizInGame()


  def extractQuizInGame(): QuizInGame =
    val selectedCourse = gameStage.coursesInGame(randomNumberGenerator(1, gameStage.coursesInGame.size).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    val quizInGame = QuizInGame(selectedCourse, selectedQuiz, selectedAnswers)
    gameStage.quizInGame_(quizInGame)
    quizInGame

  override def chooseQuiz(course: SavedCourse): Quiz = course.quizList(randomNumberGenerator(1, course.quizList.size).head)

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)

  def updateScore(timeRemaining: Int, maxTime: Long, maxScore: Int): Int = {
    val coeff: Double = maxScore.toDouble / (maxTime / 1000).toDouble
    val score = (coeff * timeRemaining).toInt
    if (score >= maxScore) score else score + 1
  }

  override def endGame(): Unit = AppController.handle(ReviewMenuAction(Option(gameStage)))
  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 until range).take(quantity).toList