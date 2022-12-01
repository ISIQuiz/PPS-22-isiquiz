package view.graphicUI

import javafx.fxml.FXML
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicReviewMenu

/** Review menu graphic interface  */
class GraphicReviewMenu(stage: Stage) extends GraphicView:

  loadGUI(stage, this, "review_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
