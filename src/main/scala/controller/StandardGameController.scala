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

  override def nextQuiz(): QuizInGame =
    val selectedCourse = gameStage.courseInGame(randomNumberGenerator(1, gameStage.courseInGame.length).head)
    val selectedQuiz = chooseQuiz(selectedCourse)
    val selectedAnswers = chooseAnswers(selectedQuiz)
    QuizInGame.apply(selectedCourse, selectedQuiz, selectedAnswers)

  override def chooseQuiz(course: SavedCourse): Quiz = ???

  override def chooseAnswers(quiz: Quiz): List[Answer] =
    val r = scala.util.Random

    //val correctAnswers = gameStage.quizInGame.quiz.answerList.filter(giuste)
    //val wrongAnswers = gameStage.quizInGame.quiz.answerList.filter(sbagliate)
    //val oneCorrectAnswer = gameStage.quizInGame.quiz.answerList(r.nextInt(countAnswers(correctAnswers)))

    //val randomIndexes = randomNumberGenerator(3, countAnswers(wrongAnswers))
    //val answers: AnswerList = for i <- 0 until randomIndexes.size yield AnswerList.addAnswer(wrongAnswers(randomIndexes(i)))

    ???

  override def endQuiz(): Unit = ???

  def randomNumberGenerator(quantity: Int, range: Int): List[Int] =
    val r = scala.util.Random
    val randomNumbers = for i <- 0 until quantity yield r.nextInt(range)
    checkNumbersAreDifferent(randomNumbers.toList)
    randomNumbers.toList

  def checkNumbersAreDifferent(numbers: List[Int]): Boolean = if numbers.size == numbers.distinct.size then true else false
