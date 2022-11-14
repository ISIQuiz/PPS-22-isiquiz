package controller

import controller.{AppController, PageController}
import controller.actions.{Action, ParameterlessAction}
import view.{View, SettingsMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}

/** Companion object of settings menu controller */
object SettingsMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the settings page */
class SettingsMenuController extends PageController :

  import SettingsMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenu)

  override def nextIteration(): Unit =
    updateUI(SettingsMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
