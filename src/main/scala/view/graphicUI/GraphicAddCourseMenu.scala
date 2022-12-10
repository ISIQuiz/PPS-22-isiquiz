package view.graphicUI


import controller.AddCourseMenuController.{AddCourseAction, Back}
import view.AddCourseMenuView.{CourseAddedUpdate, CoursePrintUpdate}
import javafx.fxml.FXML
import javafx.scene.control.{Label, TextField}
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import model.SavedCourse.SavedCourse
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz

object GraphicAddCourseMenu

/** add course menu graphic interface  */
class GraphicAddCourseMenu(stage: Stage) extends GraphicView:

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
      feedbackLabel.setText("Nome mancante")

  loadGUI(stage, this, "add_course_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseAddedUpdate =>
      feedbackLabel.setText("Corso aggiunto")
      courseNameTextField.clear()
      degreeNameTextField.clear()
      universityNameTextField.clear()
      descriptionCourseTextField.clear()
    case _ => {}

  private def checkInputs: Boolean =
    courseNameTextField.getText.nonEmpty
