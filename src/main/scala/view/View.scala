package view

import controller.Controller.ApplicationControllerImpl
import controller.Controller.Action

object View:

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    def draw(): Unit
    def sendEvent[T](action: Action[T]): Unit =
      ApplicationControllerImpl.handle(action)

object MainMenu:
  import View.*

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends PageView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:
    override def draw(): Unit = println(
      "Menu principale:\n" +
      "1) Gioca\n" +
      "2) Statistiche\n" +
      "3) Impostazioni\n" +
      "4) Esci")

object SettingsMenu:
  import View.*

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:
    override def draw(): Unit = ???

