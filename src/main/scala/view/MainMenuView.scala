package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map
import controller.MainMenuController
import controller.MainMenuController.*
import controller.actions.{Action, ParameterlessAction}
import utils.{TerminalInput, TerminalInputImpl}

object MainMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends TerminalView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Select,
      "2" -> Statistics,
      "3" -> Settings,
      "4" -> Quit
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      handleInput()
