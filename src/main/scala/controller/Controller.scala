package controller

import controller.Controller.AppController.currentPage
import controller.MainMenuController.*
import controller.SelectMenuController.*
import controller.StatisticsMenuController.*
import controller.SettingsMenuController.*
import model.{SavedCourse, Session}
import resources.SampleCourseList
import controller.AddCourseMenuController.*
import controller.AddQuizMenuController.*
import view.View.*
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StatisticsMenuView.*
import view.SettingsMenuView.*
import view.StandardGameView.*
import view.AddCourseMenuView.*
import view.AddQuizMenuView.*

object Controller:

  /** Trait of a generic controller that should handle actions with their optional values */
  trait Controller:
    def handle[T](action: Enumeration, value: Option[T]): Unit

  /** PageController should include all behaviours common between different pages controllers */
  trait PageController extends Controller:
    def nextIteration(): Unit
    def updateUI[T](update: Option[T]): Unit

  /** Provides a binder between the page logic and the relative page view */
  case class Page[C, V](var pageController: C, var pageView: V)

  /**  Controller for the general logic of the application */
  object AppController extends Controller:

    private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
    def currentPage: Page[PageController, PageView] = _currentPage
    def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

    // Init var session with a default saved course list
    private var _session: Session = Session()
    def session: Session = _session
    def session_(savedCourses: List[SavedCourse]): Unit = _session = Session.changeSavedCourses(savedCourses)
    
    enum AvailablePages extends Enumeration:
      case MainMenu
      case SelectMenu
      case SettingsMenu
      case StatisticsMenu
      case StandardGame
      case AddCourseMenu
      case AddQuizMenu

    override def handle[T](action: Enumeration, value: Option[T]): Unit = action match
      case AvailablePages.MainMenu =>  currentPage_(new MainMenuController, MainMenuViewImpl())
      case AvailablePages.SelectMenu => currentPage_(new SelectMenuController, SelectMenuViewImpl())
      case AvailablePages.StatisticsMenu => currentPage_(new StatisticsMenuController, StatisticsMenuViewImpl())
      case AvailablePages.SettingsMenu => currentPage_(new SettingsMenuController, SettingsMenuViewImpl())
      case AvailablePages.StandardGame => currentPage_(new StandardGameController, StandardGameViewImpl())
      case AvailablePages.AddCourseMenu => currentPage_(new AddCourseMenuController, AddCourseMenuViewImpl())
      case AvailablePages.AddQuizMenu => currentPage_(new AddQuizMenuController, AddQuizMenuViewImpl())
      case _ => currentPage.pageController.handle(action, value)

    def startApp():Unit =
      // TODO Init the session from file: session_(getListFromFile())
      while(true)
        currentPage.pageController.nextIteration()
