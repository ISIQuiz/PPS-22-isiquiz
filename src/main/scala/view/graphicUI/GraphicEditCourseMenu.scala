package view.graphicUI

import controller.EditCourseMenuController.Back
import view.EditCourseMenuView.*
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.stage.Stage
import model.CourseIdentifier.CourseIdentifier
import model.SavedCourse.SavedCourse
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.Vocabulary
import view.updates.ViewUpdate

object GraphicEditCourseMenu

/** edit course menu graphic interface  */
class GraphicEditCourseMenu(stage: Stage) extends GraphicView:

  val toggleGroup: ToggleGroup = ToggleGroup()
  
  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _
  
  @FXML
  var selectCourseLabel: Label = _
  
  @FXML
  var coursesVBox: VBox = _
  
  @FXML
  var courseNameLabel: Label = _

  @FXML
  var courseNameTextField: TextField = _
  
  @FXML
  var degreeNameLabel: Label = _

  @FXML
  var degreeNameTextField: TextField = _
  
  @FXML
  var universityNameLabel: Label = _

  @FXML
  var universityNameTextField: TextField = _
  
  @FXML
  var descriptionCourseLabel: Label = _

  @FXML
  var descriptionCourseTextField: TextField = _

  @FXML
  var editCourseButton: Button = _

  @FXML
  var deleteCourseButton: Button = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def editCourseButtonClicked(): Unit =
    if checkInputs then
      val course = SavedCourse(
        courseId = CourseIdentifier(
          courseName = courseNameTextField.getText,
          degreeName = degreeNameTextField.getText,
          universityName = universityNameTextField.getText
        ),
        description = descriptionCourseTextField.getText match
          case "" => None
          case text => Some(text)
        ,quizList = Nil
      )
      import controller.EditCourseMenuController.EditCourseAction
      sendEvent(EditCourseAction(Option(course)))
    else
      feedbackLabel.setText(Vocabulary.INVALID_CONFIGURATION)

  @FXML
  def deleteCourseButtonClicked(): Unit =
    if checkSelections then
      import controller.EditCourseMenuController.EditCourseAction
      sendEvent(EditCourseAction(Option.empty))
    else
      feedbackLabel.setText(Vocabulary.SELECT_COURSE)

  loadGUI(stage, this, "edit_course_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.EDIT_COURSE)
  selectCourseLabel.setText(Vocabulary.SELECT_COURSE)
  courseNameLabel.setText(Vocabulary.COURSE_NAME)
  degreeNameLabel.setText(Vocabulary.DEGREE_NAME)
  universityNameLabel.setText(Vocabulary.UNIVERSITY_NAME)
  descriptionCourseLabel.setText(Vocabulary.DESCRIPTION_COURSE)
  editCourseButton.setText(Vocabulary.EDIT_COURSE)
  deleteCourseButton.setText(Vocabulary.DELETE_COURSE)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseListUpdate(updateParameter) =>
      Platform.runLater { () =>
        coursesVBox.getChildren.clear()
        updateParameter.get.foreach(savedCourse =>
          val radioButton = RadioButton(savedCourse.courseId.courseName);
          radioButton.setToggleGroup(toggleGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            courseNameTextField.setText(savedCourse.courseId.courseName);
            degreeNameTextField.setText(savedCourse.courseId.degreeName);
            universityNameTextField.setText(savedCourse.courseId.universityName);
            descriptionCourseTextField.setText(savedCourse.description match
              case Some(text) => text
              case _ => ""
            );
            import controller.EditCourseMenuController.SelectCourseAction
            sendEvent(SelectCourseAction(Option(savedCourse)));
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case CourseEditedUpdate =>
      feedbackLabel.setText(Vocabulary.COURSE_EDITED)
      clearAllFields()
    case CourseDeletedUpdate =>
      feedbackLabel.setText(Vocabulary.COURSE_DELETED)
      clearAllFields()
    case _ => {}

  private def checkInputs: Boolean =
    courseNameTextField.getText.nonEmpty && checkSelections

  private def checkSelections: Boolean =
    toggleGroup.getToggles.removeIf(_.isSelected)

  private def clearAllFields(): Unit =
    courseNameTextField.clear()
    degreeNameTextField.clear()
    universityNameTextField.clear()
    descriptionCourseTextField.clear()
