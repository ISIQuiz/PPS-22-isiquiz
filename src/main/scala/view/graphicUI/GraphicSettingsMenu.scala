package view.graphicUI

import controller.SettingsMenuController.Back
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.stage.Stage

object GraphicSettingsMenu

/** Settings menu graphic interface  */
class GraphicSettingsMenu(stage: Stage) extends GraphicView:

  @FXML
  def mainMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "settings_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
