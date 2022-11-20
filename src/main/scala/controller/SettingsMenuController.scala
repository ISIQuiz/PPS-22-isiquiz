package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import view.{SettingsMenuView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of settings menu controller */
object SettingsMenuController:

  case object Back extends ParameterlessAction
  case object AddCourse extends ParameterlessAction
  case object AddQuiz extends ParameterlessAction

/** Defines the logic of the settings page */
class SettingsMenuController extends PageController :

  import SettingsMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)
    case AddCourse => AppController.handle(AddCourseMenu)
    case AddQuiz => AppController.handle(AddQuizMenu)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(SettingsMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
