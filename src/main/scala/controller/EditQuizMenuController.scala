package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.Quiz.Quiz
import model.SavedCourse
import view.EditQuizMenuView.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of edit quiz menu controller */
object EditQuizMenuController extends BackAction:

  case class SelectCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)
  
  case class SelectQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

  case class EditQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the edit quiz page */
class EditQuizMenuController extends PageController:

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
    AppController.currentPage.pageView.updateUI(DefaultUpdate)
    if courseSelected.isEmpty then
      AppController.currentPage.pageView.updateUI(CourseListUpdate(Option(AppController.session.savedCourses)))
      AppController.currentPage.pageView.updateUI(AskCourseUpdate)
    else if quizSelected.isEmpty then
      AppController.currentPage.pageView.updateUI(QuizListUpdate(Option(courseSelected.get.quizList)))
      AppController.currentPage.pageView.updateUI(AskQuizUpdate)
    else 
      AppController.currentPage.pageView.updateUI(QuizPrintUpdate(quizSelected))
      AppController.currentPage.pageView.updateUI(AskQuizEditUpdate)

  def editQuiz(quizEdited:Option[Quiz]):Unit =
    val newQuizList = courseSelected.get.quizList.filterNot(quiz => quiz==quizSelected.get).appended(quizEdited.get)
    val newSavedCourse = SavedCourse.changeQuizList(courseSelected.get, newQuizList)
    val newListCourses = AppController.session.savedCourses.filterNot(course => course==courseSelected.get).appended(newSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    AppController.currentPage.pageView.updateUI(QuizPrintUpdate(quizEdited))
    AppController.currentPage.pageView.updateUI(QuizEditedUpdate)
    courseSelected = None
    quizSelected = None
