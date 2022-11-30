package view.graphicUI

import controller.CustomMenuController.Back
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.stage.Stage

object GraphicCustomMenu

/** Custom menu graphic interface  */
class GraphicCustomMenu(stage: Stage) extends GraphicView:

  @FXML
  def selectMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "custom_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
