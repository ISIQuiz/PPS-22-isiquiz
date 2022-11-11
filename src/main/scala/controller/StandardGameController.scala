package controller

import controller.Controller.{AppController, PageController}
import model.Answer.Answer
import model.GameStage.{GameStage, GameStageImpl}
import model.{QuizInGame, SavedCourse}
import model.Quiz.Quiz

trait GameController:
  def nextQuiz(): QuizInGame
  def chooseQuiz(course: SavedCourse): Quiz
  def chooseAnswers(quiz: Quiz): List[Answer]
  def endQuiz(): Unit

/** Companion object of standard game controller */
object StandardGameController:

  enum AvailableActions extends Enumeration:
    case Back
    case SelectAnswer

/** Defines the logic of the select page */
class StandardGameController extends PageController, GameController:

  import AppController.AvailablePages
  import StandardGameController.*

  val gameStage: GameStage = new GameStageImpl

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit =
    nextQuiz()
    updateUI(Option(gameStage))

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back
    case AvailableActions.SelectAnswer => selectAnswer(value.get.toString.toInt)

  def back: Unit = AppController.handle(AvailablePages.SelectMenu, Option.empty)

  def selectAnswer(index: Int): Unit =
    if gameStage.quizInGame.answers(index - 1).isCorrect then
      println("Risposta GIUSTA!")
    else
      println("Risposta SBAGLIATA!")
//    println(getCorrectIndex(gameStage.quizInGame.answers))
//    println(value.get.toString + " | " + getCorrect(gameStage.quizInGame.answers))

  override def nextQuiz(): QuizInGame = ???

  override def chooseQuiz(course: SavedCourse): Quiz = ???

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val allCorrectAnswers = gameStage.quizInGame.quiz.answerList.filter(answer => answer.isCorrect)
    val allWrongAnswers = gameStage.quizInGame.quiz.answerList.filter(answer => !answer.isCorrect)

    val correctAnswers = randomNumberGenerator(1, allCorrectAnswers.size).map(allCorrectAnswers)
    val wrongAnswers = randomNumberGenerator(3, allWrongAnswers.size).map(allWrongAnswers)

    scala.util.Random.shuffle(correctAnswers ::: wrongAnswers)

  override def endQuiz(): Unit = ???

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] = scala.util.Random.shuffle(0 to range).take(quantity).toList