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

    override val actionsMap: Map[Int, Action[Any]] = Map(
      1 -> Select.asInstanceOf[Action[Any]],
      2 -> Statistics.asInstanceOf[Action[Any]],
      3 -> Settings.asInstanceOf[Action[Any]],
      4 -> Quit.asInstanceOf[Action[Any]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      "MainMenu"
