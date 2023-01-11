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
import view.Vocabulary

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
    fileChooser.setTitle(Vocabulary.IMPORT_QUIZ)
    fileChooser.setInitialDirectory(File(CurrentDirectoryPath))
    val selectedFile = fileChooser.showOpenDialog(stage)
    if (selectedFile != null)
      ImportHandler.importSavedCourseListFromFile(AppController.session, selectedFile.getPath) match
        case Success(savedCourseList) =>
          AppController.changeSavedCourses(savedCourseList)
          ExportHandler.exportDataToPersonalDirectory(savedCourseList)
          showDialogAlert(
            AlertType.INFORMATION,
            Vocabulary.IMPORT_QUIZ,
            Vocabulary.OPERATION_SUCCEEDED,
            selectedFile.getPath
          )
        case Failure(f) =>
          showDialogAlert(
            AlertType.ERROR,
            Vocabulary.IMPORT_QUIZ,
            Vocabulary.ERROR_INVALID_FILE
          )

  @FXML
  def exportButtonClicked(): Unit =
    val directoryChooser: DirectoryChooser = DirectoryChooser()
    directoryChooser.setTitle(Vocabulary.EXPORT_QUIZ)
    directoryChooser.setInitialDirectory(File(HomeDirectoryPath))
    val selectedDirectory = directoryChooser.showDialog(stage)
    if (selectedDirectory != null)
      ExportHandler.exportSavedCoursesToPath(AppController.session.savedCourses, selectedDirectory.toString) match
        case Success(path) =>
          showDialogAlert(
            AlertType.INFORMATION,
            Vocabulary.EXPORT_QUIZ,
            Vocabulary.OPERATION_SUCCEEDED,
            path
          )
        case Failure(f) =>
          showDialogAlert(
            AlertType.ERROR,
            Vocabulary.EXPORT_QUIZ,
            Vocabulary.ERROR_INVALID_FILE
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
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.SETTINGS)
  importButton.setText(Vocabulary.IMPORT_QUIZ)
  exportButton.setText(Vocabulary.EXPORT_QUIZ)
  addCourseButton.setText(Vocabulary.ADD_COURSE)
  addQuizButton.setText(Vocabulary.ADD_QUIZ)
  editCourseButton.setText(Vocabulary.EDIT_COURSE)
  editQuizButton.setText(Vocabulary.EDIT_QUIZ)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = {}
