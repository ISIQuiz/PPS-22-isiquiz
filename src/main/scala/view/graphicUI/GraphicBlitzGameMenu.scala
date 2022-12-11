package view.graphicUI

import controller.BlitzGameController.{Back, NextQuiz, SelectAnswer}
import controller.{AppController, BlitzGameController}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, ProgressBar}
import javafx.stage.Stage
import model.GameStage
import model.settings.StandardGameSettings
import utils.GUILoader.loadGUI
import utils.{GUILoader, Timer}
import view.StandardGameMenuView.*
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicBlitzGameMenu

/** Standard Game menu graphic interface */
class GraphicBlitzGameMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var courseLabel: Label = _

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
  def firstAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(0)))

  @FXML
  def secondAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(1)))

  @FXML
  def thirdAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(2)))

  @FXML
  def fourthAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(3)))

  @FXML
  def nextButtonClicked(): Unit =
    sendEvent(NextQuiz)

  loadGUI(stage, this, "blitz_game.fxml")

  val answerButtons: List[Button] = List(firstAnswerButton, secondAnswerButton, thirdAnswerButton, fourthAnswerButton)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate => {}
    case CurrentGameUpdate(updateParameter) =>
      Platform.runLater(() => {
        val gameStage: GameStage = updateParameter.get
        courseLabel.setText(gameStage.quizInGame.course.courseId.courseName)
        quizLabel.setText(gameStage.quizInGame.quiz.question)
        pointsLabel.setText(gameStage.quizInGame.quiz.maxScore + " punti")
        firstAnswerButton.setText(gameStage.quizInGame.answers(0).text)
        secondAnswerButton.setText(gameStage.quizInGame.answers(1).text)
        thirdAnswerButton.setText(gameStage.quizInGame.answers(2).text)
        fourthAnswerButton.setText(gameStage.quizInGame.answers(3).text)
      })
    case TimerUpdate(updateParameter) =>
      val timer: Timer = updateParameter.get
      Platform.runLater(() => {
        timeRemainingLabel.setText(s"${timer.getRemainingTime.toInt}")
        timeProgressBar.setProgress(timer.getCompletionPercentage)
      })
