package controller

import controller.Controller.AppController.{session, session_}
import controller.Controller.{AppController, PageController}
import model.GameStage.GameStageImpl
import model.Session.changeSavedCourses
import model.{SavedCourse, Session}

/** Companion object of select menu controller */
object SelectMenuController:

  enum AvailableActions extends Enumeration:
    case Back
    case Start
    case Selection(value: Option[Int])

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import AppController.AvailablePages
  import SelectMenuController.*

  // Get session from application controller
  def getSession: Session = AppController.session

  override def updateUI[T](update: Option[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

  override def nextIteration(): Unit = updateUI(Option(getSession))

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back
    case AvailableActions.Start => start
    case AvailableActions.Selection(value) => selection(value.get)

  def back: Unit = AppController.handle(AvailablePages.MainMenu, Option.empty)

  def start: Unit = AppController.handle(AvailablePages.StandardGame, Option.empty)

  def selection(value: Int): Unit =
    val newCourse = getSession.savedCourses(value - 1)
    AppController.handle(AvailablePages.StandardGame, Option(GameStageImpl(List(newCourse))))