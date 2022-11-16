package controller

import controller.{AppController, PageController}
import controller.actions.{Action, ParameterlessAction, BackAction}
import view.{View, SelectMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.{AppController, PageController}
import model.Session
import model.GameStage.GameStageImpl

/** Companion object of select menu controller */
object SelectMenuController:

  case object Back extends ParameterlessAction
  case class Selection[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenu)
    case Selection(actionParameter) =>
      AppController.handle(AppController.StandardGame(Option(GameStageImpl(List(getSession.savedCourses(actionParameter.get.asInstanceOf[Int] - 1))))))

  // Get session from application controller
  def getSession: Session = AppController.session

  override def nextIteration(): Unit =
    updateUI(SelectMenuView.DefaultUpdate)
    updateUI(SelectMenuView.CourseUpdate(Option(getSession)))
    AppController.currentPage.pageView.handleInput()

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
