package view

import View.*
import controller.SelectMenuController

object SelectMenu:

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class SelectMenuViewImpl extends SelectMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SelectMenuController.AvailableActions.Back
    )

    override def draw(): String =
      println("Menu selezione:\n1) Menu principale")
      "SelectMenu"
