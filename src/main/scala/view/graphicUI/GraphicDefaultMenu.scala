package view.graphicUI

import javafx.fxml.FXML
import javafx.scene.control.Label
import scalafx.application.JFXApp3.PrimaryStage
import utils.PaneLoader
import view.View.GraphicView
import view.updates.ViewUpdate

object GraphicDefaultMenu

/** Default menu graphic interface  */
class GraphicDefaultMenu(stage: PrimaryStage) extends GraphicView:

  PaneLoader.loadPane(stage, this, "default.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
