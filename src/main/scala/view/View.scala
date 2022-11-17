package view

import controller.AppController
import controller.actions.Action
import view.updates.ViewUpdate

import scala.io.StdIn.readLine
import scala.collection.mutable.Map

object View:

  def sendEvent[T](action: Action[T]): Unit = AppController.handle(action)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    val actionsMap: Map[String, Action[Any]]
    def draw[T](update: ViewUpdate[T]): String
    def inputReader() = readLine
    def handleInput(): Unit =
      val input = inputReader()
      sendEvent(actionsMap(input))
