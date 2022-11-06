package controller

import controller.Controller.{AppController, PageController}

/** Companion object of standard game controller */
object StandardGameController:

  enum AvailableActions extends Enumeration:
    case Back

/** Defines the logic of the select page */
class StandardGameController extends PageController:

  import AppController.AvailablePages
  import StandardGameController.*

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back

  def back: Unit = AppController.handle(AvailablePages.SelectMenu, Option.empty)