package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import view.{AddCourseMenuView, StandardGameView}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add course menu controller */
object AddCourseMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
