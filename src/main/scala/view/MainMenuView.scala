package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.MainMenuController
import controller.actions.Action

object MainMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends PageView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:

    override val actionsMap: Map[Int, Action[T]] = Map(
      1 -> MainMenuController.Select,
      2 -> MainMenuController.Statistics,
      3 -> MainMenuController.Settings,
      4 -> MainMenuController.Quit
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      "MainMenu"
