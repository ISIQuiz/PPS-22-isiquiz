package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import view.{AddCourseMenuView, StandardGameView}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

/** Companion object of add course menu controller */
object AddCourseMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)

  override def nextIteration(): Unit =
    updateUI(AddCourseMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
