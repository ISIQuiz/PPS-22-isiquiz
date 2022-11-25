package view

import controller.AppController
import controller.AppController.*
import controller.actions.{Action, ParameterlessAction}
import javafx.event.Event
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import javafx.stage.StageStyle
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Stage
import utils.{TerminalInput, TerminalInputImpl}
import view.terminalUI.{TerminalAddCourseMenu, TerminalAddQuizMenu, TerminalCustomMenu, TerminalMainMenu, TerminalSelectMenu, TerminalSettingsMenu, TerminalStandardGameMenu, TerminalStatisticsMenu}
import view.graphicUI.GraphicMainMenu.*
import view.graphicUI.GraphicMainMenu
import view.graphicUI.GraphicDefaultMenu
import view.updates.ViewUpdate

import scala.io.StdIn.readLine
import scala.collection.mutable.Map
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.util.{Failure, Success}
import scalafx.Includes.jfxScene2sfx
import scalafx.scene.SceneIncludes.jfxScene2sfx
import scalafx.stage.Stage.sfxStage2jfx
import view.View.ViewFactory.GUIType.*

object View:

  private val _scene = Scene(1280, 720)
  private val _basePanel: Pane = Pane()
  _scene.root.value = _basePanel
  private var _stage: PrimaryStage = new JFXApp3.PrimaryStage {
    title.value = "ISIQuiz"
    resizable = false
    scene = _scene
    onCloseRequest = _ => System.exit(0)
//    initStyle(StageStyle.UNDECORATED)
  }

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  object ViewFactory:

    enum GUIType:
      case Terminal
      case ScalaFX

    private var _currentGUIType: GUIType = GUIType.Terminal
    def currentGUIType: GUIType = _currentGUIType
    def currentGUIType_(guiType: GUIType): Unit = _currentGUIType = guiType

    def create[T](page: Action[T]): PageView = page match
      case MainMenuAction => if _currentGUIType == Terminal then {new GraphicDefaultMenu(_basePanel); new TerminalMainMenu()} else new GraphicMainMenu(_basePanel)
      case SelectMenuAction => if _currentGUIType == Terminal then new TerminalSelectMenu() else new GraphicDefaultMenu(_basePanel)
      case StatisticsMenuAction => if _currentGUIType == Terminal then new TerminalStatisticsMenu() else new GraphicDefaultMenu(_basePanel)
      case SettingsMenuAction => if _currentGUIType == Terminal then new TerminalSettingsMenu() else new GraphicDefaultMenu(_basePanel)
      case AddCourseMenuAction => if _currentGUIType == Terminal then new TerminalAddCourseMenu() else new GraphicDefaultMenu(_basePanel)
      case AddQuizMenuAction => if _currentGUIType == Terminal then new TerminalAddQuizMenu() else new GraphicDefaultMenu(_basePanel)
      case CustomMenuAction(_) => if _currentGUIType == Terminal then new TerminalCustomMenu() else new GraphicDefaultMenu(_basePanel)
      case StandardGameAction(_) => if _currentGUIType == Terminal then new TerminalStandardGameMenu() else new GraphicDefaultMenu(_basePanel)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    def updateUI[T](update: ViewUpdate[Any]): Unit
    // TODO: Maybe move handleInput def to PageView trait

  trait GraphicView extends PageView

  trait TerminalView extends PageView:
    /**
     * Map between input and actions of the controllers
     */
    val actionsMap: Map[String, Action[Any]]
    def inputReader() = readLine
    val terminalInput: TerminalInput = TerminalInputImpl()
    def handleInput(): Unit =
      terminalInput.readInput().onComplete( _ match
        case Success(value) =>
          terminalInput.cancellable._2.apply()
            sendEvent(actionsMap(value))
        case Failure(exception) => throw exception
      )
