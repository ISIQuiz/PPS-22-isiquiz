package view.graphicUI

import controller.CustomMenuController.Back
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}
import javafx.stage.Stage

object GraphicCustomMenu

/** Custom menu graphic interface  */
class GraphicCustomMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var maxQuizzesTextField: TextField = _

  @FXML
  var maxTimeTextField: TextField = _

  @FXML
  var quizMaxTimeTextField: TextField = _

  @FXML
  var startButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def startButtonClicked(): Unit = ???

  loadGUI(stage, this, "custom_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
