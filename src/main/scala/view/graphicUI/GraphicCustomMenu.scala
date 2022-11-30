package view.graphicUI

import controller.CustomMenuController.Back
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import model.GameStage
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import utils.PaneLoader
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicCustomMenu

/** Custom settings menu graphic interface  */
class GraphicCustomMenu(basePanel: Pane) extends GraphicView:

  @FXML
  def selectMenuClicked: Unit =
    sendEvent(Back)

  PaneLoader.loadPane(basePanel, this, "custom_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
