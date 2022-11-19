package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import utils.{Timer, TimerImpl}
import view.{MainMenuView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import java.util.logging.Level

/** Companion object of main menu controller */
object MainMenuController:

  case object Select extends ParameterlessAction
  case object Statistics extends ParameterlessAction
  case object Settings extends ParameterlessAction
  case object Quit extends ParameterlessAction

/** Defines the logic of the main menu page */
class MainMenuController extends PageController:

  import MainMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Select => AppController.handle(SelectMenu)
    case Statistics => AppController.handle(StatisticsMenu)
    case Settings => AppController.handle(SettingsMenu)
    case Quit => System.exit(0)

  override def nextIteration(): Unit =
    updateUI(MainMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit  =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

