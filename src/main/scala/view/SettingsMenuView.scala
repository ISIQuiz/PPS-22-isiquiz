package view

import View.*
import controller.SettingsMenuController

object SettingsMenu:

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SettingsMenuController.AvailableActions.Back
    )

    override def draw(): String =
      println("Menu impostazioni:\n1) Menu principale")
      "SettingsMenu"
