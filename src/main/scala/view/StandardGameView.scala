package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.StandardGameController
import controller.StandardGameController.*
import controller.actions.{Action, ParameterlessAction}
import model.GameStage
import scala.collection.mutable.Map

object StandardGameView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class NewQuizUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)
  case class AnswerFeedbackUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends TerminalView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "0" -> Back,
      "1" -> SelectAnswer(Option(1)),
      "2" -> SelectAnswer(Option(2)),
      "3" -> SelectAnswer(Option(3)),
      "4" -> SelectAnswer(Option(4))
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
      case DefaultUpdate =>
        println("Standard quiz:")
        println("0) Termina quiz")
        handleInput()
      case NewQuizUpdate(updateParameter: Option[T]) =>
        if (updateParameter.isDefined){
          updateParameter.get.asInstanceOf[GameStage].coursesInGame.foreach(savedCourse => savedCourse.quizList.foreach(quiz =>
            println(quiz);
          ))
        }
      case AnswerFeedbackUpdate(updateParameter: Option[T]) =>
        println(updateParameter)
