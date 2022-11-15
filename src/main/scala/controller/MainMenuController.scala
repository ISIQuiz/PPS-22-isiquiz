package controller

import controller.{AppController, PageController}
import controller.actions.{Action, ParameterlessAction}
import view.{View, MainMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}

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
    case Select => AppController.handle(AppController.SelectMenu)
    case Statistics => AppController.handle(AppController.StatisticsMenu)
    case Settings => AppController.handle(AppController.SettingsMenu)
    case Quit => System.exit(0)

  override def nextIteration(): Unit =
    updateUI(MainMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit  =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

