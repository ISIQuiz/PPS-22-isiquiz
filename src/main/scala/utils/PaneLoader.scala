package utils

import javafx.fxml.FXMLLoader
import javafx.scene.layout.{GridPane, Pane}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.jfxScene2sfx
import scalafx.scene.SceneIncludes.jfxScene2sfx
import view.View.GraphicView

object PaneLoader:

  protected val loader: FXMLLoader = FXMLLoader()

  def loadPane(stage: PrimaryStage, controller: GraphicView, fileName: String): Unit =
    loader.setController(controller)
    loader.setLocation(getClass.getResource(s"/fxml/$fileName"))
    stage.scene = new Scene()
    val panel: Pane = loader.load[Pane]
    stage.scene.value.content.add(panel)
