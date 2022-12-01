package view.graphicUI

import controller.AppController
import controller.StatisticsMenuController.Back
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.stage.Stage
import javafx.scene.control.Label
import model.stats.PlayerStats
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import view.StatisticsMenuView.*

object GraphicStatisticsMenu

/** Statistics menu graphic interface */
class GraphicStatisticsMenu(stage: Stage) extends GraphicView :

  @FXML
  var totalScoreLabel: Label = _

  @FXML
  var totalAnsweredQuestionsLabel: Label = _

  @FXML
  var totalCorrectAnswersLabel: Label = _

  @FXML
  var answerPrecisionLabel: Label = _

  @FXML
  var averageTimeAnswerLabel: Label = _


  @FXML
  def mainMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "statistics_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      Platform.runLater { () =>
        totalScoreLabel.setText(AppController.session.playerStats.totalScore.toString)
        totalAnsweredQuestionsLabel.setText(PlayerStats.calculateTotalAnsweredQuestions(AppController.session.playerStats).toString)
        totalCorrectAnswersLabel.setText(PlayerStats.calculateTotalCorrectAnswer(AppController.session.playerStats).toString)
        answerPrecisionLabel.setText(PlayerStats.calculateAnswerPrecision(AppController.session.playerStats).toString + " %")
        averageTimeAnswerLabel.setText(PlayerStats.calculateAverageTimeAnswer(AppController.session.playerStats).toString)
      }


