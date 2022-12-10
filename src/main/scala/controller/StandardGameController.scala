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
import model.stats.PlayerStats
import utils.storage.ExportHandler
import utils.{TerminalInput, TerminalInputImpl, Timer}
import view.terminalUI.TerminalStandardGameMenu

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of standard game controller */
object StandardGameController extends BackAction:

  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)

  case object NextQuiz extends ParameterlessAction

  def apply(game: GameStage): StandardGameController =
    val standardGameController = new StandardGameController(game)
    standardGameController.extractQuizInGame()
    standardGameController.timer.startTimer()
    standardGameController

/** Defines the logic of the select page */
class StandardGameController(val game: GameStage) extends PageController, GameController:

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
        currentScore = updateScore(timer.getRemainingTime.toInt, timer.maxTime, gameStage.quizInGame.quiz.maxScore)
        sendUpdate(QuizScoreUpdate(Option(currentScore)))


  def selectAnswer[T](actionParameter: Option[T]): Unit =
    currentTime = timer.getTime
    timer.stopTimer()
    if actionParameter.isDefined && !timer.isExpired then
      val selectedAnswerIndex: Int = actionParameter.get.asInstanceOf[Int]
      val answerSelected: Answer = gameStage.quizInGame.answers(selectedAnswerIndex)
      currentAnswer = Some(answerSelected)
      sendUpdate(AnswerFeedbackUpdate(Option((selectedAnswerIndex, answerSelected.isCorrect))))

  override def nextQuiz(): QuizInGame =
    val maxTime = timer.maxTime/1000
    currentAnswer match
      case Some(ans) =>
        if (!ans.isCorrect)
          currentScore = 0
          currentTime = maxTime
        gameStage.addReviewQuizAnswer(ans, currentScore, currentTime)
        gameStage.addQuizToStats(ans.isCorrect, currentScore, currentTime)

      case _ =>
        gameStage.addReviewQuizNotAnswered()
        gameStage.addQuizToStats(false, 0, maxTime)

    if gameStage.maxQuizzesReached then endGame()
    currentAnswer = None
    currentScore = 0
    currentTime = 0
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

  // update the game score based on time remaining
  private def updateScore(timeRemaining: Int, maxTime: Long, maxScore: Int): Int = {
    val coeff: Double = maxScore.toDouble / (maxTime / 1000).toDouble
    val score = (coeff * timeRemaining).toInt
    if (score >= maxScore) score else score + 1
  }

  override def endGame(): Unit =
    // Update session player stats and export to personal stats
    AppController.changePlayerStats(PlayerStats.mergePlayerStats(AppController.session.playerStats, gameStage.playerStatsInGame))
    ExportHandler.exportDataToPersonalDirectory(AppController.session.playerStats)

    AppController.handle(ReviewMenuAction(Option(gameStage)))

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 until range).take(quantity).toList