package view

import controller.AppController
import controller.AppController.MainMenu
import controller.actions.{Action, ParameterlessAction}
import utils.{TerminalInput, TerminalInputImpl}
import view.MainMenuView.MainMenuViewImpl
import view.updates.ViewUpdate

import scala.io.StdIn.readLine
import scala.collection.mutable.Map
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.util.{Failure, Success}

import view.ScalaFX.FXMainMenuView.*

object View:

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  object ViewFactory:
    enum GUIType:
      case Terminal
      case ScalaFX

    private var _currentGUIType: GUIType = GUIType.Terminal
      def currentGUIType: GUIType = _currentGUIType
      def currentGUIType_(guiType: GUIType): Unit = _currentGUIType = guiType
    def create(page: ParameterlessAction): PageView = page match
      case MainMenu => if GUIType == GUIType.Terminal then new MainMenuViewImpl() else new FXMainMenuViewImpl()

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    def updateUI[T](update: ViewUpdate[Any]): Unit
    // TODO: Maybe move handleInput def to PageView trait

  trait ScalaFXView extends PageView

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
