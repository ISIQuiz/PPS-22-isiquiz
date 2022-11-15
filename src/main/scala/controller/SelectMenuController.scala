package controller

import controller.{AppController, PageController}
import controller.actions.{Action, ParameterlessAction, BackAction}
import view.{View, SelectMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}

/** Companion object of select menu controller */
object SelectMenuController:

  case object Back extends ParameterlessAction
  case object Start extends ParameterlessAction


/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import SelectMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(AppController.MainMenu)
    case Start => AppController.handle(AppController.StandardGame)

  override def nextIteration(): Unit =
    updateUI(SelectMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
