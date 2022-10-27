package view

object View:

  trait PageView:
    def draw(): Unit

object MainMenu:
  import View.*

  trait MainMenuView extends PageView

  class MainMenuViewImpl extends MainMenuView:
    override def draw(): Unit = println(
      "Menu principale:\n" +
      "1) Gioca\n" +
      "2) Statistiche\n" +
      "3) Impostazioni\n" +
      "4) Esci")

object SettingsMenu:
  import View.*

  trait SettingsMenuView extends PageView

  class SettingsMenuViewImpl extends SettingsMenuView:
    override def draw(): Unit = ???

