package controller

import controller.MainMenuController.*
import controller.SelectMenuController.*
import controller.StatisticsMenuController.*
import controller.SettingsMenuController.*
import view.View.*
import view.MainMenuView.*
import view.SelectMenu.*
import view.StatisticsMenu.*
import view.SettingsMenu.*

object Controller:

  /** Trait of a generic controller that should handle actions with their optional values */
  trait Controller:
    def handle[T](action: Enumeration, value: Option[T]): Unit

  /** PageController should include all behaviours common between different pages controllers */
  trait PageController extends Controller

  /** Provides a binder between the page logic and the relative page view */
  case class Page[C, V](var pageController: C, var pageView: V)

  /**  Controller for the general logic of the application */
  object AppController extends Controller:

    private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
    def currentPage: Page[PageController, PageView] = _currentPage
    def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

    enum AvailablePages extends Enumeration:
      case MainMenu
      case SelectMenu
      case SettingsMenu
      case StatisticsMenu

    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailablePages.MainMenu =>  currentPage_(new MainMenuController, MainMenuViewImpl())
      case AvailablePages.SelectMenu => currentPage_(new SelectMenuController, SelectMenuViewImpl())
      case AvailablePages.StatisticsMenu => currentPage_(new StatisticsMenuController, StatisticsMenuViewImpl())
      case AvailablePages.SettingsMenu => currentPage_(new SettingsMenuController, SettingsMenuViewImpl())
      case _ => currentPage.pageController.handle(action, value)

    def startApp():Unit =
      while(true)
        currentPage.pageView.draw()
        currentPage.pageView.handleInput()
