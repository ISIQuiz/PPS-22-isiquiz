package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction, BackAction}
import view.{View, SelectMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.{AppController, PageController}
import model.Session

/** Companion object of select menu controller */
object SelectMenuController:

  case object Back extends ParameterlessAction
  case object Start extends ParameterlessAction


/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)
    case Start => AppController.handle(StandardGame)

  // Get session from application controller
  def getSession: Session = AppController.session

  override def nextIteration(): Unit =
    updateUI(SelectMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()

