package utils

import javafx.fxml.FXMLLoader
import scalafx.scene.Scene
import scalafx.scene.Parent
import javafx.scene.layout.Pane
import scalafx.application.JFXApp3.PrimaryStage
import view.View.GraphicView

object PaneLoader:

  def loadPane(basePanel: Pane, controller: GraphicView, fileName: String): Unit =
    val loader: FXMLLoader = FXMLLoader()
    loader.setController(controller)
    loader.setLocation(getClass.getResource(s"/fxml/$fileName"))
    val panel: Pane = loader.load[Pane]
    basePanel.getChildren.clear()
    basePanel.getChildren.add(panel)
