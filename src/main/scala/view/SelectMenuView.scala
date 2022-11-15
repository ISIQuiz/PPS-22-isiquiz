package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.SelectMenuController
import controller.actions.{Action, ParameterlessAction}

object SelectMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class SelectMenuViewImpl extends SelectMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> SelectMenuController.Back.asInstanceOf[Action[T]],
      2 -> SelectMenuController.Start.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu selezione:\n1) Menu principale\n2) Avvia quiz")
      "SelectMenu"
