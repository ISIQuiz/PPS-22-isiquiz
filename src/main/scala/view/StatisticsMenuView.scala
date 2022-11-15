package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.StatisticsMenuController
import controller.actions.Action

object StatisticsMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general StatisticsMenuView */
  trait StatisticsMenuView extends PageView

  /** A basic implementation of a StatisticsMenuView  */
  class StatisticsMenuViewImpl extends StatisticsMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> StatisticsMenuController.Back.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu statistiche:\n1) Menu principale")
      "StatisticsMenu"
