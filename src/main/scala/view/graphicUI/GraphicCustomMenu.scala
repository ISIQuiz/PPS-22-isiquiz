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
import javafx.scene.control.{Button, CheckBox, TextField}
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.settings.StandardGameSettings
import scala.collection.mutable.ListBuffer
import view.CustomMenuView.DefaultUpdate

object GraphicCustomMenu

/** Custom menu graphic interface  */
class GraphicCustomMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var maxQuizzesTextField: TextField = _

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

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate => {}
