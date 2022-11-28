package controller

import controller.Controller
import controller.actions.{Action, ParameterlessAction}
import model.GameStage
import model.{SavedCourse, Session}
import utils.Configuration.SavedCoursesFilePath
import utils.{CourseJsonParser, FileHandler}
import view.View.{PageView, TerminalView, ViewFactory}
import view.terminalUI.TerminalMainMenu.*
import view.terminalUI.TerminalSelectMenu.*
import view.terminalUI.TerminalStatisticsMenu.*
import view.terminalUI.TerminalSettingsMenu.*
import view.terminalUI.TerminalStandardGameMenu.*
import view.terminalUI.TerminalAddCourseMenu.*
import view.terminalUI.TerminalAddQuizMenu.*
import view.terminalUI.TerminalCustomMenu.*

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.duration.TimeUnit
import scala.util.Success

/** Controller for the general logic of the application */
object AppController extends Controller:

  private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, ViewFactory.create(MainMenuAction))
  def currentPage: Page[PageController, PageView] = _currentPage
  def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

  // Init var session with a default saved course list
  private var _session: Session = Session()
  def session: Session = _session
  def changeSavedCourses(savedCourses: List[SavedCourse]): Unit = _session = Session.changeSavedCourses(session, savedCourses)

  case object MainMenuAction extends ParameterlessAction
  case object SelectMenuAction extends ParameterlessAction
  case object StatisticsMenuAction extends ParameterlessAction
  case object SettingsMenuAction extends ParameterlessAction
  case object AddCourseMenuAction extends ParameterlessAction
  case object AddQuizMenuAction extends ParameterlessAction
  case class CustomMenuAction[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  case class StandardGameAction[T](override val actionParameter: Option[T]) extends Action(actionParameter)

  override def handle[T](action: Action[T]): Unit = action match
    case MainMenuAction => currentPage_(new MainMenuController, ViewFactory.create(MainMenuAction))
    case SelectMenuAction => currentPage_(new SelectMenuController, ViewFactory.create(SelectMenuAction))
    case StatisticsMenuAction => currentPage_(new StatisticsMenuController, ViewFactory.create(StatisticsMenuAction))
    case SettingsMenuAction => currentPage_(new SettingsMenuController, ViewFactory.create(SettingsMenuAction))
    case StandardGameAction(actionParameter) => currentPage_(new StandardGameController(actionParameter.get.asInstanceOf[GameStage]), ViewFactory.create(StandardGameAction(Option.empty)))
    case AddCourseMenuAction => currentPage_(new AddCourseMenuController, ViewFactory.create(AddCourseMenuAction))
    case AddQuizMenuAction => currentPage_(new AddQuizMenuController, ViewFactory.create(AddQuizMenuAction))
    case CustomMenuAction(actionParameter) => currentPage_(new CustomMenuController(actionParameter.get.asInstanceOf[GameStage]), ViewFactory.create(CustomMenuAction(Option.empty)))
    case action: Action[T] => currentPage.pageController.handle(action)
    case null => throw new IllegalArgumentException

  def startApp(): Unit =
    loadCoursesFromFile()
    val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    scheduler.scheduleAtFixedRate(() => currentPage.pageController.nextIteration(), 0, 1000, TimeUnit.MILLISECONDS)

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
