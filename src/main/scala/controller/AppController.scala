package controller

import controller.Controller
import controller.actions.{Action, ParameterlessAction}
import model.GameStage
import model.{SavedCourse, Session}
import utils.Configuration.SavedCoursesFilePath
import utils.{CourseJsonParser, FileHandler}
import view.View.PageView
import view.MainMenuView.*
import view.SelectMenuView.*
import view.StatisticsMenuView.*
import view.SettingsMenuView.*
import view.StandardGameView.*
import view.AddCourseMenuView.*
import view.AddQuizMenuView.*
import view.CustomMenuView.*
import scala.util.Success

/** Controller for the general logic of the application */
object AppController extends Controller :

  private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, MainMenuViewImpl())
  def currentPage: Page[PageController, PageView] = _currentPage
  def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

  // Init var session with a default saved course list
  private var _session: Session = Session()
  def session: Session = _session
  def changeSavedCourses(savedCourses: List[SavedCourse]): Unit = _session = Session.changeSavedCourses(session, savedCourses)

  case object MainMenu extends ParameterlessAction
  case object SelectMenu extends ParameterlessAction
  case object StatisticsMenu extends ParameterlessAction
  case object SettingsMenu extends ParameterlessAction
  case object AddCourseMenu extends ParameterlessAction
  case object AddQuizMenu extends ParameterlessAction
  case class CustomMenu[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  case class StandardGame[T](override val actionParameter: Option[T]) extends Action(actionParameter)

  override def handle[T](action: Action[T]): Unit = action match
    case MainMenu => currentPage_(new MainMenuController, MainMenuViewImpl())
    case SelectMenu => currentPage_(new SelectMenuController, SelectMenuViewImpl())
    case StatisticsMenu => currentPage_(new StatisticsMenuController, StatisticsMenuViewImpl())
    case SettingsMenu => currentPage_(new SettingsMenuController, SettingsMenuViewImpl())
    case StandardGame(actionParameter) => currentPage_(new StandardGameController(actionParameter.get.asInstanceOf[GameStage]), StandardGameViewImpl())
    case AddCourseMenu => currentPage_(new AddCourseMenuController, AddCourseMenuViewImpl())
    case AddQuizMenu => currentPage_(new AddQuizMenuController, AddQuizMenuViewImpl())
    case CustomMenu(actionParameter) => currentPage_(new CustomMenuController(actionParameter.get.asInstanceOf[GameStage]), CustomMenuViewImpl())
    case action: Action[T] => currentPage.pageController.handle(action)
    case null => throw new IllegalArgumentException

  def startApp(): Unit =
    loadCoursesFromFile()
    currentPage.pageController.nextIteration()

  // Read courses list from a JSON file and deserialize it
  def loadCoursesFromFile(): Unit =
    val fileHandler = FileHandler()
    val courseJsonParser = CourseJsonParser()

    // Read resource file
    fileHandler.readResource(SavedCoursesFilePath) match
      case Success(jsonString: String) =>
        // Deserialize the JSON string
        courseJsonParser.deserializeSavedCourses(jsonString) match
          case Success(savedCourses: List[SavedCourse]) => changeSavedCourses(savedCourses)
          case _ =>
            //println("ERROR DESERIALIZE JSON")
            //IllegalArgumentException()
      case _ =>
        //println("ERROR FIND FILE JSON")
        //FileNotFoundException()
