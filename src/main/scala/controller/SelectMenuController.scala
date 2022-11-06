package controller

import controller.Controller.{AppController, PageController}

/** Companion object of select menu controller */
object SelectMenuController:

  enum AvailableActions extends Enumeration:
    case Back
    case Start

/** Defines the logic of the select page */
class SelectMenuController extends PageController:

  import AppController.AvailablePages
  import SelectMenuController.*

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back
    case AvailableActions.Start => start

  def back: Unit = AppController.handle(AvailablePages.MainMenu, Option.empty)

  def start: Unit = AppController.handle(AvailablePages.StandardGame, Option.empty)