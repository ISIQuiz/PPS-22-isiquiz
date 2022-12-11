package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.StandardGameMenuView.{AnswerFeedbackUpdate, CurrentGameUpdate, DefaultUpdate, QuizScoreUpdate, TimeExpiredUpdate, TimerUpdate}
import model.Answer.Answer
import model.GameStage
import model.QuizInGame
import model.SavedCourse.SavedCourse
import model.Review
import model.QuizAnswered
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz
import model.settings.StandardGameSettings
import model.stats.PlayerStats
import utils.storage.ExportHandler
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
    standardGameController.gameStage.quizInGame = standardGameController.extractQuizInGame(game)
    standardGameController.timer.startTimer()
    standardGameController

  def isGameStagePlayable(gameStage: GameStage): Boolean = gameStage.coursesInGame.nonEmpty && gameStage.coursesInGame.forall( course =>
    course.quizList.nonEmpty && course.quizList.forall(quiz => 
    quiz.answerList.exists(_.isCorrect) && quiz.answerList.count(!_.isCorrect)>=3))

/** Defines the logic of the select page */
class StandardGameController(val game: GameStage) extends PageController, GameController :

  import StandardGameController.*

  val gameStage: GameStage = game
  val timer: Timer = Timer.apply(gameStage.gameSettings.asInstanceOf[StandardGameSettings].quizMaxTime)
  var currentAnswer: Option[Answer] = None
  var currentScore: Int = 0
  var currentTime: Double = 0

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
        // Update actual quiz in game score
        currentScore = updateScore(timer.getRemainingTime.toInt, timer.maxTimeInSeconds, gameStage.quizInGame.quiz.maxScore)
        sendUpdate(QuizScoreUpdate(Option(currentScore)))


  def selectAnswer[T](actionParameter: Option[T]): Unit =
    currentTime = BigDecimal(timer.getTime).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    timer.stopTimer()
    if actionParameter.isDefined && !timer.isExpired then
      actionParameter match
        case Some(selectedAnswerIndex: Int) =>
          currentAnswer = Some(gameStage.quizInGame.answers(selectedAnswerIndex))
        case Some(selectedAnswer: Answer) =>
          currentAnswer = Some(selectedAnswer)
        case _ => throw IllegalArgumentException()
      sendUpdate(AnswerFeedbackUpdate(Option((gameStage.quizInGame.answers.indexOf(currentAnswer.get), currentAnswer.get.isCorrect))))

  override def nextQuiz(): Unit =
    val maxTime = timer.maxTimeInSeconds
    currentAnswer match
      case Some(ans) =>
        if (!ans.isCorrect)
          currentScore = 0
          currentTime = 0
        gameStage.addReviewQuizAnswer(ans, currentScore, currentTime)
        gameStage.addQuizToStats(ans.isCorrect, currentScore, currentTime)
      case _ =>
        gameStage.addReviewQuizNotAnswered()
        gameStage.addQuizToStats(false, 0, 0)

    if gameStage.maxQuizzesReached then endGame(gameStage)
    currentAnswer = None
    currentScore = 0
    currentTime = 0
    timer.startTimer()
    gameStage.quizInGame = extractQuizInGame(gameStage)

  // update the game score based on time remaining
  private def updateScore(timeRemaining: Int, maxTimeInSeconds: Long, maxScore: Int): Int = {
    val coeff: Double = maxScore.toDouble / maxTimeInSeconds.toDouble
    val score = (coeff * timeRemaining).toInt
    if (score >= maxScore) score else score + 1
  }
