package view.graphicUI

import controller.SelectMenuController.{Back, Custom, Start}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, ScrollPane}
import javafx.stage.Stage

object GraphicSelectMenu

/** Select menu graphic interface  */
class GraphicSelectMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var coursesScrollPane: ScrollPane = _

  @FXML
  var standardGameButton: Button = _

  @FXML
  var reviewGameButton: Button = _

  @FXML
  var customGameButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def standardGameButtonClicked(): Unit =
    sendEvent(Start)

  @FXML
  def reviewGameButtonClicked(): Unit = ???

  @FXML
  def customGameButtonClicked(): Unit =
    sendEvent(Custom)

  loadGUI(stage, this, "select_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
