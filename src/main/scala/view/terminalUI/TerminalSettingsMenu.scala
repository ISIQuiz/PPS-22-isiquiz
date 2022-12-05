package view.terminalUI

import controller.SettingsMenuController
import controller.SettingsMenuController.*
import controller.actions.Action
import view.View.TerminalView
import view.updates.{ViewUpdate}
import view.SettingsMenuView.*
import scala.collection.mutable.Map

object TerminalSettingsMenu

/** Settings menu terminal interface  */
class TerminalSettingsMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back,
    "2" -> AddCourse,
    "3" -> AddQuiz,
    "4" -> EditCourse
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("Menu impostazioni:\n1) Menu principale\n2) Aggiungi corso\n3) Aggiungi quiz\n4) Modifica corso")
