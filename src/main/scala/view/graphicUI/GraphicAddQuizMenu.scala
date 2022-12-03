package view.graphicUI

import com.sun.javafx.scene.control.IntegerField
import controller.AddQuizMenuController.{AddCourseAction, Back}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.Quiz.Quiz
import model.SavedCourse.SavedCourseImpl
import scalafx.geometry.Insets
import scalafx.scene.layout.HBox
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.AddQuizMenuView.QuizAdded
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

object GraphicAddQuizMenu

/** Default menu graphic interface  */
class GraphicAddQuizMenu(stage: Stage) extends GraphicView:


  @FXML
  var coursesScrollPane: ScrollPane = _

  @FXML
  var questionTextField: TextField = _

  @FXML
  var scoreIntegerField: IntegerField = _

  @FXML
  var answersVBox: VBox = _

  @FXML
  var answerTextField:TextField = _

  @FXML
  var answerCorrectCheckBox: CheckBox = _

  @FXML
  var imagePathTextField: TextField = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def addAnswerButtonClicked(): Unit = ???

  @FXML
  def addQuizButtonClicked(): Unit =
    if checkInputs then
      val answerList:List[Answer] = Nil
      val quiz = Quiz(questionTextField.getText, answerList, scoreIntegerField.getValue, imagePathTextField.getText match
        case "" => None
        case text => Some(text)
      )
      sendEvent(AddCourseAction(Option(SavedCourseImpl(CourseIdentifierImpl("nameCourseTEST", "degreeName", "uniName"),Option("descr"),Nil ))))
      import controller.AddQuizMenuController.AddQuizAction
      import model.Quiz.Quiz
      sendEvent(AddQuizAction(Option(quiz)))
    else
      feedbackLabel.setText("Configurazione Invalida")

  loadGUI(stage, this, "add_quiz_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case QuizAdded =>
      feedbackLabel.setText("Quiz Aggiunto!!!")
    case _ => {}


  private def checkInputs: Boolean = {
    questionTextField.getText.nonEmpty
  }