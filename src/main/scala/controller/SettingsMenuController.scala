package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.SettingsMenuView.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import view.SettingsMenuView.*

/** Companion object of settings menu controller */
object SettingsMenuController extends BackAction:

  case object AddCourse extends ParameterlessAction
  case object AddQuiz extends ParameterlessAction
  case object EditCourse extends ParameterlessAction
  case object EditQuiz extends ParameterlessAction

/** Defines the logic of the settings page */
class SettingsMenuController extends PageController :

  import SettingsMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)
    case AddCourse => AppController.handle(AddCourseMenuAction)
    case AddQuiz => AppController.handle(AddQuizMenuAction)
    case EditCourse => AppController.handle(EditCourseMenuAction)
    case EditQuiz => AppController.handle(EditQuizMenuAction)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
