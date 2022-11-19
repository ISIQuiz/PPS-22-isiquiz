package controller

import controller.actions.{Action, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import view.AddCourseMenuView
import view.updates.ViewUpdate

/** Companion object of add quiz menu controller */
object AddQuizMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)

  override def nextIteration(): Unit =
    updateUI(AddCourseMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
