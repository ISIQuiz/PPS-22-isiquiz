package view.graphicUI

import controller.StandardGameController.Back
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.stage.Stage

object GraphicStandardGameMenu

/** Standard Game menu graphic interface  */
class GraphicStandardGameMenu(stage: Stage) extends GraphicView:

  @FXML
  def selectMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "standard_game.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}

