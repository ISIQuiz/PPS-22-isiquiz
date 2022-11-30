package view.graphicUI

import controller.SelectMenuController.{Back, Custom, Start}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.stage.Stage

object GraphicSelectMenu

/** Select menu graphic interface  */
class GraphicSelectMenu(stage: Stage) extends GraphicView:

  @FXML
  def mainMenuClicked: Unit =
    sendEvent(Back)

  @FXML
  def standardGameMenuClicked: Unit =
    sendEvent(Start)

  @FXML
  def customMenuClicked: Unit =
    sendEvent(Custom)

  loadGUI(stage, this, "select_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
