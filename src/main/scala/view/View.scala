package view

import controller.AppController
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import javafx.event.Event
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import javafx.stage.StageStyle
import scalafx.application.{JFXApp3, Platform}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Stage
import utils.{TerminalInput, TerminalInputImpl}
import view.terminalUI.{TerminalAddCourseMenu, TerminalAddQuizMenu, TerminalCustomMenu, TerminalMainMenu, TerminalSelectMenu, TerminalSettingsMenu, TerminalStandardGameMenu, TerminalStatisticsMenu}
import view.graphicUI.GraphicMainMenu.*
import view.graphicUI.{GraphicDefaultMenu, GraphicMainMenu, GraphicSelectMenu, GraphicStandardGameMenu}
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

  private val _scene = Scene(1280, 720)
  private val _basePanel: Pane = Pane()
  _scene.root.value = _basePanel
  private var _stage: PrimaryStage = new JFXApp3.PrimaryStage {
    title.value = "ISIQuiz"
    resizable = false
    scene = _scene
    onCloseRequest = _ => System.exit(0)
  }

  def basePanel(): Pane = _basePanel

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  object ViewFactory:

    enum GUIType:
      case Terminal
      case ScalaFX

    private var _currentGUIType: GUIType = GUIType.ScalaFX
    def currentGUIType: GUIType = _currentGUIType
    def currentGUIType_(guiType: GUIType): Unit = _currentGUIType = guiType

    def create[T](page: Action[T]): PageView = page match
      case MainMenuAction => if _currentGUIType == Terminal then new TerminalMainMenu() else new GraphicMainMenu(basePanel())
      case SelectMenuAction => if _currentGUIType == Terminal then new TerminalSelectMenu() else new GraphicSelectMenu(basePanel())
      case StatisticsMenuAction => if _currentGUIType == Terminal then new TerminalStatisticsMenu() else new GraphicDefaultMenu(basePanel())
      case SettingsMenuAction => if _currentGUIType == Terminal then new TerminalSettingsMenu() else new GraphicDefaultMenu(basePanel())
      case AddCourseMenuAction => if _currentGUIType == Terminal then new TerminalAddCourseMenu() else new GraphicDefaultMenu(basePanel())
      case AddQuizMenuAction => if _currentGUIType == Terminal then new TerminalAddQuizMenu() else new GraphicDefaultMenu(basePanel())
      case CustomMenuAction(_) => if _currentGUIType == Terminal then new TerminalCustomMenu() else new GraphicDefaultMenu(basePanel())
      case StandardGameAction(_) => if _currentGUIType == Terminal then new TerminalStandardGameMenu() else new GraphicStandardGameMenu(basePanel())

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