package view.graphicUI

import com.sun.javafx.scene.control.IntegerField
import controller.EditQuizMenuController.*
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.Answer.Answer
import scalafx.geometry.Insets
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.EditQuizMenuView.*
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer

object GraphicEditQuizMenu

/** Default menu graphic interface  */
class GraphicEditQuizMenu(stage: Stage) extends GraphicView:


  val toggleCourseGroup: ToggleGroup = ToggleGroup()

  val toggleQuizGroup: ToggleGroup = ToggleGroup()

  @FXML
  var coursesVBox: VBox = _
  @FXML
  var quizVBox: VBox = _

  @FXML
  var questionTextField: TextField = _

  @FXML
  var imagePathTextField: TextField = _

  @FXML
  var scoreIntegerField: IntegerField = _

  @FXML
  var answersVBox: VBox = _

  @FXML
  var feedbackLabel: Label = _


  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def addAnswerButtonClicked(): Unit =
    addAnswerGUI(None)

  @FXML
  def removeAnswerButtonClicked(): Unit =
    if answersVBox.getChildren.size()>1 then answersVBox.getChildren.remove(answersVBox.getChildren.size()-1)

  @FXML
  def editQuizButtonClicked(): Unit =
    if checkInputs then
      val answerList: ListBuffer[Answer] = ListBuffer()
      answersVBox.getChildren.forEach(hBox =>
        val answerTextField = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(1).asInstanceOf[TextField]
        val answerCorrectCheckBox: CheckBox = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(3).asInstanceOf[CheckBox]
          answerList += Answer(answerTextField.getText, answerCorrectCheckBox.isSelected)
      )
      val quiz = Quiz(question = questionTextField.getText, answerList = answerList.toList, maxScore = scoreIntegerField.getValue, imagePath = imagePathTextField.getText match
        case "" => None
        case text => Some(text)
      )
      import controller.EditQuizMenuController.EditQuizAction
      sendEvent(EditQuizAction(Option(quiz)))
    else
      feedbackLabel.setText("Configurazione Invalida")


  loadGUI(stage, this, "edit_quiz_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseListUpdate(updateParameter) =>
      Platform.runLater { () =>
        coursesVBox.getChildren.clear()
        answersVBox.getChildren.clear()
        updateParameter.get.foreach(savedCourse =>
          val radioButton = RadioButton(s"${savedCourse.courseId.courseName} (${savedCourse.quizList.size} quiz)");
          radioButton.setToggleGroup(toggleCourseGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            sendEvent(SelectCourseAction(Option(savedCourse)));
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizListUpdate(updateParameter) =>
      Platform.runLater { () =>
        quizVBox.getChildren.clear()
        updateParameter.get.foreach(quiz =>
          val radioButton = RadioButton(quiz.question);
          radioButton.setToggleGroup(toggleQuizGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            questionTextField.setText(quiz.question);
            imagePathTextField.setText(if quiz.imagePath.isDefined then quiz.imagePath.get else "");
            scoreIntegerField.setValue(quiz.maxScore);
            clearAllAnswers();
            quiz.answerList.foreach(ans => addAnswerGUI(Option(ans)));
            sendEvent(SelectQuizAction(Option(quiz)));
          );
          quizVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizEditedUpdate =>
      feedbackLabel.setText("Quiz Modificato!!!")
      quizVBox.getChildren.clear()
      questionTextField.clear()
      imagePathTextField.clear()
      scoreIntegerField.setValue(10)
      clearAllAnswers()
      addAnswerGUI(None)
    case _ => {}

  private def checkInputs: Boolean =
    questionTextField.getText.nonEmpty && toggleCourseGroup.getToggles.removeIf(_.isSelected) && toggleQuizGroup.getToggles.removeIf(_.isSelected)

  private def clearAllAnswers(): Unit =
    while answersVBox.getChildren.size() > 0 do answersVBox.getChildren.remove(0)

  private def addAnswerGUI(opAnswer: Option[Answer]): Unit =
    Platform.runLater { () =>
      val idNum = answersVBox.getChildren.size()
      val answerBox: HBox = HBox()
      answerBox.setAlignment(javafx.geometry.Pos.CENTER)
      answerBox.setPadding(Insets.apply(10, 0, 0, 10))
      val textField: TextField = TextField(if opAnswer.nonEmpty then opAnswer.get.text else "")
      textField.setId("answerTextField" + idNum)
      textField.setPrefWidth(650.0)
      val checkBox = CheckBox()
      checkBox.setSelected(if opAnswer.nonEmpty then opAnswer.get.isCorrect else false)
      checkBox.setId("answerCorrectCheckBox" + idNum)
      answerBox.getChildren.addAll(Label("Risposta "), textField, Label(" Corretta "), checkBox)
      answersVBox.getChildren.addAll(answerBox)
  }
