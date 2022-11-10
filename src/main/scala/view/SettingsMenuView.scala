package view

import View.*
import controller.SettingsMenuController

object SettingsMenuView:

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SettingsMenuController.AvailableActions.Back,
      2 -> SettingsMenuController.AvailableActions.AddCourse,
      3 -> SettingsMenuController.AvailableActions.AddQuiz
    )

    override def draw[T](update: Option[T]): String =
      println("Menu impostazioni:\n1) Menu principale\n2) Aggiungi corso\n3) Aggiungi quiz")
      "SettingsMenu"
