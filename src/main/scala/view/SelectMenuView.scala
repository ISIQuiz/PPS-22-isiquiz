package view

import View.*
import controller.SelectMenuController

object SelectMenuView:

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class SelectMenuViewImpl extends SelectMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SelectMenuController.AvailableActions.Back,
      2 -> SelectMenuController.AvailableActions.Start
    )

    override def draw(): String =
      println("Menu selezione:\n1) Menu principale\n2) Avvia quiz")
      "SelectMenu"
