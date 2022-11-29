package view.graphicUI

import controller.AppController.SelectMenuAction
import controller.MainMenuController.Select
import javafx.fxml.FXML
import javafx.scene.control.Label
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.layout.Pane
import utils.PaneLoader
import view.MainMenuView
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicMainMenu

/** Main menu graphic interface  */
class GraphicMainMenu(basePanel: Pane) extends GraphicView:

  @FXML
  var label: Label = _

  @FXML
  def selectClicked: Unit =
    sendEvent(Select)

  PaneLoader.loadPane(basePanel, this, "main.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
