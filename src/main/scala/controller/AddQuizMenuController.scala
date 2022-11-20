package controller

import controller.actions.{Action, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import view.AddCourseMenuView
import view.updates.ViewUpdate

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add quiz menu controller */
object AddQuizMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
