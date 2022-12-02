package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import view.View
import view.terminalUI.TerminalStatisticsMenu
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of statistics menu controller */
object StatisticsMenuController extends BackAction

/** Defines the logic of the statistics page */
class StatisticsMenuController extends PageController :

  import StatisticsMenuController.*

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenuAction)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalStatisticsMenu.DefaultUpdate)
