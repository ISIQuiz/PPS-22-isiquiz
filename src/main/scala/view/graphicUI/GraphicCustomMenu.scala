package view.graphicUI

import controller.{AppController, CustomMenuController}
import controller.CustomMenuController.{Back, Start}
import javafx.application.Platform
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control
import javafx.scene.control.{Button, Label, CheckBox, TextField}
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.settings.StandardGameSettings
import scala.collection.mutable.ListBuffer
import view.CustomMenuView.DefaultUpdate
import view.Vocabulary

object GraphicCustomMenu

/** Custom menu graphic interface  */
class GraphicCustomMenu(stage: Stage) extends GraphicView:

  val defaultMaxQuizzes = 10
  val defaultMaxTime = 30

  @FXML
  var backButton: Button = _

  @FXML
  var titleLabel: Label = _

  @FXML
  var numberQuestionLabel: Label = _

  @FXML
  var maxQuizzesTextField: TextField = _

  @FXML
  var maxTimeLabel: Label = _

  @FXML
  var quizMaxTimeTextField: TextField = _

  @FXML
  var startButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def startButtonClicked(): Unit =
    val customGameSettings = StandardGameSettings(
      maxQuizzes = maxQuizzesTextField.getText.toInt,
      quizMaxTime = quizMaxTimeTextField.getText.toInt
    )
    AppController.currentPage.pageController.asInstanceOf[CustomMenuController].gameStage.gameSettings = customGameSettings
    sendEvent(Start)

  loadGUI(stage, this, "custom_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  titleLabel.setText(Vocabulary.CUSTOM_GAME)
  numberQuestionLabel.setText(Vocabulary.NUMBER_OF_QUESTIONS)
  maxQuizzesTextField.setText(defaultMaxQuizzes.toString)
  maxTimeLabel.setText(Vocabulary.MAX_TIME_FOR_QUESTION)
  quizMaxTimeTextField.setText(defaultMaxTime.toString)
  startButton.setText(Vocabulary.PLAY)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate => {}
