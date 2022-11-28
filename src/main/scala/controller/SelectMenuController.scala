package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.{AppController, PageController}
import model.{GameStage, QuizInGame, Session}
import model.GameStage.*
import view.terminalUI.TerminalSelectMenu

import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/** Companion object of select menu controller */
object SelectMenuController:

  case object Back extends ParameterlessAction
  case object Start extends ParameterlessAction
  case object Custom extends ParameterlessAction
  case class Selection[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  var gameStage = GameStage()

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenuAction)
    case Start => AppController.handle(AppController.StandardGameAction(Option(gameStage)))
    case Custom =>  AppController.handle(AppController.CustomMenuAction(Option(gameStage)))
    case Selection(actionParameter) => modifySelectedCourses(actionParameter)

  // Get session from application controller
  def getSession: Session = AppController.session

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalSelectMenu.DefaultUpdate)
    AppController.currentPage.pageView.updateUI(TerminalSelectMenu.CourseUpdate(Option(getSession.savedCourses.map(course => (course,gameStage.coursesInGame.contains(course))))))

  private def modifySelectedCourses[T](courseIndex: Option[T]): Unit =
    val selectedCourse = getSession.savedCourses(courseIndex.get.asInstanceOf[Int] - 1)

    if gameStage.coursesInGame.contains(selectedCourse) then
      gameStage.coursesInGame = gameStage.coursesInGame.diff(List(selectedCourse))
    else
      gameStage.coursesInGame = gameStage.coursesInGame ::: List(selectedCourse)
