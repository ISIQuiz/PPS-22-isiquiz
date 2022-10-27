package controller

import view.View.*
import view.MainMenu.*
import view.SettingsMenu.*

object Controller:

  trait PageController

  class MainMenuController extends PageController

  class SettingsController extends PageController

  enum ApplicationControllerAction:
    case SETTINGS
  trait ApplicationController:
    def currentPage: Page[PageController, PageView]
    def currentPage_(pageController: PageController, pageView: PageView): Unit
    def handle(action: ApplicationControllerAction): Unit

  case class Page[C, V](var pageController: C, var pageView: V)

  class ApplicationControllerImpl(private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](MainMenuController(), MainMenuViewImpl())) extends ApplicationController :
    override def currentPage = _currentPage
    override def currentPage_(pageController: PageController, pageView: PageView) = _currentPage = Page[PageController, PageView](pageController, pageView)
    override def handle(action: ApplicationControllerAction): Unit = action match
      case ApplicationControllerAction.SETTINGS => currentPage_(SettingsController(), SettingsMenuViewImpl())
    currentPage.pageView.draw()
