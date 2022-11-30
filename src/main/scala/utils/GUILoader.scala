package utils

import javafx.fxml.FXMLLoader
import scalafx.scene.Scene
import scalafx.scene.Parent
import javafx.scene.layout.Pane
import javafx.stage.Stage
import scalafx.application.JFXApp3.PrimaryStage
import view.View.GraphicView

object GUILoader:

  def loadGUI(stage: Stage, controller: GraphicView, fileName: String): Unit =
    val loader: FXMLLoader = FXMLLoader()
    loader.setController(controller)
    loader.setLocation(getClass.getResource(s"/fxml/$fileName"))
    val panel: Pane = loader.load[Pane]
    stage.getScene.setRoot(panel)
