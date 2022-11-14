package view

import View.*
import view.updates.ParameterlessViewUpdate
import controller.StandardGameController
import controller.actions.Action
import model.GameStage.*

object StandardGameView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class NewQuizUpdate[T](updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)
  case class AnswerFeedbackUpdate[T](updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[Int, Action[T]] = Map(
      0 -> StandardGameController.AvailableActions.Back,
      1 -> StandardGameController.AvailableActions.SelectAnswer,
      2 -> StandardGameController.AvailableActions.SelectAnswer,
      3 -> StandardGameController.AvailableActions.SelectAnswer,
      4 -> StandardGameController.AvailableActions.SelectAnswer
    )

    override def draw[T](update: ViewUpdate[T]): String = update match
      case DefaultUpdate =>
        println("Standard quiz:")
        if (update.updateValue.isDefined){
          update.updateValue.get.asInstanceOf[GameStage].courseInGame.foreach(savedCourse => savedCourse.quizList.foreach(quiz =>
            println("0) Termina quiz");
            println(quiz);
          ))
        }
        "StandardGame"
      case AnswerFeedbackUpdate =>
        println(update)
        "StandardGameUpdate"
