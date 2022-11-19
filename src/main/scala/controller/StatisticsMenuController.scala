package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction, BackAction}
import view.{View, StatisticsMenuView}
import view.updates.{ViewUpdate, ParameterlessViewUpdate}

/** Companion object of statistics menu controller */
object StatisticsMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the statistics page */
class StatisticsMenuController extends PageController :

  import StatisticsMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)

  override def nextIteration(): Unit =
    updateUI(StatisticsMenuView.DefaultUpdate)

  override def updateUI[T](update: ViewUpdate[T]): Unit =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
