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
  def selectClicked: Unit =
    sendEvent(Select)

  @FXML
  def statisticsClicked: Unit =
    sendEvent(Statistics)

  @FXML
  def settingsClicked: Unit =
    sendEvent(Settings)

  @FXML
  def quitClicked: Unit =
    sendEvent(Quit)

  loadGUI(stage, this, "main.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
