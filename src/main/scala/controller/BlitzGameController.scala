package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.Answer.Answer
import model.Quiz.Quiz
import model.settings.BlitzGameSettings
import model.*
import model.SavedCourse.SavedCourse
import utils.{TerminalInput, TerminalInputImpl, Timer}
import view.StandardGameMenuView.*
import view.View
import view.terminalUI.TerminalStandardGameMenu
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

/** Companion object of blitz game controller */
object BlitzGameController extends BackAction:

  /** action to select an answer in the controller of a blitz game */
  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)

  /** action to go to the next quiz in the controller of a blitz game */
  case object NextQuiz extends ParameterlessAction

  def apply(game: GameStage): BlitzGameController =
    game.gameSettings = BlitzGameSettings()
    val blitzGameController = new BlitzGameController(game)
    blitzGameController.gameStage.quizInGame = blitzGameController.extractQuizInGame(game)
    blitzGameController.timer.startTimer()
    blitzGameController

  def isGameStagePlayable(gameStage: GameStage): Boolean = gameStage.coursesInGame.nonEmpty && gameStage.coursesInGame.forall(course =>
    course.quizList.nonEmpty && course.quizList.forall(quiz =>
      quiz.answerList.exists(_.isCorrect) && quiz.answerList.count(!_.isCorrect) >= 3))

/** Defines the logic of the select page */
class BlitzGameController(val game: GameStage) extends PageController, GameController:

  import BlitzGameController.*

  val gameStage: GameStage = game
  val timer: Timer = Timer.apply(gameStage.gameSettings.asInstanceOf[BlitzGameSettings].maxTime)
  var currentAnswer: Option[Answer] = None
  var currentScore: Int = 0
  var quizTime: Double = 0

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)
    case NextQuiz => nextQuiz()

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if timer.isExpired then
      endGame(gameStage)
    else
      sendUpdate(CurrentGameUpdate(Option(gameStage)))
      sendUpdate(TimerUpdate(Option(timer)))

  def selectAnswer[T](actionParameter: Option[T]): Unit =
    if actionParameter.isDefined && !timer.isExpired then
      actionParameter match
        case Some(selectedAnswerIndex: Int) =>
          currentAnswer = Some(gameStage.quizInGame.answers(selectedAnswerIndex))
        case Some(selectedAnswer: Answer) =>
          currentAnswer = Some(selectedAnswer)
        case _ => throw IllegalArgumentException()
      nextQuiz()

  override def nextQuiz(): Unit =
    var currentTime = timer.getTime - quizTime
    if (currentTime < 0) currentTime = 0
    quizTime = timer.getTime
    currentAnswer match
      case Some(ans) =>
        gameStage.addReviewQuizAnswer(ans, gameStage.quizInGame.quiz.maxScore, currentTime)
        gameStage.addQuizToStats(ans.isCorrect, gameStage.quizInGame.quiz.maxScore, currentTime)
        println
      case _ =>
        gameStage.addReviewQuizNotAnswered()
        gameStage.addQuizToStats(false, 0, 0)
    currentAnswer = None
    gameStage.quizInGame = extractQuizInGame(gameStage)
