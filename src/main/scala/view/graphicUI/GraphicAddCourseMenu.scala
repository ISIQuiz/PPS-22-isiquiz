package view.graphicUI

import controller.AddCourseMenuController.{AddCourseAction, Back}
import view.AddCourseMenuView.{CourseAddedUpdate, CoursePrintUpdate}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import model.SavedCourse.SavedCourse
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz
import view.Vocabulary

object GraphicAddCourseMenu

/** add course menu graphic interface  */
class GraphicAddCourseMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _
  
  @FXML
  var menuLabel: Label = _

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
  var addCourseButton: Button = _
  
  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def addCourseButtonClicked(): Unit =
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
      import controller.AddCourseMenuController.AddCourseAction
      sendEvent(AddCourseAction(Option(course)))
    else
      feedbackLabel.setText(Vocabulary.NAME_MISSING)

  loadGUI(stage, this, "add_course_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.ADD_COURSE)
  courseNameLabel.setText(Vocabulary.COURSE_NAME)
  degreeNameLabel.setText(Vocabulary.DEGREE_NAME)
  universityNameLabel.setText(Vocabulary.UNIVERSITY_NAME)
  descriptionCourseLabel.setText(Vocabulary.DESCRIPTION_COURSE)
  addCourseButton.setText(Vocabulary.ADD_COURSE)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseAddedUpdate =>
      feedbackLabel.setText(Vocabulary.COURSE_ADDED)
      courseNameTextField.clear()
      degreeNameTextField.clear()
      universityNameTextField.clear()
      descriptionCourseTextField.clear()
    case _ => {}

  private def checkInputs: Boolean =
    courseNameTextField.getText.nonEmpty
