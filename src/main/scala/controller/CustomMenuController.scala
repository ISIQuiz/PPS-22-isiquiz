package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.GameStage
import model.settings.GameSettings
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.View
import view.terminalUI.TerminalCustomMenu

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of custom settings menu controller */
object CustomMenuController extends BackAction:

  case class NewGameSettings[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the custom settings page */
class CustomMenuController(var gameStage: GameStage) extends PageController :

  import CustomMenuController.*

  var actionsBuffer: ListBuffer[Action[Any]] = ListBuffer()

  override def handle[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(SelectMenuAction)
    case NewGameSettings(actionParameter) =>
      gameStage.gameSettings = actionParameter.get.asInstanceOf[GameSettings]
      AppController.handle(AppController.StandardGameAction(Option(gameStage)))

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(TerminalCustomMenu.DefaultUpdate)
