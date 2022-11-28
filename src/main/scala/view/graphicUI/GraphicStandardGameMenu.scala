package view.graphicUI

import controller.AppController.{MainMenuAction, SelectMenuAction}
import javafx.fxml.FXML
import javafx.scene.control.Label
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.layout.Pane
import utils.PaneLoader
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicStandardGameMenu

/** Standard Game menu graphic interface  */
class GraphicStandardGameMenu(basePanel: Pane) extends GraphicView:

  @FXML
  def selectMenuClicked: Unit =
    sendEvent(SelectMenuAction)

  PaneLoader.loadPane(basePanel, this, "standard_game.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}

