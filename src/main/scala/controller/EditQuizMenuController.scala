package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.Quiz.Quiz
import model.SavedCourse
import model.SavedCourse.*
import utils.storage.ExportHandler
import view.EditQuizMenuView.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of edit quiz menu controller */
object EditQuizMenuController extends BackAction :

  /** action to select a course in the edit quiz controller */
  case class SelectCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)

  /** action to select a quiz in the edit quiz controller */
  case class SelectQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

  /** action to edit a quiz selected in the edit quiz controller */
  case class EditQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the edit quiz page */
class EditQuizMenuController extends PageController :

  import EditQuizMenuController.*

  var courseSelected: Option[SavedCourse] = Option.empty

  var quizSelected: Option[Quiz] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case SelectCourseAction(actionParameter) =>
      courseSelected = actionParameter
      quizSelected = None
    case SelectQuizAction(actionParameter) => quizSelected = actionParameter
    case EditQuizAction(actionParameter) => editQuiz(actionParameter)

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
    if courseSelected.isEmpty then
      sendUpdate(CourseListUpdate(Option(AppController.session.savedCourses)))
      sendUpdate(AskCourseSelectUpdate)
    else if quizSelected.isEmpty then
      sendUpdate(QuizListUpdate(Option(courseSelected.get.quizList)))
      sendUpdate(AskQuizSelectUpdate)
    else
      sendUpdate(QuizPrintUpdate(quizSelected))
      sendUpdate(AskQuizEditUpdate)

  def editQuiz(optionalQuizEdited: Option[Quiz]): Unit =
    var newQuizList = courseSelected.get.quizList.filterNot(quiz => quiz == quizSelected.get)
    var feedbackUpdate: ParameterlessViewUpdate = DefaultUpdate
    optionalQuizEdited match
      case Some(quiz) =>
        newQuizList = newQuizList.appended(quiz)
        feedbackUpdate = QuizEditedUpdate
      case _ =>
        feedbackUpdate = QuizDeletedUpdate
    val newSavedCourse = SavedCourse.changeQuizList(courseSelected.get, newQuizList)
    val newListCourses = AppController.session.savedCourses.filterNot(course => course == courseSelected.get).appended(newSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    ExportHandler.exportDataToPersonalDirectory(newListCourses) // Export saved course list to personal directory
    sendUpdate(QuizPrintUpdate(optionalQuizEdited))
    sendUpdate(feedbackUpdate)
    courseSelected = None
    quizSelected = None
