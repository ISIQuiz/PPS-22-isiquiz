package view

import controller.CustomMenuController
import controller.CustomMenuController.*
import controller.actions.Action
import model.settings.{GameSettings, StandardGameSettings}
import view.View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object CustomMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** CustomMenuView define aspects of a general CustomMenuView */
  trait CustomMenuView extends PageView

  /** A basic implementation of a CustomMenuView  */
  class CustomMenuViewImpl extends CustomMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "M" -> Back
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
      case DefaultUpdate =>
        println("Menu impostazioni personalizzate:\nM) Menu principale")

        def checkControls(control: String): Unit =
          if actionsMap.contains(control) then
            sendEvent(actionsMap.get(control).get)

        print("Tempo massimo per domanda: ")
        val quizMaxTime = readLine()
        checkControls(quizMaxTime)
        print("Numero di domande: ")
        val maxQuizzes = readLine()
        checkControls(maxQuizzes)
        print("Numero massimo di aiuti: ")
        val helpsNumber = readLine()
        checkControls(helpsNumber)
        val gameSettings: GameSettings = StandardGameSettings(quizMaxTime.toInt, maxQuizzes.toInt, helpsNumber.toInt)
        sendEvent(NewGameSettings(Option(gameSettings)))
