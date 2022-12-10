package view.graphicUI


import com.sun.javafx.scene.control.IntegerField
import controller.AddQuizMenuController.{SelectCourseAction, Back}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
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
import view.AddQuizMenuView.*
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate

import scala.collection.mutable.ListBuffer

object GraphicAddQuizMenu

/** add quiz menu graphic interface  */
class GraphicAddQuizMenu(stage: Stage) extends GraphicView:


  val toggleGroup: ToggleGroup = ToggleGroup()

  @FXML
  var coursesScrollPane: ScrollPane = _

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var questionTextField: TextField = _

  @FXML
  var scoreIntegerField: TextField = _

  @FXML
  var answersVBox: VBox = _

  @FXML
  var imagePathTextField: TextField = _

  @FXML
  var answerTextField: TextField = _

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
    if answersVBox.getChildren.size()>1 then answersVBox.getChildren.remove(answersVBox.getChildren.size()-1)

  @FXML
  def addQuizButtonClicked(): Unit =
    if checkInputs then
      val answerList:ListBuffer[Answer] = ListBuffer()
      answersVBox.getChildren.forEach( hBox =>
        val answerTextField = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(1).asInstanceOf[TextField]
        val answerCorrectCheckBox:CheckBox = hBox.asInstanceOf[HBox].getChildrenUnmodifiable.get(3).asInstanceOf[CheckBox]
        answerList += Answer(answerTextField.getText, answerCorrectCheckBox.isSelected)
      )
      val quiz = Quiz(question=questionTextField.getText, answerList = answerList.toList, maxScore = scoreIntegerField.getText.toInt, imagePath = imagePathTextField.getText match
        case "" => None
        case text => Some(text)
      )
      import controller.AddQuizMenuController.AddQuizAction
      sendEvent(AddQuizAction(Option(quiz)))
    else
      feedbackLabel.setText("Configurazione Invalida")

  loadGUI(stage, this, "add_quiz_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case CourseListUpdate(updateParameter) =>
      Platform.runLater { () =>
        coursesVBox.getChildren.clear()
        updateParameter.get.foreach(savedCourse =>
          val radioButton = RadioButton(savedCourse.courseId.courseName);
          radioButton.setToggleGroup(toggleGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            sendEvent(SelectCourseAction(Option(savedCourse)))
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizAddedUpdate =>
      feedbackLabel.setText("Quiz aggiunto")
      questionTextField.clear()
      imagePathTextField.clear()
      scoreIntegerField.setText("0")
      while answersVBox.getChildren.size()>0 do answersVBox.getChildren.remove(0)
      addAnswerGUI()
    case _ => {}


  private def checkInputs: Boolean =
    questionTextField.getText.nonEmpty && toggleGroup.getToggles.removeIf(_.isSelected)


  private def addAnswerGUI(): Unit =
    Platform.runLater { () =>
      val idNum = answersVBox.getChildren.size()
      val answerBox: HBox = HBox()
      answerBox.setAlignment(javafx.geometry.Pos.CENTER)
      answerBox.setPadding(Insets.apply(10, 0, 0, 10))
      val textField: TextField = TextField()
      textField.setId("answerTextField" + idNum)
      textField.getStyleClass.add("text-field-extra-large")
      textField.setText(answerTextField.getText)
      textField.setEditable(false)
      val checkBox = CheckBox()
      checkBox.setText("corretta")
      checkBox.setId("answerCorrectCheckBox" + idNum)
      textField.getStyleClass.add("checkbox-dark")
      answerBox.getChildren.addAll(textField, checkBox)
      answersVBox.getChildren.addAll(answerBox)
    }