package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import model.SavedCourse
import view.{AddCourseMenuView, StandardGameView}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add course menu controller */
object AddCourseMenuController:

  case object Back extends ParameterlessAction
  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)
    case AddCourseAction(actionParameter) => addCourse(actionParameter)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.DefaultUpdate)
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.AskCoursePrint)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()

  def addCourse(actionParameter:Option[SavedCourse]):Unit =
    val newListCourses = AppController.session.savedCourses.appended(actionParameter.get)
    AppController.changeSavedCourses(newListCourses)
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.CoursePrint(actionParameter))
    AppController.handle(SettingsMenu)
