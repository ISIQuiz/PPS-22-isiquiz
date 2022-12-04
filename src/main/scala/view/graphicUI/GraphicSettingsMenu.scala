package view.graphicUI

import controller.AppController
import controller.SettingsMenuController.*
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.{GUILoader, StorageHandler}
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.{DirectoryChooser, FileChooser, Stage}
import utils.Configuration.{CurrentDirectoryPath, HomeDirectoryPath}
import java.io.File

object GraphicSettingsMenu

/** Settings menu graphic interface */
class GraphicSettingsMenu(stage: Stage) extends GraphicView :

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _

  @FXML
  var usernameTextField: TextField = _

  @FXML
  var importButton: Button = _

  @FXML
  var exportButton: Button = _

  @FXML
  var editButton: Button = _
  
  @FXML
  var addCourseButton: Button = _
  
  @FXML
  var addQuizButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def importButtonClicked(): Unit =
    val extensionFilter: ExtensionFilter = ExtensionFilter("JSON file", "*.json")
    val fileChooser: FileChooser = FileChooser()
    fileChooser.getExtensionFilters.add(extensionFilter)
    fileChooser.setTitle("Importa i corsi")
    fileChooser.setInitialDirectory(File(CurrentDirectoryPath))
    val selectedFile = fileChooser.showOpenDialog(stage)
    println(selectedFile.getPath)


  @FXML
  def exportButtonClicked(): Unit =
    val directoryChooser: DirectoryChooser = DirectoryChooser()
    directoryChooser.setTitle("Esporta i corsi")
    directoryChooser.setInitialDirectory(File(HomeDirectoryPath))
    val selectedDirectory = directoryChooser.showDialog(stage)
    StorageHandler.exportSavedCoursesToPersonalDirectory(AppController.session, selectedDirectory.toString)

  @FXML
  def editButtonClicked(): Unit = ???

  @FXML
  def addCourseButtonClicked(): Unit =
    sendEvent(AddCourse)

  @FXML
  def addQuizButtonClicked(): Unit =
    sendEvent(AddQuiz)

  loadGUI(stage, this, "settings_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
