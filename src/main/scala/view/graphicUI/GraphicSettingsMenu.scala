package view.graphicUI

import controller.AppController.{CustomMenuAction, MainMenuAction, SelectMenuAction, StandardGameAction}
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import model.GameStage
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import utils.PaneLoader
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicSettingsMenu

/** Settings menu graphic interface  */
class GraphicSettingsMenu(basePanel: Pane) extends GraphicView:

  @FXML
  def mainMenuClicked: Unit =
    sendEvent(MainMenuAction)

  PaneLoader.loadPane(basePanel, this, "settings_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
