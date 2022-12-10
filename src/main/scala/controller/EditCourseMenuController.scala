package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.SavedCourse
import utils.storage.ExportHandler
import view.EditCourseMenuView.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of edit course menu controller */
object EditCourseMenuController extends BackAction:
  /** action to select a course in the edit course controller */
  case class SelectCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)
  /** action to edit a course selected in the edit course controller */
  case class EditCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

/** Defines the logic of the edit course page */
class EditCourseMenuController extends PageController:

  import EditCourseMenuController.*

  var courseSelected: Option[SavedCourse] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case SelectCourseAction(actionParameter) => courseSelected = actionParameter
    case EditCourseAction(actionParameter) => editCourse(actionParameter)

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if courseSelected.isEmpty then
      sendUpdate(CourseListUpdate(Option(AppController.session.savedCourses)))
      sendUpdate(AskCourseSelectUpdate)
    else
      sendUpdate(CoursePrintUpdate(courseSelected))
      sendUpdate(AskCourseEditUpdate)

  def editCourse(optionalCourseEdited:Option[SavedCourse]):Unit =
    val newListCourses = AppController.session.savedCourses.filterNot(course => course==courseSelected.get)
    var feedbackUpdate:ParameterlessViewUpdate = DefaultUpdate
    optionalCourseEdited match
      case Some(course) =>
        newListCourses.appended(SavedCourse.changeQuizList(course, courseSelected.get.quizList))
        feedbackUpdate = CourseEditedUpdate
      case _ =>
        feedbackUpdate = CourseDeletedUpdate
    AppController.changeSavedCourses(newListCourses)
    ExportHandler.exportDataToPersonalDirectory(newListCourses) // Export saved course list to personal directory
    sendUpdate(CoursePrintUpdate(optionalCourseEdited))
    sendUpdate(feedbackUpdate)
    courseSelected = None