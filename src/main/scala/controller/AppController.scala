package controller

import controller.Controller
import controller.actions.{Action, ParameterlessAction}
import javafx.stage.Stage
import model.GameStage
import model.stats.PlayerStats.PlayerStats
import model.SavedCourse.SavedCourse
import utils.storage.ImportHandler.importSessionFromPersonalDirectory
import view.View
import view.View.{PageView, TerminalView, ViewFactory}
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.duration.TimeUnit
import scala.util.{Failure, Success}

/** Controller for the general logic of the application */
object AppController extends Controller:

  private var _currentPage: Page[PageController, PageView] = Page[PageController, PageView](new MainMenuController, ViewFactory.create(MainMenuAction))
  def currentPage: Page[PageController, PageView] = _currentPage
  def currentPage_(pageController: PageController, pageView: PageView): Unit = _currentPage = Page[PageController, PageView](pageController, pageView)

  import model.Session.*

  private var _session: Session = Session()
  def session: Session = _session

  import model.Session
  def changeSavedCourses(savedCourses: List[SavedCourse]): Unit = _session = Session.changeSavedCourses(session, savedCourses)
  def changePlayerStats(playerStats: PlayerStats): Unit = _session = Session.changePlayerStats(session, playerStats)

  case object MainMenuAction extends ParameterlessAction
  case object SelectMenuAction extends ParameterlessAction
  case object StatisticsMenuAction extends ParameterlessAction
  case object SettingsMenuAction extends ParameterlessAction
  case object AddCourseMenuAction extends ParameterlessAction
  case object AddQuizMenuAction extends ParameterlessAction
  case object EditCourseMenuAction extends ParameterlessAction
  case object EditQuizMenuAction extends ParameterlessAction
  case class ReviewMenuAction(override val actionParameter: Option[GameStage]) extends Action(actionParameter)
  case class CustomMenuAction[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  case class StandardGameAction[T](override val actionParameter: Option[T]) extends Action(actionParameter)
  case class BlitzGameAction[T](override val actionParameter: Option[T]) extends Action(actionParameter)

  override def handle[T](action: Action[T]): Unit = action match
    case MainMenuAction => currentPage_(new MainMenuController, ViewFactory.create(MainMenuAction))
    case SelectMenuAction => currentPage_(new SelectMenuController, ViewFactory.create(SelectMenuAction))
    case StatisticsMenuAction => currentPage_(new StatisticsMenuController, ViewFactory.create(StatisticsMenuAction))
    case SettingsMenuAction => currentPage_(new SettingsMenuController, ViewFactory.create(SettingsMenuAction))
    case StandardGameAction(actionParameter) => currentPage_(StandardGameController(actionParameter.get.asInstanceOf[GameStage]), ViewFactory.create(StandardGameAction(Option.empty)))
    case BlitzGameAction(actionParameter) => currentPage_(BlitzGameController(actionParameter.get.asInstanceOf[GameStage]), ViewFactory.create(BlitzGameAction(Option.empty)))
    case AddCourseMenuAction => currentPage_(new AddCourseMenuController, ViewFactory.create(AddCourseMenuAction))
    case AddQuizMenuAction => currentPage_(new AddQuizMenuController, ViewFactory.create(AddQuizMenuAction))
    case EditCourseMenuAction => currentPage_(new EditCourseMenuController, ViewFactory.create(EditCourseMenuAction))
    case EditQuizMenuAction => currentPage_(new EditQuizMenuController, ViewFactory.create(EditQuizMenuAction))
    case ReviewMenuAction(actionParameter) => currentPage_(new ReviewMenuController(actionParameter.get), ViewFactory.create(ReviewMenuAction(Option.empty)))
    case CustomMenuAction(actionParameter) => currentPage_(new CustomMenuController(actionParameter.get.asInstanceOf[GameStage]), ViewFactory.create(CustomMenuAction(Option.empty)))
    case action: Action[T] => currentPage.pageController.handle(action)
    case null => throw new IllegalArgumentException

  def startApp(): Unit =

    importSessionFromPersonalDirectory(session) match
      case Success(s) => _session = s
      case Failure(f) => Failure(f)

    val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    scheduler.scheduleAtFixedRate(() => currentPage.pageController.nextIteration(), 0, 1000/10, TimeUnit.MILLISECONDS)