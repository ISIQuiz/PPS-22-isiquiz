package view

import controller.AppController
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import javafx.event.Event
import javafx.fxml.FXMLLoader
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.StageStyle
import scalafx.application.{JFXApp3, Platform}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Stage
import utils.{TerminalInput, TerminalInputImpl}
import view.terminalUI.{TerminalAddCourseMenu, TerminalAddQuizMenu, TerminalCustomMenu, TerminalEditCourseMenu, TerminalEditQuizMenu, TerminalMainMenu, TerminalSelectMenu, TerminalSettingsMenu, TerminalStandardGameMenu, TerminalStatisticsMenu}
import view.graphicUI.GraphicMainMenu.*
import view.graphicUI.{GraphicAddCourseMenu, GraphicAddQuizMenu, GraphicCustomMenu, GraphicDefaultMenu, GraphicEditCourseMenu, GraphicEditQuizMenu, GraphicMainMenu, GraphicReviewMenu, GraphicSelectMenu, GraphicSettingsMenu, GraphicStandardGameMenu, GraphicStatisticsMenu}
import view.updates.ViewUpdate

import scala.io.StdIn.readLine
import scala.collection.mutable.Map
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scalafx.Includes.jfxScene2sfx
import scalafx.scene.SceneIncludes.jfxScene2sfx
import scalafx.stage.Stage.sfxStage2jfx
import view.View.ViewFactory.GUIType.*

import scala.collection.mutable

object View:

  private val _stage: Stage = PrimaryStage()
  private val _scene = Scene(1280, 720)
  private val _basePanel: Pane = Pane()
  _stage.title = "ISIQuiz"
  _stage.resizable = false
  _stage.scene = _scene
  _stage.onCloseRequest = _ => System.exit(0)
  _stage.getIcons.add(new Image(View.getClass.getResourceAsStream("/images/favicon_265_text.png")));
  _scene.root.value = _basePanel

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  object ViewFactory:

    enum GUIType:
      case Terminal
      case ScalaFX

    private val _currentGUIType: GUIType = GUIType.ScalaFX

    def create[T](page: Action[T]): PageView = page match
      case MainMenuAction => _currentGUIType match
        case Terminal => TerminalMainMenu()
        case ScalaFX => GraphicMainMenu(_stage)
      case SelectMenuAction => _currentGUIType match
        case Terminal => TerminalSelectMenu()
        case ScalaFX => GraphicSelectMenu(_stage)
      case StatisticsMenuAction => _currentGUIType match
        case Terminal => TerminalStatisticsMenu()
        case ScalaFX => GraphicStatisticsMenu(_stage)
      case SettingsMenuAction => _currentGUIType match
        case Terminal => TerminalSettingsMenu()
        case ScalaFX => GraphicSettingsMenu(_stage)
      case AddCourseMenuAction => _currentGUIType match
        case Terminal => TerminalAddCourseMenu()
        case ScalaFX => GraphicAddCourseMenu(_stage)
      case AddQuizMenuAction => _currentGUIType match
        case Terminal => TerminalAddQuizMenu()
        case ScalaFX => GraphicAddQuizMenu(_stage)
      case EditCourseMenuAction => _currentGUIType match
        case Terminal => TerminalEditCourseMenu()
        case ScalaFX => GraphicEditCourseMenu(_stage)
      case EditQuizMenuAction => _currentGUIType match
        case Terminal => TerminalEditQuizMenu()
        case ScalaFX => GraphicEditQuizMenu(_stage)
      case ReviewMenuAction(_) => _currentGUIType match
        case Terminal => throw new IllegalArgumentException
        case ScalaFX => GraphicReviewMenu(_stage)
      case CustomMenuAction(_) => _currentGUIType match
        case Terminal => TerminalCustomMenu()
        case ScalaFX => GraphicCustomMenu(_stage)
      case StandardGameAction(_) => _currentGUIType match
        case Terminal => TerminalStandardGameMenu()
        case ScalaFX => GraphicStandardGameMenu(_stage)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    def updateUI[T](update: ViewUpdate[Any]): Unit

  trait GraphicView extends PageView

  trait TerminalView extends PageView:
    /**
     * Map between input and actions of the controllers
     */
    val actionsMap: mutable.Map[String, Action[Any]]
    def inputReader(): String = readLine
    var terminalInput: TerminalInput = TerminalInputImpl()

    def handleInput(): Unit =
      val input: Future[String] = Future(readLine())
      input.onComplete(_ match
        case Success(result) =>
          Platform.runLater(() => sendEvent(actionsMap(result)));
          handleInput()
        case Failure(_) =>
          handleInput()
      )
    handleInput()