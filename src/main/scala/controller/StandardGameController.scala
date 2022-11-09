package controller

import controller.Controller.{AppController, PageController}
import model.GameStage.{GameStage, GameStageImpl}
import model.Quiz.AnswerList.{getCorrect, getCorrectIndex}
import model.Quiz.{AnswerList, Quiz}
import model.QuizInGame

trait GameController:
  def nextQuiz(): QuizInGame
  def chooseQuiz(): Quiz
  def chooseAnswers(): AnswerList
  def endQuiz(): Unit

/** Companion object of standard game controller */
object StandardGameController:

  enum AvailableActions extends Enumeration:
    case Back
    case SelectAnswer

/** Defines the logic of the select page */
class StandardGameController extends PageController:

  import AppController.AvailablePages
  import StandardGameController.*

  val gameStage: GameStage = new GameStageImpl

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit = updateUI(Option(gameStage))

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back
    case AvailableActions.SelectAnswer => selectAnswer(value)

  def back: Unit = AppController.handle(AvailablePages.SelectMenu, Option.empty)

  def selectAnswer[T](value: Option[T]): Unit =
    if value.get.toString.toInt -1 == getCorrectIndex(gameStage.quizInGame.answers) then
      println("Risposta GIUSTA!")
    else
      println("Risposta SBAGLIATA!")
//    println(getCorrectIndex(gameStage.quizInGame.answers))
//    println(value.get.toString + " | " + getCorrect(gameStage.quizInGame.answers))
