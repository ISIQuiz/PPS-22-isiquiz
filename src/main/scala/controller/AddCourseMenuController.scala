package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import model.SavedCourse
import view.{AddCourseMenuView, StandardGameView}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

/** Companion object of add course menu controller */
object AddCourseMenuController:

  case object Back extends ParameterlessAction
  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddCourseMenuController extends PageController:

  import AddCourseMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)
    case AddCourseAction(actionParameter) => addCourse(actionParameter)

  override def nextIteration(): Unit =
    updateUI(AddCourseMenuView.DefaultPrint)
    updateUI(AddCourseMenuView.AskCoursePrint)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)


  def addCourse(actionParameter:Option[SavedCourse]):Unit =
    val newListCourses = AppController.session.savedCourses.appended(actionParameter.get)
    AppController.changeSavedCourses(newListCourses)
    updateUI(AddCourseMenuView.CoursePrint(actionParameter))
    AppController.handle(SettingsMenu)