package view.graphicUI

import controller.SettingsMenuController.Back
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.Stage

object GraphicSettingsMenu

/** Settings menu graphic interface  */
class GraphicSettingsMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _

  @FXML
  var usernameTextField: TextField = _

  @FXML
  var importButton: Button = _

  @FXML
  var exportButton: Button = _

  @FXML
  var editButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def importButtonClicked(): Unit = ???

  @FXML
  def exportButtonClicked(): Unit = ???

  @FXML
  def editButtonClicked(): Unit = ???

  loadGUI(stage, this, "settings_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
