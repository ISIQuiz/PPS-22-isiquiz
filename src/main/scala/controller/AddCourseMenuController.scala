package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import model.SavedCourse
import view.terminalUI.{TerminalAddCourseMenu, TerminalStandardGameMenu}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add course menu controller */
object AddCourseMenuController:

  case object Back extends ParameterlessAction
  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  var actionsBuffer: ListBuffer[Action[Any]] = ListBuffer()

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case AddCourseAction(actionParameter) => addCourse(actionParameter)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalAddCourseMenu.DefaultUpdate)
    AppController.currentPage.pageView.updateUI(TerminalAddCourseMenu.AskCoursePrint)

  def addCourse(actionParameter:Option[SavedCourse]):Unit =
    val newListCourses = AppController.session.savedCourses.appended(actionParameter.get)
    AppController.changeSavedCourses(newListCourses)
    AppController.currentPage.pageView.updateUI(TerminalAddCourseMenu.CoursePrint(actionParameter))
    AppController.handle(SettingsMenuAction)
