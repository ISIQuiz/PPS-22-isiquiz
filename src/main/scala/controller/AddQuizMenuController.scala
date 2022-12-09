package controller

import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import model.{Course, SavedCourse}
import model.Quiz.Quiz
import model.GameStage.*
import utils.storage.ExportHandler
import view.AddQuizMenuView.*
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of add quiz menu controller */
object AddQuizMenuController extends BackAction:

  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)
  case class AddQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  var courseSelected:Option[SavedCourse] = Option.empty
  var quizToAdd:Option[Quiz] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case AddCourseAction(actionParameter) => courseSelected = actionParameter
    case AddQuizAction(actionParameter) => addQuiz(actionParameter)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
    if courseSelected.isEmpty then
      sendUpdate(CourseUpdate(Option(AppController.session.savedCourses)))
      sendUpdate(AskCourseUpdate)
    else
      sendUpdate(AskQuizUpdate)

  private def addQuiz[T](actionParameter: Option[Quiz]): Unit =
    quizToAdd = actionParameter
    val newSavedCourse = SavedCourse.changeQuizList(courseSelected.get, courseSelected.get.quizList.::(quizToAdd.get))
    val newListCourses = AppController.session.savedCourses.filterNot(course => course == courseSelected.get).appended(newSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    ExportHandler.exportDataToPersonalDirectory(newListCourses) // Export saved course list to personal directory
    sendUpdate(QuizPrintUpdate(quizToAdd))
    sendUpdate(QuizAddedUpdate)
    courseSelected = None

