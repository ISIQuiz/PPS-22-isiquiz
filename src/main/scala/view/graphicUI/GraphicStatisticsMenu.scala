package view.graphicUI

import controller.StatisticsMenuController.{Back}
import javafx.fxml.FXML
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicStatisticsMenu

/** Statistics menu graphic interface  */
class GraphicStatisticsMenu(stage: Stage) extends GraphicView:

  @FXML
  def mainMenuClicked: Unit =
    sendEvent(Back)

  loadGUI(stage, this, "statistics_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
