package view.graphicUI

import com.sun.javafx.scene.control.IntegerField
import controller.EditQuizMenuController.Back
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

/** edit quiz menu graphic interface  */
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
  var scoreIntegerField: TextField = _

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
        val answerCorrectCheckBox: CheckBox = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(2).asInstanceOf[CheckBox]
          answerList += Answer(answerTextField.getText, answerCorrectCheckBox.isSelected)
      )
      val quiz = Quiz(question = questionTextField.getText, answerList = answerList.toList, maxScore = scoreIntegerField.getText.toInt, imagePath = imagePathTextField.getText match
        case "" => None
        case text => Some(text)
      )
      import controller.EditQuizMenuController.EditQuizAction
      sendEvent(EditQuizAction(Option(quiz)))
    else
      feedbackLabel.setText("Configurazione Invalida")

  @FXML
  def deleteQuizButtonClicked(): Unit =
    if checkSelections then
      import controller.EditQuizMenuController.EditQuizAction
      sendEvent(EditQuizAction(Option.empty))
    else
      feedbackLabel.setText("Selezione Invalida")

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
            import controller.EditQuizMenuController.SelectCourseAction
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
            scoreIntegerField.setText(quiz.maxScore.toString);
            clearAllAnswers();
            quiz.answerList.foreach(ans => addAnswerGUI(Option(ans)));
            import controller.EditQuizMenuController.SelectQuizAction
            sendEvent(SelectQuizAction(Option(quiz)));
          );
          quizVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizEditedUpdate =>
      feedbackLabel.setText("Quiz modificato")
      clearAllFields()
    case QuizDeletedUpdate =>
      feedbackLabel.setText("Quiz cancellato")
      clearAllFields()
    case _ => {}

  private def checkInputs: Boolean =
    questionTextField.getText.nonEmpty && scoreIntegerField.getText().toIntOption.nonEmpty && answersVBox.getChildren.size()>0 && checkSelections

  private def checkSelections: Boolean =
    toggleCourseGroup.getToggles.removeIf(_.isSelected) && toggleQuizGroup.getToggles.removeIf(_.isSelected)

  private def clearAllFields():Unit =
    quizVBox.getChildren.clear()
    questionTextField.clear()
    imagePathTextField.clear()
    scoreIntegerField.setText("0")
    clearAllAnswers()
    addAnswerGUI(None)

  private def clearAllAnswers(): Unit =
    while answersVBox.getChildren.size() > 0 do answersVBox.getChildren.remove(0)

  private def addAnswerGUI(opAnswer: Option[Answer]): Unit =
    Platform.runLater { () =>
      val idNum = answersVBox.getChildren.size()
      val answerBox: HBox = HBox()
      answerBox.setAlignment(javafx.geometry.Pos.CENTER)
      answerBox.setPadding(Insets.apply(5, 0, 0, 5))
      val textField: TextField = TextField(if opAnswer.nonEmpty then opAnswer.get.text else "")
      textField.setId("answerTextField" + idNum)
      textField.getStyleClass.add("text-field-large")
      val checkBox = CheckBox()
      checkBox.setSelected(if opAnswer.nonEmpty then opAnswer.get.isCorrect else false)
      checkBox.setId("answerCorrectCheckBox" + idNum)
      checkBox.setText("Corretta")
      checkBox.getStyleClass.add("checkbox")
      answerBox.getChildren.addAll(Label("Risposta "), textField, checkBox)
      answersVBox.getChildren.addAll(answerBox)
  }
