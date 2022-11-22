package controller

import controller.actions.{Action, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import model.{Course, SavedCourse}
import model.Quiz.Quiz
import view.AddQuizMenuView
import view.updates.ViewUpdate

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of add quiz menu controller */
object AddQuizMenuController:

  case object Back extends ParameterlessAction

  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)
  case class AddQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  var courseSelected:Option[SavedCourse] = Option.empty
  var quizToAdd:Option[Quiz] = Option.empty

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenu)
    case AddCourseAction(actionParameter) => courseSelected = actionParameter
    case AddQuizAction(actionParameter) => addQuiz(actionParameter)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(AddCourseMenuView.DefaultUpdate)
    if courseSelected.isEmpty then
      updateUI(AddQuizMenuView.AskCoursePrint(Option(AppController.session.savedCourses)))
    else
      updateUI(AddQuizMenuView.AskQuizPrint)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()

  private def addQuiz[T](actionParameter: Option[Quiz]): Unit =
    quizToAdd = actionParameter
    val newSavedCourse = SavedCourse.changeQuizList(courseSelected.get, courseSelected.get.quizList.::(quizToAdd.get))
    val newListCourses = AppController.session.savedCourses.filterNot(course => course == courseSelected).appended(newSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    updateUI(AddQuizMenuView.QuizPrint(quizToAdd))
    AppController.handle(SettingsMenu)
