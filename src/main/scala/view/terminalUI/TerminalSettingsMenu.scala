package view.terminalUI

import controller.SettingsMenuController
import controller.SettingsMenuController.*
import controller.actions.Action
import view.View.TerminalView
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map

object TerminalSettingsMenu:

  case object DefaultUpdate extends ParameterlessViewUpdate

/** Settings menu terminal interface  */
class TerminalSettingsMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back,
    "2" -> AddCourse,
    "3" -> AddQuiz
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("Menu impostazioni:\n1) Menu principale\n2) Aggiungi corso\n3) Aggiungi quiz")
