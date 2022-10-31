package controller

import view.View.*
import view.MainMenu.*
import view.SettingsMenu.*

object Controller:

  /** PageController should include all behaviours common between different pages controllers */
  trait PageController

  /** Defines the logic of the main menu page */
  class MainMenuController extends PageController

  /** Defines the logic of the settings page */
  class SettingsController extends PageController

  /** Generic application action with a descriptive name and an optional value */
  case class Action[T](var actionName: String, var actionValue: Option[T])

  /** Provides a binder between the page logic and the relative page view */
  case class Page[C, V](var pageController: C, var pageView: V)

  /**  Controller for the general logic of the application */
  trait ApplicationController:
    def currentPage: Page[PageController, PageView]
    def currentPage_(pageController: PageController, pageView: PageView): Unit
    def handle[T](action: Action[T]): Unit

  /** Extends ApplicationController trait providing the current page data and a generic handler for the application
   * Application controller object used as a bridge with Application view
   */
  object ApplicationControllerImpl extends ApplicationController:
    private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
    override def currentPage: Page[PageController, PageView] = _currentPage
    override def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)
    override def handle[T](action: Action[T]): Unit = action match
      case Action("Settings", _) => currentPage_(new SettingsController, SettingsMenuViewImpl())
      // TODO: Add custom exception for action not available
      case _ => ???
    currentPage.pageView.draw()
