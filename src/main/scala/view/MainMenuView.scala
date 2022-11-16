package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.Map
import controller.MainMenuController
import controller.MainMenuController.*
import controller.actions.{Action, ParameterlessAction}

object MainMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends PageView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> Select.asInstanceOf[Action[T]],
      2 -> Statistics.asInstanceOf[Action[T]],
      3 -> Settings.asInstanceOf[Action[T]],
      4 -> Quit.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      "MainMenu"
