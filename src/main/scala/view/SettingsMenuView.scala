package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.SettingsMenuController
import controller.actions.Action

object SettingsMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> SettingsMenuController.Back.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu impostazioni:\n1) Menu principale")
      "SettingsMenu"
