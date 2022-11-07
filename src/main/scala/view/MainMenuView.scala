package view

import View.*
import controller.MainMenuController

object MainMenuView:

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends PageView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> MainMenuController.AvailableActions.Select,
      2 -> MainMenuController.AvailableActions.Statistics,
      3 -> MainMenuController.AvailableActions.Settings,
      4 -> MainMenuController.AvailableActions.Quit
    )

    override def draw[T](update: Option[T]): String =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      "MainMenu"
