package view.graphicUI

import controller.MainMenuController.{Quit, Select, Settings, Statistics}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.Stage

object GraphicMainMenu

/** Main menu graphic interface  */
class GraphicMainMenu(stage: Stage) extends GraphicView:

  @FXML
  var selectButton: Button = _

  @FXML
  var statisticsButton: Button = _

  @FXML
  var settingsButton: Button = _

  @FXML
  var quitButton: Button = _

  @FXML
  def selectButtonClicked(): Unit =
    sendEvent(Select)

  @FXML
  def statisticsButtonClicked(): Unit =
    sendEvent(Statistics)

  @FXML
  def settingsButtonClicked(): Unit =
    sendEvent(Settings)

  @FXML
  def quitButtonClicked(): Unit =
    sendEvent(Quit)

  loadGUI(stage, this, "main.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
