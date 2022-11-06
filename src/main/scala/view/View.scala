package view

import controller.Controller.AppController
import scala.io.StdIn.readLine

object View:

  def sendEvent[T](action: Enumeration, value: Option[T]): Unit = AppController.handle(action, value)

  /** PageView should include all behaviours common between different pages views */
  trait PageView:
    val actionsMap: Map[Int, Enumeration]
    def draw(): String
    def inputReader() = readLine
    def handleInput(): Unit = sendEvent(actionsMap(inputReader().toInt), Option.empty)
