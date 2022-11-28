package controller

import controller.actions.{Action, ParameterlessAction}
import controller.{AppController, PageController}
import controller.AppController.*
import model.{Course, SavedCourse}
import model.Quiz.Quiz
import view.terminalUI.TerminalAddQuizMenu
import view.updates.ViewUpdate


import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of add quiz menu controller */
object AddQuizMenuController:

  case object Back extends ParameterlessAction

  case class AddCourseAction(override val actionParameter: Option[SavedCourse]) extends Action(actionParameter)
  case class AddQuizAction(override val actionParameter: Option[Quiz]) extends Action(actionParameter)

/** Defines the logic of the add course page */
class AddQuizMenuController extends PageController :

  import AddQuizMenuController.*

  var actionsBuffer: ListBuffer[Action[Any]] = ListBuffer()

  var courseSelected:Option[SavedCourse] = Option.empty
  var quizToAdd:Option[Quiz] = Option.empty

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SettingsMenuAction)
    case AddCourseAction(actionParameter) => courseSelected = actionParameter
    case AddQuizAction(actionParameter) => addQuiz(actionParameter)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalAddQuizMenu.DefaultUpdate)
    actionPromise = Promise[Unit]
    if courseSelected.isEmpty then
      AppController.currentPage.pageView.updateUI(TerminalAddQuizMenu.AskCoursePrint(Option(AppController.session.savedCourses)))
    else
      AppController.currentPage.pageView.updateUI(TerminalAddQuizMenu.AskQuizPrint)

  private def addQuiz[T](actionParameter: Option[Quiz]): Unit =
    quizToAdd = actionParameter
    val newSavedCourse = SavedCourse.changeQuizList(courseSelected.get, courseSelected.get.quizList.::(quizToAdd.get))
    val newListCourses = AppController.session.savedCourses.filterNot(course => course == courseSelected).appended(newSavedCourse)
    AppController.changeSavedCourses(newListCourses)
    AppController.currentPage.pageView.updateUI(TerminalAddQuizMenu.QuizPrint(quizToAdd))
    actionPromise = Promise[Unit]
    AppController.currentPage.pageView.updateUI(TerminalAddQuizMenu.QuizAdded)
    Await.ready(actionPromise.future, Duration.Inf)
    actionPromise = Promise[Unit] //EXTRA to fix the nesting matchAction Problem since complete is after that (after this there is the complete of AskQuiz

