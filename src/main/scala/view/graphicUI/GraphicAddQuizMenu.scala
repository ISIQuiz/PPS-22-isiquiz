package view.graphicUI

import com.sun.javafx.scene.control.IntegerField
import controller.AddQuizMenuController.{AddCourseAction, Back}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.stage.Stage
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifierImpl
import model.Quiz.Quiz
import model.SavedCourse.SavedCourseImpl
import scalafx.geometry.Insets
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.AddQuizMenuView.QuizAdded
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer

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
  var imagePathTextField: TextField = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def addAnswerButtonClicked(): Unit =
    Platform.runLater { () =>
      val idNum = answersVBox.getChildren.size()
      println("adding answer "+idNum)
      val answerBox: HBox = HBox()
      answerBox.setAlignment(javafx.geometry.Pos.CENTER)
      answerBox.setPadding(Insets.apply(10,0,0,10))
      val textField: TextField = TextField()
      textField.setId("answerTextField"+idNum)
      textField.setPrefWidth(650.0)
      val checkBox = CheckBox()
      checkBox.setId("answerCorrectCheckBox"+idNum)
      answerBox.getChildren.addAll(Label("Risposta "),textField,Label(" Corretta "),checkBox)
      answersVBox.getChildren.addAll(answerBox)
    }

  @FXML
  def addQuizButtonClicked(): Unit =
    if checkInputs then
      val answerList:ListBuffer[Answer] = ListBuffer()
      answersVBox.getChildren.forEach( hBox =>
        val answerTextField = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(1).asInstanceOf[TextField]
        val answerCorrectCheckBox:CheckBox = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(3).asInstanceOf[CheckBox]
        answerList += Answer(answerTextField.getText, answerCorrectCheckBox.isSelected)
      )
      val quiz = Quiz(questionTextField.getText, answerList.toList, scoreIntegerField.getValue, imagePathTextField.getText match
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