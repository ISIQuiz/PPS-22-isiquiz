package view.graphicUI

import controller.{AppController, StandardGameController}
import controller.StandardGameController.Back
import javafx.application.Platform
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, ProgressBar}
import javafx.stage.Stage
import model.settings.StandardGameSettings

object GraphicStandardGameMenu

/** Standard Game menu graphic interface  */
class GraphicStandardGameMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var courseLabel: Label = _

  @FXML
  var quizNumberLabel: Label = _

  @FXML
  var timeRemainingLabel: Label = _

  @FXML
  var pointsLabel: Label = _

  @FXML
  var timeProgressBar: ProgressBar = _

  @FXML
  var quizLabel: Label = _

  @FXML
  var firstAnswerButton: Button = _

  @FXML
  var secondAnswerButton: Button = _

  @FXML
  var thirdAnswerButton: Button = _

  @FXML
  var fourthAnswerButton: Button = _

  @FXML
  var nextButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def firstAnswerButtonClicked(): Unit = ???

  @FXML
  def secondAnswerButtonClicked(): Unit = ???

  @FXML
  def thirdAnswerButtonClicked(): Unit = ???

  @FXML
  def fourthAnswerButtonClicked(): Unit = ???

  @FXML
  def nextButtonClicked(): Unit = ???

  loadGUI(stage, this, "standard_game.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("Updating")
    Platform.runLater(() => {
      timeRemainingLabel.setText(AppController.currentPage.pageController.asInstanceOf[StandardGameController].gameStage.gameSettings.asInstanceOf[StandardGameSettings].quizMaxTime.toString)
    })
