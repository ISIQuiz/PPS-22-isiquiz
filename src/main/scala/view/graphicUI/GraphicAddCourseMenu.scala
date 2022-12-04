package view.graphicUI


import controller.AddCourseMenuController.{AddCourseAction, Back}
import javafx.fxml.FXML
import javafx.scene.control.{Label, TextField}
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import model.SavedCourse.SavedCourseImpl
import model.CourseIdentifier.CourseIdentifierImpl
import model.Quiz.Quiz
import view.AddCourseMenuView.CoursePrint

object GraphicAddCourseMenu

/** Default menu graphic interface  */
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
    if courseNameTextField.getText.nonEmpty then
      val course = SavedCourseImpl(
        courseId = CourseIdentifierImpl(
          courseName = courseNameTextField.getText,
          degreeName = degreeNameTextField.getText,
          universityName = universityNameTextField.getText
        ),
        description = Option(descriptionCourseTextField.getText),
        quizList = Nil
      )
      import controller.AddCourseMenuController.AddCourseAction
      sendEvent(AddCourseAction(Option(course)))
      println("1 "+course)
    else
      feedbackLabel.setText("Nome mancante")

  loadGUI(stage, this, "add_course_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CoursePrint(course) =>
      feedbackLabel.setText("Corso Aggiunto!!!")
      courseNameTextField.setText("")
      degreeNameTextField.setText("")
      universityNameTextField.setText("")
      descriptionCourseTextField.setText("")
    case _ => {}