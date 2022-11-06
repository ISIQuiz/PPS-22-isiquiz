package controller

import controller.Controller.{AppController, PageController}

/** Companion object of settings menu controller */
object SettingsMenuController:

  enum AvailableActions extends Enumeration :
    case Back

/** Defines the logic of the settings page */
class SettingsMenuController extends PageController :

  import AppController.AvailablePages
  import SettingsMenuController.*

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Back => back

  def back: Unit = AppController.handle(AvailablePages.MainMenu, Option.empty)