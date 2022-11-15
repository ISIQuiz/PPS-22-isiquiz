package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.StandardGameController
import controller.actions.{Action, ParameterlessAction}
import model.GameStage.*

object StandardGameView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class NewQuizUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)
  case class AnswerFeedbackUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      0 -> StandardGameController.Back.asInstanceOf[Action[T]],
      1 -> StandardGameController.SelectAnswer(Option(1)).asInstanceOf[Action[T]],
      2 -> StandardGameController.SelectAnswer(Option(2)).asInstanceOf[Action[T]],
      3 -> StandardGameController.SelectAnswer(Option(3)).asInstanceOf[Action[T]],
      4 -> StandardGameController.SelectAnswer(Option(4)).asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String = update match
      case DefaultUpdate =>
        println("Standard quiz:")
        if (update.updateParameter.isDefined){
          update.updateParameter.get.asInstanceOf[GameStage].courseInGame.foreach(savedCourse => savedCourse.quizList.foreach(quiz =>
            println("0) Termina quiz");
            println(quiz);
          ))
        }
        "StandardGame"
      case AnswerFeedbackUpdate(updateParameter: Option[T]) =>
        println(updateParameter)
        "StandardGameUpdate"
