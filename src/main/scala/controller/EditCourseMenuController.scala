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

  case class SelectCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

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
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
    if courseSelected.isEmpty then
      sendUpdate(CourseUpdate(Option(AppController.session.savedCourses)))
      sendUpdate(AskCourseUpdate)
    else
      sendUpdate(CoursePrintUpdate(courseSelected))
      sendUpdate(AskCourseEditUpdate)

  def editCourse(actionParameter:Option[SavedCourse]):Unit =
    val editedSavedCourse = SavedCourse.changeQuizList(actionParameter.get, courseSelected.get.quizList)
    val newListCourses = AppController.session.savedCourses.filterNot(course => course==courseSelected.get).appended(editedSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    ExportHandler.exportDataToPersonalDirectory(newListCourses) // Export saved course list to personal directory
    sendUpdate(CoursePrintUpdate(Option(editedSavedCourse)))
    sendUpdate(CourseEditedUpdate)
    courseSelected = None