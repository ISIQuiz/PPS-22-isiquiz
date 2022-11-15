package controller

import controller.Controller
import controller.actions.{Action, ParameterlessAction}
import model.{Session, SavedCourse}
import view.View.PageView
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StatisticsMenuView.*
import view.SettingsMenuView.*
import view.StandardGameView.*
import view.AddCourseMenuView.*
import view.AddQuizMenuView.*

/** Controller for the general logic of the application */
object AppController extends Controller :

  private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
  def currentPage: Page[PageController, PageView] = _currentPage
  def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

  // Init var session with a default saved course list
  private var _session: Session = Session()
  def session: Session = _session
  def session_(savedCourses: List[SavedCourse]): Unit = _session = Session.changeSavedCourses(savedCourses)

  case object MainMenu extends ParameterlessAction
  case object SelectMenu extends ParameterlessAction
  case object StatisticsMenu extends ParameterlessAction
  case object SettingsMenu extends ParameterlessAction
  case object StandardGame extends ParameterlessAction
  case object AddCourseMenu extends ParameterlessAction
  case object AddQuizMenu extends ParameterlessAction

  override def handle[T](action: Action[T]): Unit = action match
    case MainMenu => currentPage_(new MainMenuController, MainMenuViewImpl())
    case SelectMenu => currentPage_(new SelectMenuController, SelectMenuViewImpl())
    case StatisticsMenu => currentPage_(new StatisticsMenuController, StatisticsMenuViewImpl())
    case SettingsMenu => currentPage_(new SettingsMenuController, SettingsMenuViewImpl())
    case StandardGame => currentPage_(new StandardGameController, StandardGameViewImpl())
    case AddCourseMenu => currentPage_(new AddCourseMenuController, AddCourseMenuViewImpl())
    case AddQuizMenu => currentPage_(new AddQuizMenuController, AddQuizMenuViewImpl())
    case action: Action[T] => currentPage.pageController.handle(action)
    case _ => throw new IllegalArgumentException

  def startApp(): Unit =
    // TODO Init the session from file: session_(getListFromFile())
    while (true)
      currentPage.pageController.nextIteration()