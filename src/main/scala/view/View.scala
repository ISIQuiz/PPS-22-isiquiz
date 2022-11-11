package view

import controller.Controller.AppController
import scala.io.StdIn.readLine

object View:

  def sendEvent[T](action: Enumeration, value: Option[T]): Unit = AppController.handle(action, value)

  case class UIUpdate[T](updateType: Enumeration, updateValue: Option[T])

  /** PageView should include all behaviours common between different pages views */
  trait PageView:

    val actionsMap: Map[Int, Enumeration]
    def draw[T](update: UIUpdate[T]): String
    def inputReader() = readLine
    def handleInput(): Unit =
      val input = inputReader()
      sendEvent(actionsMap(input.toInt), Option(input))
