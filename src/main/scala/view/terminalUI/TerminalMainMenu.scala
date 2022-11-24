package view.terminalUI

import controller.MainMenuController
import controller.MainMenuController.*
import controller.actions.{Action, ParameterlessAction}
import utils.{TerminalInput, TerminalInputImpl}
import view.View.TerminalView
import view.MainMenuView
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.Map

object TerminalMainMenu

/** Main menu terminal interface  */
class TerminalMainMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Select,
    "2" -> Statistics,
    "3" -> Settings,
    "4" -> Quit
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
    handleInput()
