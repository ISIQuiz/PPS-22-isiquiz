package view.graphicUI

import controller.EditCourseMenuController.*
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
import view.updates.ViewUpdate

object GraphicEditCourseMenu

/** Default menu graphic interface  */
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
      feedbackLabel.setText("Nome mancante")

  loadGUI(stage, this, "edit_course_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseUpdate(updateParameter) =>
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
      feedbackLabel.setText("Corso Modificato!!!")
      courseNameTextField.clear()
      degreeNameTextField.clear()
      universityNameTextField.clear()
      descriptionCourseTextField.clear()
    case _ => {}

  private def checkInputs: Boolean =
    courseNameTextField.getText.nonEmpty && toggleGroup.getToggles.removeIf(_.isSelected)
