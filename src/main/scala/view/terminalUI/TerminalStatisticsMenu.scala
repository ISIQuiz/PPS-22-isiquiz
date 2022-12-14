package view.terminalUI

import controller.{AppController, StatisticsMenuController}
import controller.StatisticsMenuController.*
import controller.actions.Action
import view.View.TerminalView
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.Map

object TerminalStatisticsMenu:

  case object DefaultUpdate extends ParameterlessViewUpdate

/** Statistics menu terminal interface */
class TerminalStatisticsMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("Menu statistiche:\n1) Menu principale")
    println("Totale punti: " + AppController.session.playerStats.totalScore.toString)
    println("Totale domande risposte: " + AppController.session.playerStats.totalAnsweredQuestions.toString)
    println("Totale risposte corrette: " + AppController.session.playerStats.totalCorrectAnswers.toString)
    println("Precisione: " + AppController.session.playerStats.totalAnswerPrecision.toString + " %")
    println("Tempo medio di risposta: " + AppController.session.playerStats.totalAverageTimeAnswer.toString)
