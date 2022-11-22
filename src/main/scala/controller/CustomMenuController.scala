package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import model.GameStage
import model.settings.GameSettings
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.{CustomMenuView, View}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of custom settings menu controller */
object CustomMenuController:

  case object Back extends ParameterlessAction
  case class NewGameSettings[T](override val actionParameter: Option[T]) extends Action(actionParameter)

/** Defines the logic of the custom settings page */
class CustomMenuController(var gameStage: GameStage) extends PageController :

  import CustomMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)
    case NewGameSettings(actionParameter) =>
      gameStage.gameSettings = actionParameter.get.asInstanceOf[GameSettings]
      AppController.handle(AppController.StandardGame(Option(gameStage)))

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(CustomMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
