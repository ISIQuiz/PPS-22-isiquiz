package controller

import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import model.Course
import model.SavedCourse
import model.SavedCourse.*
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

  /** action to select a course in the add quiz controller */
  case class SelectCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

  /** action to add a quiz in the course selected in the add quiz controller */
  case class AddQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  var courseSelected:Option[SavedCourse] = Option.empty
  var quizToAdd:Option[Quiz] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case SelectCourseAction(actionParameter) => courseSelected = actionParameter
    case AddQuizAction(actionParameter) => addQuiz(actionParameter)

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if courseSelected.isEmpty then
      sendUpdate(CourseListUpdate(Option(AppController.session.savedCourses)))
      sendUpdate(AskCourseSelectUpdate)
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
