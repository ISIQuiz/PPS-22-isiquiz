package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import view.View
import view.terminalUI.TerminalSettingsMenu
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
    case Back => AppController.handle(MainMenuAction)
    case AddCourse => AppController.handle(AddCourseMenuAction)
    case AddQuiz => AppController.handle(AddQuizMenuAction)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalSettingsMenu.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
