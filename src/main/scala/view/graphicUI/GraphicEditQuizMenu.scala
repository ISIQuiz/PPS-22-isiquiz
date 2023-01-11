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
import view.Vocabulary
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer

object GraphicEditQuizMenu

/** edit quiz menu graphic interface  */
class GraphicEditQuizMenu(stage: Stage) extends GraphicView:

  val toggleCourseGroup: ToggleGroup = ToggleGroup()

  val toggleQuizGroup: ToggleGroup = ToggleGroup()

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _
  
  @FXML
  var selectCourseLabel: Label = _

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var selectQuizLabel: Label = _

  @FXML
  var quizVBox: VBox = _

  @FXML
  var questionLabel: Label = _

  @FXML
  var questionTextField: TextField = _

  @FXML
  var imagePathLabel: Label = _

  @FXML
  var imagePathTextField: TextField = _

  @FXML
  var scoreLabel: Label = _

  @FXML
  var scoreIntegerField: TextField = _

  @FXML
  var answersVBox: VBox = _

  @FXML
  var answerLabel: Label = _

  @FXML
  var answerCorrectCheckBox0: CheckBox = _

  @FXML
  var addAnswerButton: Button = _

  @FXML
  var removeAnswerButton: Button = _

  @FXML
  var editQuizButton: Button = _
  
  @FXML
  var deleteQuizButton: Button = _
  
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
    if answersVBox.getChildren.size()>0 then answersVBox.getChildren.remove(answersVBox.getChildren.size()-1)

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
      feedbackLabel.setText(Vocabulary.INVALID_CONFIGURATION)

  @FXML
  def deleteQuizButtonClicked(): Unit =
    if checkSelections then
      import controller.EditQuizMenuController.EditQuizAction
      sendEvent(EditQuizAction(Option.empty))
    else
      feedbackLabel.setText(Vocabulary.INVALID_SELECTION)

  loadGUI(stage, this, "edit_quiz_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.EDIT_QUIZ)
  selectCourseLabel.setText(Vocabulary.SELECT_COURSE)
  selectQuizLabel.setText(Vocabulary.SELECT_QUIZ)
  questionLabel.setText(Vocabulary.QUESTION)
  imagePathLabel.setText(Vocabulary.IMAGE_PATH)
  scoreLabel.setText(Vocabulary.POINTS)
  answerLabel.setText(Vocabulary.ANSWER + " ")
  answerCorrectCheckBox0.setText(Vocabulary.CORRECT)
  addAnswerButton.setText(Vocabulary.ADD_ANSWER)
  removeAnswerButton.setText(Vocabulary.REMOVE_ANSWER)
  editQuizButton.setText(Vocabulary.EDIT_QUIZ)
  deleteQuizButton.setText(Vocabulary.DELETE_QUIZ)

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
      feedbackLabel.setText(Vocabulary.QUIZ_EDITED)
      clearAllFields()
    case QuizDeletedUpdate =>
      feedbackLabel.setText(Vocabulary.QUIZ_DELETED)
      clearAllFields()
    case _ => {}

  private def checkInputs: Boolean =
    questionTextField.getText.nonEmpty && scoreIntegerField.getText().toIntOption.nonEmpty && checkSelections

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
      checkBox.setText(Vocabulary.CORRECT)
      checkBox.getStyleClass.add("checkbox")
      answerBox.getChildren.addAll(Label(Vocabulary.ANSWER+" "), textField, checkBox)
      answersVBox.getChildren.addAll(answerBox)
  }
