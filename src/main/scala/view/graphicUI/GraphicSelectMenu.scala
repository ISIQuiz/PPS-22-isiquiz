package view.graphicUI

import controller.AppController.{CustomMenuAction, MainMenuAction, SelectMenuAction, StandardGameAction}
import javafx.fxml.FXML
import javafx.scene.control.Label
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.layout.Pane
import utils.PaneLoader
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import model.GameStage

object GraphicSelectMenu

/** Select menu graphic interface  */
class GraphicSelectMenu(basePanel: Pane) extends GraphicView:

  @FXML
  def mainMenuClicked: Unit =
    sendEvent(MainMenuAction)

  @FXML
  def standardGameMenuClicked: Unit =
    sendEvent(StandardGameAction(Option(GameStage())))

  @FXML
  def customMenuClicked: Unit =
    sendEvent(CustomMenuAction(Option(GameStage())))

  PaneLoader.loadPane(basePanel, this, "select_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
