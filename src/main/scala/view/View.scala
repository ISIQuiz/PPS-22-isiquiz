package view

import controller.AppController
import controller.actions.Action
import utils.{TerminalInput, TerminalInputImpl}
import view.updates.ViewUpdate

import scala.io.StdIn.readLine
import scala.collection.mutable.Map
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.util.{Failure, Success}

object View:

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    val actionsMap: Map[String, Action[Any]]
    def updateUI[T](update: ViewUpdate[T]): String
    def inputReader() = readLine
    val terminalInput: TerminalInput = TerminalInputImpl()
    def handleInput(): Unit =
      terminalInput.readInput().onComplete( _ match
          case Success(value) =>
            terminalInput.cancellable._2.apply()
            sendEvent(actionsMap(value))
          case Failure(exception) => throw exception
      )

//      val input = terminalInput.readInput()._1.result(sendEvent(actionsMap(terminalInput.readInput()._1.result)))
