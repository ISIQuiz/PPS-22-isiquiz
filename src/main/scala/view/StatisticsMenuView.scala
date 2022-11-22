package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.StatisticsMenuController
import controller.StatisticsMenuController.*
import controller.actions.Action
import scala.collection.mutable.Map

object StatisticsMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general StatisticsMenuView */
  trait StatisticsMenuView extends PageView

  /** A basic implementation of a StatisticsMenuView  */
  class StatisticsMenuViewImpl extends StatisticsMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit =
      println("Menu statistiche:\n1) Menu principale")
      handleInput()
