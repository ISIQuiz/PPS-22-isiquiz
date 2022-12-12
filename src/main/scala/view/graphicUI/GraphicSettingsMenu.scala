package view.graphicUI

import controller.AppController
import controller.SettingsMenuController.*
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, Label, TextField}
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.{DirectoryChooser, FileChooser, Stage}
import utils.storage.Configuration.{CurrentDirectoryPath, HomeDirectoryPath, PlayerCoursesFileName}
import utils.storage.{ExportHandler, ImportHandler}
import java.io.File
import scala.util.{Failure, Success}

object GraphicSettingsMenu

/** Settings menu graphic interface */
class GraphicSettingsMenu(stage: Stage) extends GraphicView:

  val alert = Alert(AlertType.NONE)

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
  var addCourseButton: Button = _

  @FXML
  var addQuizButton: Button = _

  @FXML
  var editCourseButton: Button = _

  @FXML
  var editQuizButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  private def showDialogAlert(alertType: AlertType, title: String, headerText: String, path: String = ""): Unit =
    alert.setAlertType(alertType)
    alert.setTitle(title)
    alert.setHeaderText(headerText + "\n" + path)
    alert.show()

  @FXML
  def importButtonClicked(): Unit =
    val extensionFilter: ExtensionFilter = ExtensionFilter("JSON file", "*.json")
    val fileChooser: FileChooser = FileChooser()
    fileChooser.getExtensionFilters.add(extensionFilter)
    fileChooser.setTitle("Importa i corsi")
    fileChooser.setInitialDirectory(File(CurrentDirectoryPath))
    val selectedFile = fileChooser.showOpenDialog(stage)
    if (selectedFile != null)
      ImportHandler.importSavedCourseListFromFile(AppController.session, selectedFile.getPath) match
        case Success(savedCourseList) =>
          AppController.changeSavedCourses(savedCourseList)
          showDialogAlert(
            AlertType.INFORMATION,
            "Importazione",
            "Importazione avvenuta con successo!",
            selectedFile.getPath
          )
        case Failure(f) =>
          showDialogAlert(
            AlertType.ERROR,
            "Importazione",
            "Errore! File non valido!"
          )

  @FXML
  def exportButtonClicked(): Unit =
    val directoryChooser: DirectoryChooser = DirectoryChooser()
    directoryChooser.setTitle("Esporta i corsi")
    directoryChooser.setInitialDirectory(File(HomeDirectoryPath))
    val selectedDirectory = directoryChooser.showDialog(stage)
    if (selectedDirectory != null)
      ExportHandler.exportSavedCoursesToPath(AppController.session.savedCourses, selectedDirectory.toString) match
        case Success(path) =>
          showDialogAlert(
            AlertType.INFORMATION,
            "Esportazione",
            "Esportazione avvenuta con successo!",
            path
          )
        case Failure(f) =>
          showDialogAlert(
            AlertType.ERROR,
            "Esportazione",
            "Errore! File non valido!"
          )

  @FXML
  def addCourseButtonClicked(): Unit =
    sendEvent(AddCourse)

  @FXML
  def addQuizButtonClicked(): Unit =
    sendEvent(AddQuiz)

  @FXML
  def editCourseButtonClicked(): Unit =
    sendEvent(EditCourse)

  @FXML
  def editQuizButtonClicked(): Unit =
    sendEvent(EditQuiz)

  loadGUI(stage, this, "settings_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
