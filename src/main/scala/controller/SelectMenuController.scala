package controller

import controller.{AppController, PageController}
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.{SelectMenuView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.{AppController, PageController}
import model.{GameStage, QuizInGame, Session}
import model.GameStage.*

/** Companion object of select menu controller */
object SelectMenuController:

  case object Back extends ParameterlessAction
  case object Start extends ParameterlessAction
  case class Selection[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  var gameStage = GameStage()

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenu)
    case Start => AppController.handle(AppController.StandardGame(Option(gameStage)))
    case Selection(actionParameter) => modifySelectedCourses(actionParameter)

  // Get session from application controller
  def getSession: Session = AppController.session

  override def nextIteration(): Unit =

    updateUI(SelectMenuView.DefaultUpdate)
    updateUI(SelectMenuView.CourseUpdate(Option(getSession.savedCourses.map(course => (course,gameStage.coursesInGame.contains(course))))))
    AppController.currentPage.pageView.handleInput()

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)

  private def modifySelectedCourses[T](courseIndex: Option[T]): Unit =
    val selectedCourse = getSession.savedCourses(courseIndex.get.asInstanceOf[Int] - 1)

    if gameStage.coursesInGame.contains(selectedCourse) then
      gameStage.coursesInGame = gameStage.coursesInGame.diff(List(selectedCourse))
    else
      gameStage.coursesInGame = gameStage.coursesInGame ::: List(selectedCourse)
