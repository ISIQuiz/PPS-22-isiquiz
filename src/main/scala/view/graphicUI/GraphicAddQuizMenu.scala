package view.graphicUI

import com.sun.javafx.scene.control.IntegerField
import controller.AddQuizMenuController.{Back, SelectCourseAction}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination, KeyEvent, MouseEvent}
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.stage.Stage
import model.Answer.Answer
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import scalafx.geometry.Insets
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.AddQuizMenuView.*
import view.View.{GraphicView, sendEvent}
import view.Vocabulary
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer

object GraphicAddQuizMenu

/** add quiz menu graphic interface  */
class GraphicAddQuizMenu(stage: Stage) extends GraphicView:

  val toggleGroup: ToggleGroup = ToggleGroup()

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _
  
  @FXML
  var selectCourseLabel: Label = _

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var questionLabel: Label = _
  
  @FXML
  var questionTextField: TextField = _

  @FXML
  var imagePathBox: HBox = _

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
  var addQuizButton: Button = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def addAnswerButtonClicked(): Unit =
    addAnswerGUI()

  @FXML
  def removeAnswerButtonClicked(): Unit =
    if answersVBox.getChildren.size()>0 then answersVBox.getChildren.remove(answersVBox.getChildren.size()-1)

  @FXML
  def addQuizButtonClicked(): Unit =
    if checkInputs then
      val answerList:ListBuffer[Answer] = ListBuffer()
      answersVBox.getChildren.forEach( hBox =>
        val answerTextField = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(1).asInstanceOf[TextField]
        val answerCorrectCheckBox:CheckBox = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(2).asInstanceOf[CheckBox]
        answerList += Answer(answerTextField.getText, answerCorrectCheckBox.isSelected)
      )
      val quiz = Quiz(question=questionTextField.getText, answerList = answerList.toList, maxScore = scoreIntegerField.getText.toInt, imagePath = imagePathTextField.getText match
        case "" => None
        case text => Some(text)
      )
      import controller.AddQuizMenuController.AddQuizAction
      sendEvent(AddQuizAction(Option(quiz)))
    else
      feedbackLabel.setText(Vocabulary.INVALID_CONFIGURATION)

  loadGUI(stage, this, "add_quiz_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.ADD_QUIZ)
  selectCourseLabel.setText(Vocabulary.SELECT_COURSE)
  questionLabel.setText(Vocabulary.QUESTION)
  imagePathLabel.setText(Vocabulary.IMAGE_PATH)
  imagePathBox.setVisible(false)
  imagePathBox.setManaged(false)
  scoreLabel.setText(Vocabulary.POINTS)
  addAnswerButton.setText(Vocabulary.ADD_ANSWER)
  removeAnswerButton.setText(Vocabulary.REMOVE_ANSWER)
  addQuizButton.setText(Vocabulary.ADD_QUIZ)
  clearPageInputs()
  stage.getScene.addEventHandler(KeyEvent.KEY_RELEASED, e => {
    if KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN).`match`(e) then addQuizButtonClicked()
  })

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseListUpdate(updateParameter) =>
      Platform.runLater { () =>
        coursesVBox.getChildren.clear()
        updateParameter.get.foreach(savedCourse =>
          val radioButton = RadioButton(savedCourse.courseId.courseName);
          radioButton.setToggleGroup(toggleGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            radioButton.setSelected(true);
            sendEvent(SelectCourseAction(Option(savedCourse)))
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizAddedUpdate =>
      feedbackLabel.setText(Vocabulary.QUIZ_ADDED)
      clearPageInputs()
    case _ => {}

  private def clearPageInputs(): Unit =
    questionTextField.clear()
    if checkSelections then questionTextField.requestFocus()
    imagePathTextField.clear()
    scoreIntegerField.setText("1")
    while answersVBox.getChildren.size() > 0 do answersVBox.getChildren.remove(0)
    addAnswerGUI()

  private def checkInputs: Boolean =
    questionTextField.getText.nonEmpty && scoreIntegerField.getText().toIntOption.nonEmpty && checkSelections

  private def checkSelections: Boolean =
    toggleGroup.getToggles.filtered(_.isSelected).size() > 0

  private def addAnswerGUI(): Unit =
    Platform.runLater { () =>
      val idNum = answersVBox.getChildren.size()
      val answerBox: HBox = HBox()
      answerBox.setAlignment(javafx.geometry.Pos.CENTER)
      answerBox.setPadding(Insets.apply(5, 0, 0, 5))
      val textField: TextField = TextField()
      textField.setId("answerTextField" + idNum)
      textField.getStyleClass.add("text-field-large")
      val checkBox = CheckBox()
      checkBox.setText(Vocabulary.CORRECT)
      checkBox.setId("answerCorrectCheckBox" + idNum)
      checkBox.getStyleClass.add("checkbox")
      if idNum==0 then checkBox.setSelected(true)
      answerBox.getChildren.addAll(Label(Vocabulary.ANSWER+" "), textField, checkBox)
      answersVBox.getChildren.addAll(answerBox)
    }
