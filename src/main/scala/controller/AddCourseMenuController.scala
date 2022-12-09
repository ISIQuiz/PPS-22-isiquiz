package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import model.SavedCourse
import utils.storage.ExportHandler
import view.AddCourseMenuView.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add course menu controller */
object AddCourseMenuController extends BackAction:

  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case AddCourseAction(actionParameter) => addCourse(actionParameter)

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    sendUpdate(AskCourseUpdate)

  def addCourse(actionParameter:Option[SavedCourse]):Unit =
    val newListCourses = AppController.session.savedCourses.appended(actionParameter.get)
    AppController.changeSavedCourses(newListCourses)
    ExportHandler.exportDataToPersonalDirectory(newListCourses) // Export saved course list to personal directory
    sendUpdate(CoursePrintUpdate(actionParameter))
    sendUpdate(CourseAddedUpdate)
