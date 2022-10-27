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

  /** Enumerates all the possible actions that happens in the general application
   * These actions could be, for example, changing a page, quitting the application, etc.
   */
  enum ApplicationControllerAction:
    case SETTINGS

  /** Provides a binder between the page logic and the relative page view */
  case class Page[C, V](var pageController: C, var pageView: V)

  /**  Controller for the general logic of the application */
  trait ApplicationController:
    def currentPage: Page[PageController, PageView]
    def currentPage_(pageController: PageController, pageView: PageView): Unit
    def handle(action: ApplicationControllerAction): Unit

  /** Extends ApplicationController trait
   * @constructor creates a new current page which, by default, is the main menu page
   * @param _currentPage the current page of the application
   */
  class ApplicationControllerImpl(private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](MainMenuController(), MainMenuViewImpl())) extends ApplicationController :
    override def currentPage = _currentPage
    override def currentPage_(pageController: PageController, pageView: PageView) = _currentPage = Page[PageController, PageView](pageController, pageView)
    override def handle(action: ApplicationControllerAction): Unit = action match
      case ApplicationControllerAction.SETTINGS => currentPage_(SettingsController(), SettingsMenuViewImpl())
    currentPage.pageView.draw()
