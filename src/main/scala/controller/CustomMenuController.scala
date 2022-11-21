package controller

import controller.AppController.*
import controller.actions.{Action, BackAction, ParameterlessAction}
import controller.{AppController, PageController}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import view.{CustomMenuView, View}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Companion object of custom settings menu controller */
object CustomMenuController:

  case object Back extends ParameterlessAction

/** Defines the logic of the custom settings page */
class CustomMenuController extends PageController :

  import CustomMenuController.*

  override def matchAction[T](action: Action[T]): Unit = action match
    case Back => AppController.handle(MainMenu)

  override def nextIteration(): Unit =
    AppController.currentPage.pageView.updateUI(CustomMenuView.DefaultUpdate)
    Await.ready(actionPromise.future, Duration.Inf)
    AppController.currentPage.pageController.nextIteration()
