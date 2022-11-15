package controller

import controller.{AppController, PageController}
import controller.actions.{Action, ParameterlessAction, BackAction}
import view.{View, StandardGameView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
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

  case object Back extends ParameterlessAction
  case class SelectAnswer[T](override val actionParameter: Option[T]) extends Action(actionParameter)


/** Defines the logic of the select page */
class StandardGameController extends PageController:

  import StandardGameController.*

  val gameStage: GameStage = new GameStageImpl

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenu)
    case SelectAnswer(actionParameter) => selectAnswer(actionParameter)

  override def nextIteration(): Unit =
//    nextQuiz()
    updateUI(StandardGameView.DefaultUpdate)
    updateUI(StandardGameView.NewQuizUpdate(Option(gameStage)))
    AppController.currentPage.pageView.handleInput()

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)

  def selectAnswer[T](actionParameter: Option[T]): Unit =
    if actionParameter.get.toString.toInt -1 == getCorrectIndex(gameStage.quizInGame.answers) then
//      updateUI(ViewUpdate(StandardGameView.UpdateType.AnswerFeedback, Option("Giusta")))
//      nextIteration()
      println("Risposta GIUSTA!")
    else
//      updateUI(ViewUpdate(StandardGameView.UpdateType.AnswerFeedback, Option("Sbagliata")))
      println("Risposta SBAGLIATA!")
//    println(getCorrectIndex(gameStage.quizInGame.answers))
//    println(value.get.toString + " | " + getCorrect(gameStage.quizInGame.answers))
