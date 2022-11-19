package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.{StandardGameView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import model.Answer.Answer
import model.GameStage
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz
import model.settings.StandardGameSettings

trait GameController:
  def nextQuiz(): QuizInGame
  def chooseQuiz(course: SavedCourse): Quiz
  def chooseAnswers(quiz: Quiz): List[Answer]
  def endQuiz(): Unit

/** Companion object of standard game controller */
object StandardGameController:

  case object Back extends ParameterlessAction
  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the select page */
class StandardGameController(val gameStage: GameStage) extends PageController, GameController:

  import StandardGameController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)

  override def nextIteration(): Unit =
//    nextQuiz()
    updateUI(StandardGameView.DefaultUpdate)
    updateUI(StandardGameView.NewQuizUpdate(Option(gameStage)))
    AppController.currentPage.pageView.handleInput()

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)

  def selectAnswer[T](actionParameter: Option[T]): Unit =
    ???
    // TODO: Fix selected answer check
//    if actionParameter.get.toString.toInt -1 == getCorrectIndex(gameStage.quizInGame.answers) then
//      updateUI(ViewUpdate(StandardGameView.UpdateType.AnswerFeedback, Option("Giusta")))
//      nextIteration()
//      println("Risposta GIUSTA!")
//    else
//      updateUI(ViewUpdate(StandardGameView.UpdateType.AnswerFeedback, Option("Sbagliata")))
//      println("Risposta SBAGLIATA!")
//    println(getCorrectIndex(gameStage.quizInGame.answers))
//    println(value.get.toString + " | " + getCorrect(gameStage.quizInGame.answers))

  override def nextQuiz(): QuizInGame =
    val selectedCourse = gameStage.coursesInGame(randomNumberGenerator(1, gameStage.coursesInGame.size).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    QuizInGame.apply(selectedCourse, selectedQuiz, selectedAnswers)

  override def chooseQuiz(course: SavedCourse): Quiz = course.quizList(randomNumberGenerator(1, course.quizList.size).head)

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = gameStage.quizInGame.quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = gameStage.quizInGame.quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)

  override def endQuiz(): Unit = ???

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 to range-1).take(quantity).toList