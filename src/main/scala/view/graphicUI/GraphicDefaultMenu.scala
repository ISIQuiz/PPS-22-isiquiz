package view.graphicUI

import view.View.GraphicView
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.stage.Stage

object GraphicDefaultMenu

/** Default menu graphic interface  */
class GraphicDefaultMenu(stage: Stage) extends GraphicView:

  @FXML
  var notAvailableLabel: Label = _

  loadGUI(stage, this, "default.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
