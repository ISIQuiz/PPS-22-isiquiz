package view.terminalUI

import controller.StandardGameController
import controller.StandardGameController.*
import controller.actions.{Action, ParameterlessAction}
import model.GameStage
import view.View.TerminalView
import view.terminalUI.TerminalStandardGameMenu.{AnswerFeedbackUpdate, DefaultUpdate, NewQuizUpdate}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map

object TerminalStandardGameMenu:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class NewQuizUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)
  case class AnswerFeedbackUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate[T](updateParameter)

/** Standard game terminal interface  */
class TerminalStandardGameMenu extends TerminalView:

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
