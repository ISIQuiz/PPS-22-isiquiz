package controller

import controller.{AppController, PageController}
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import model.stats.PlayerStats.removeUnusedStats
import utils.Timer
import utils.storage.ExportHandler
import view.View
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.MainMenuView
import view.MainMenuView.DefaultUpdate
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
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

  override def handle[T](action: Action[T]): Unit = action match
    case Select => AppController.handle(SelectMenuAction)
    case Statistics =>
      // Remove unused player stats and export it
      ExportHandler.exportDataToPersonalDirectory(removeUnusedStats(session.savedCourses, session.playerStats))
      AppController.handle(StatisticsMenuAction)
    case Settings => AppController.handle(SettingsMenuAction)
    case Quit => System.exit(0)

  override def nextIteration(): Unit =
    sendUpdate(DefaultUpdate)
