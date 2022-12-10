package view.graphicUI

import controller.EditCourseMenuController.Back
import view.EditCourseMenuView.*
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.stage.Stage
import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicEditCourseMenu

/** edit course menu graphic interface  */
class GraphicEditCourseMenu(stage: Stage) extends GraphicView:

  val toggleGroup: ToggleGroup = ToggleGroup()
  
  @FXML
  var coursesVBox: VBox = _
  
  @FXML
  var courseNameTextField: TextField = _
  
  @FXML
  var degreeNameTextField: TextField = _
  
  @FXML
  var universityNameTextField: TextField = _
  
  @FXML
  var descriptionCourseTextField: TextField = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def editCourseButtonClicked(): Unit =
    if checkInputs then
      val course = SavedCourseImpl(
        courseId = CourseIdentifierImpl(
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
      feedbackLabel.setText("Configurazione Invalida")

  @FXML
  def deleteCourseButtonClicked(): Unit =
    if checkSelections then
      import controller.EditCourseMenuController.EditCourseAction
      sendEvent(EditCourseAction(Option.empty))
    else
      feedbackLabel.setText("Selezione Invalida")

  loadGUI(stage, this, "edit_course_menu.fxml")

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
            sendEvent(SelectCourseAction(Option(savedCourse)));
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case CourseEditedUpdate =>
      feedbackLabel.setText("Corso modificato")
      clearAllFields()
    case CourseDeletedUpdate =>
      feedbackLabel.setText("Corso cancallato")
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
