package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import utils.{Timer, TimerImpl}
import view.{MainMenuView, View}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import concurrent.ExecutionContext.Implicits.global
import scala.util.Try

/** Companion object of main menu controller */
object MainMenuController:

  case object Select extends ParameterlessAction
  case object Statistics extends ParameterlessAction
  case object Settings extends ParameterlessAction
  case object Quit extends ParameterlessAction

/** Defines the logic of the main menu page */
class MainMenuController extends PageController:

  import MainMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Select => AppController.handle(SelectMenu)
    case Statistics => AppController.handle(StatisticsMenu)
    case Settings => AppController.handle(SettingsMenu)
    case Quit => System.exit(0)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(MainMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
