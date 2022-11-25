package utils

import javafx.fxml.FXMLLoader
import scalafx.scene.Scene
import scalafx.scene.Parent
import javafx.scene.layout.{GridPane}
//import scalafx.scene.layout.Pane
import javafx.scene.layout.Pane
import scalafx.application.JFXApp3.PrimaryStage
import view.View.GraphicView

object PaneLoader:


  def loadPane(basePanel: Pane, controller: GraphicView, fileName: String): Unit =
    var loader: FXMLLoader = FXMLLoader()
    loader.setController(controller)
    loader.setLocation(getClass.getResource(s"/fxml/$fileName"))
//    stage.scene = new Scene()
//    stage.scene.root.value
//    stage.scene.value.root.value
//    println(stage.scene.value.root.value)
    val panel: Pane = loader.load[Pane]
    basePanel.getChildren.clear()
    basePanel.getChildren.add(panel)
//    basePanel.component.setCenter(panel)
//    stage.scene.value.root.value = panel
//    stage.scene.value.setRoot(panel)
//    stage.scene.value.content.add(panel)
//    stage.scene.root.value = panel
