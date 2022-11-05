package view

import controller.Controller.{AppController, MainMenuController, SelectMenuController, StatisticsMenuController, SettingsMenuController}

import scala.io.StdIn.readLine

object View:

  def sendEvent[T](action: Enumeration, value: Option[T]): Unit = AppController.handle(action, value)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    val actionsMap: Map[Int, Enumeration]
    def draw(): String
    def inputReader() = readLine
    def handleInput(): Unit = sendEvent(actionsMap(inputReader().toInt), Option.empty)

object MainMenu:
  import View.*

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuView extends PageView

  /** A basic implementation of a MainMenuView  */
  class MainMenuViewImpl extends MainMenuView:
    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> MainMenuController.AvailableActions.Select,
      2 -> MainMenuController.AvailableActions.Statistics,
      3 -> MainMenuController.AvailableActions.Settings,
      4 -> MainMenuController.AvailableActions.Quit
    )
    override def draw(): String =
      println("Menu principale:\n1) Gioca\n2) Statistiche\n3) Impostazioni\n4) Esci")
      "MainMenu"

object SelectMenu:
  import View.*

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SelectMenuViewImpl extends SelectMenuView:
    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SelectMenuController.AvailableActions.Back
    )
    override def draw(): String =
      println("Menu selezione:\n1) Menu principale")
      "SelectMenu"

object StatisticsMenu:
  import View.*

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait StatisticsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class StatisticsMenuViewImpl extends StatisticsMenuView:
    override val actionsMap: Map[Int, Enumeration] = Map(1 -> StatisticsMenuController.AvailableActions.Back)
    override def draw(): String =
      println("Menu statistiche:\n1) Menu statistiche")
      "StatisticsMenu"

object SettingsMenu:
  import View.*

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:
    override val actionsMap: Map[Int, Enumeration] = Map(1 -> SettingsMenuController.AvailableActions.Back)
    override def draw(): String =
      println("Menu impostazioni:\n1) Menu principale")
      "SettingsMenu"
