package view

import View.*
import controller.StatisticsMenuController

object StatisticsMenuView:

  /** SettingsMenuView define aspects of a general StatisticsMenuView */
  trait StatisticsMenuView extends PageView

  /** A basic implementation of a StatisticsMenuView  */
  class StatisticsMenuViewImpl extends StatisticsMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> StatisticsMenuController.AvailableActions.Back
    )

    override def draw[T](update: UIUpdate[T]): String =
      println("Menu statistiche:\n1) Menu statistiche")
      "StatisticsMenu"
