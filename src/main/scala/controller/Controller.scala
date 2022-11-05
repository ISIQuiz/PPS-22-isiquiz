package controller

import view.View.*
import view.MainMenu.*
import view.SelectMenu.*
import view.StatisticsMenu.*
import view.SettingsMenu.*

object Controller:

  trait Controller:
    def handle[T](action: Enumeration, value: Option[T]): Unit

  /** Provides a binder between the page logic and the relative page view */
  case class Page[C, V](var pageController: C, var pageView: V)

  /**  Controller for the general logic of the application */
  object AppController extends Controller:
    private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
    def currentPage: Page[PageController, PageView] = _currentPage
    def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)
    enum AvailablePages extends Enumeration:
      case MainMenu
      case Select
      case Settings
      case Statistics
    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailablePages.MainMenu =>  currentPage_(new MainMenuController, MainMenuViewImpl())
      case AvailablePages.Select => currentPage_(new SelectMenuController, SelectMenuViewImpl())
      case AvailablePages.Statistics => currentPage_(new StatisticsMenuController, StatisticsMenuViewImpl())
      case AvailablePages.Settings => currentPage_(new SettingsMenuController, SettingsMenuViewImpl())
      case _ => currentPage.pageController.handle(action, value)
    def startApp():Unit =
      while(true)
        currentPage.pageView.draw()
        currentPage.pageView.handleInput()

  /** PageController should include all behaviours common between different pages controllers */
  trait PageController extends Controller

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
    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailableActions.Select => AppController.handle(AvailablePages.Select, Option.empty)
      case AvailableActions.Statistics => AppController.handle(AvailablePages.Statistics, Option.empty)
      case AvailableActions.Settings => AppController.handle(AvailablePages.Settings, Option.empty)
      case AvailableActions.Quit => System.exit(0)

  /** Companion object of select menu controller */
  object SelectMenuController:
    enum AvailableActions extends Enumeration:
      case Back
  /** Defines the logic of the select page */
  class SelectMenuController extends PageController:
    import AppController.AvailablePages
    import SelectMenuController.*
    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailableActions.Back => AppController.handle(AvailablePages.MainMenu, Option.empty)

  /** Companion object of statistics menu controller */
  object StatisticsMenuController:
    enum AvailableActions extends Enumeration :
      case Back
  /** Defines the logic of the statistics page */
  class StatisticsMenuController extends PageController :
    import AppController.AvailablePages
    import StatisticsMenuController.*
    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailableActions.Back => AppController.handle(AvailablePages.MainMenu, Option.empty)

  /** Companion object of settings menu controller */
  object SettingsMenuController:
    enum AvailableActions extends Enumeration:
      case Back
  /** Defines the logic of the settings page */
  class SettingsMenuController extends PageController:
    import AppController.AvailablePages
    import SettingsMenuController.*
    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailableActions.Back => AppController.handle(AvailablePages.MainMenu, Option.empty)
