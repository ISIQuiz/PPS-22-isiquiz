package view.graphicUI

import javafx.fxml.FXML
import javafx.scene.control.Label
import scalafx.application.JFXApp3.PrimaryStage
import utils.PaneLoader
import view.View.GraphicView
import view.updates.ViewUpdate

object GraphicMainMenu

/** Main menu graphic interface  */
class GraphicMainMenu(stage: PrimaryStage) extends GraphicView:

  @FXML
  var label: Label = _

  PaneLoader.loadPane(stage, this, "main.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit =
    println("ScalaFX Menu Principale")
