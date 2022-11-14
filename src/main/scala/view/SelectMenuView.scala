package view

import View.*
import view.updates.ParameterlessViewUpdate
import controller.SelectMenuController
import controller.actions.Action

object SelectMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  
  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class SelectMenuViewImpl extends SelectMenuView:

    override val actionsMap: Map[Int, Action[T]] = Map(
      1 -> SelectMenuController.AvailableActions.Back,
      2 -> SelectMenuController.AvailableActions.Start
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu selezione:\n1) Menu principale\n2) Avvia quiz")
      "SelectMenu"
