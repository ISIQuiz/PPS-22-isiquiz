package view.graphicUI

import controller.MainMenuController.{Quit, Select, Settings, Statistics}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import view.Vocabulary
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.stage.Stage

object GraphicMainMenu

/** Main menu graphic interface  */
class GraphicMainMenu(stage: Stage) extends GraphicView:

  @FXML
  var selectButton: Button = _

  @FXML
  var statisticsButton: Button = _

  @FXML
  var settingsButton: Button = _

  @FXML
  var quitButton: Button = _

  @FXML
  def itImageClicked(): Unit =
    Vocabulary.LANGUAGE_SELECTED = Vocabulary.LANGUAGE_AVAILABLE.IT
    reloadGUI()

  @FXML
  def enImageClicked(): Unit =
    Vocabulary.LANGUAGE_SELECTED = Vocabulary.LANGUAGE_AVAILABLE.EN
    reloadGUI()


  @FXML
  def selectButtonClicked(): Unit =
    sendEvent(Select)

  @FXML
  def statisticsButtonClicked(): Unit =
    sendEvent(Statistics)

  @FXML
  def settingsButtonClicked(): Unit =
    sendEvent(Settings)

  @FXML
  def quitButtonClicked(): Unit =
    sendEvent(Quit)

  reloadGUI()

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}

  def reloadGUI(): Unit =
    loadGUI(stage, this, "main.fxml")
    selectButton.setText(Vocabulary.PLAY)
    statisticsButton.setText(Vocabulary.STATISTICS)
    settingsButton.setText(Vocabulary.SETTINGS)
    quitButton.setText(Vocabulary.QUIT)

