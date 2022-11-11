package controller

import controller.Controller.{AppController, PageController}
import view.View

/** Companion object of main menu controller */
object MainMenuController:

  enum AvailableActions extends Enumeration:
    case Select
    case Statistics
    case Settings
    case Quit

/** Defines the logic of the main menu page */
class MainMenuController extends PageController:

  import AppController.AvailablePages
  import MainMenuController.*

  override def updateUI[T](update: View.UIUpdate[T]): Unit  =
    AppController.currentPage.pageView.draw(update)
    AppController.currentPage.pageView.handleInput()
  override def nextIteration(): Unit = updateUI(Option.empty)

  override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
    case AvailableActions.Select => openSelectMenu
    case AvailableActions.Statistics => openStatisticsMenu
    case AvailableActions.Settings => openSettingsMenu
    case AvailableActions.Quit => quitGame

  def openSelectMenu: Unit = AppController.handle(AvailablePages.SelectMenu, Option.empty)

  def openStatisticsMenu: Unit = AppController.handle(AvailablePages.StatisticsMenu, Option.empty)

  def openSettingsMenu: Unit = AppController.handle(AvailablePages.SettingsMenu, Option.empty)

  def quitGame: Unit = System.exit(0)