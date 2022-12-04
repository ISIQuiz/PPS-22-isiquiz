package view.graphicUI

import controller.AppController
import controller.StatisticsMenuController.Back
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.stage.Stage
import javafx.scene.control.{Button, Label}
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
  var backButton: Button = _
  
  @FXML
  var totalScoreLabel: Label = _

  @FXML
  var totalAnsweredQuestionsLabel: Label = _

  @FXML
  var totalCorrectAnswersLabel: Label = _

  @FXML
  var totalAnswerPrecisionLabel: Label = _

  @FXML
  var totalAverageTimeAnswerLabel: Label = _


  @FXML
  def mainMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "statistics_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      Platform.runLater { () =>
        totalScoreLabel.setText(AppController.session.playerStats.totalScore.toString)
        totalAnsweredQuestionsLabel.setText(AppController.session.playerStats.totalAnsweredQuestions.toString)
        totalCorrectAnswersLabel.setText(AppController.session.playerStats.totalCorrectAnswers.toString)
        totalAnswerPrecisionLabel.setText(AppController.session.playerStats.totalAnswerPrecision.toString + " %")
        totalAverageTimeAnswerLabel.setText(AppController.session.playerStats.totalAverageTimeAnswer.toString)
      }


