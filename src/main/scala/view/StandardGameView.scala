package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.StandardGameController
import controller.StandardGameController.*
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
      0 -> Back,
      1 -> SelectAnswer(Option(1.asInstanceOf[T])),
      2 -> SelectAnswer(Option(2.asInstanceOf[T])),
      3 -> SelectAnswer(Option(3.asInstanceOf[T])),
      4 -> SelectAnswer(Option(4.asInstanceOf[T]))
    )

    override def draw[T](update: ViewUpdate[T]): String = update match
      case DefaultUpdate =>
        println("Standard quiz:")
        println("0) Termina quiz");
        "StandardGame"
      case NewQuizUpdate(updateParameter: Option[T]) =>
        if (updateParameter.isDefined){
          updateParameter.get.asInstanceOf[GameStage].courseInGame.foreach(savedCourse => savedCourse.quizList.foreach(quiz =>
            println(quiz);
          ))
        }
        "StandardGameUpdate"
      case AnswerFeedbackUpdate(updateParameter: Option[T]) =>
        println(updateParameter)
        "StandardGameUpdate"
