package view.graphicUI

import controller.SelectMenuController.{Back, Blitz, Custom, Selection, Start}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, CheckBox, Label, ScrollPane}
import javafx.scene.layout.VBox
import javafx.stage.Stage
import view.SelectMenuView.*
import javafx.application.Platform
import javafx.scene.input.MouseEvent
import model.SavedCourse.*
import view.Vocabulary

import java.util
import scala.collection.mutable.ListBuffer

object GraphicSelectMenu

/** Select menu graphic interface */
class GraphicSelectMenu(stage: Stage) extends GraphicView :

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var standardGameButton: Button = _

  @FXML
  var blitzGameButton: Button = _

  @FXML
  var customGameButton: Button = _

  @FXML
  var selectCourseLabel: Label = _

  @FXML
  var feedbackLabel: Label = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def standardGameButtonClicked(): Unit =
    if isCourseSelected then sendEvent(Start)

  @FXML
  def blitzGameButtonClicked(): Unit =
    if isCourseSelected then sendEvent(Blitz)

  @FXML
  def customGameButtonClicked(): Unit =
    if isCourseSelected then sendEvent(Custom)

  var checkBoxList: ListBuffer[CheckBox] = ListBuffer()
  loadGUI(stage, this, "select_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.PLAY)
  standardGameButton.setText(Vocabulary.STANDARD_GAME)
  blitzGameButton.setText(Vocabulary.BLITZ_GAME)
  customGameButton.setText(Vocabulary.CUSTOM_GAME)
  selectCourseLabel.setText(Vocabulary.SELECT_AT_LEAST_A_COURSE)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      setButtonEnable(standardGameButton, blitzGameButton, customGameButton)
    case CourseUpdate(updateParameter) =>
      if update.updateParameter.isDefined then
        val savedCourses: List[(SavedCourse, Boolean)] = updateParameter.get.asInstanceOf[List[(SavedCourse, Boolean)]]
        case class CourseToPrint(savedCourse: SavedCourse, isSelected: Boolean)
        var coursesToPrint: ListBuffer[CourseToPrint] = ListBuffer()
        setButtonEnable(standardGameButton, blitzGameButton, customGameButton)

        savedCourses.foreach(course =>
          coursesToPrint += CourseToPrint(course._1, course._2)
        )
        Platform.runLater { () =>
          coursesVBox.getChildren.clear()
          checkBoxList.clear()
          coursesToPrint.foreach(courseToPrint =>
            val checkbox: CheckBox = new CheckBox(s"${courseToPrint.savedCourse.courseId.courseName} (${courseToPrint.savedCourse.quizList.size} quiz)");
            checkbox.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
              sendEvent(Selection(Option(coursesVBox.getChildren.indexOf(checkbox))))
            );
            checkbox.setSelected(courseToPrint.isSelected);
            checkBoxList += checkbox;
            checkbox.getStyleClass.add("label-dark");
            coursesVBox.getChildren.add(checkbox)
          )
        }
    case CourseUnplayableUpdate =>
      feedbackLabel.setText(Vocabulary.INVALID_COURSES_SELECTED)
    case _ => {}

  /* function to check if at least one course is selected before starting a game */
  private def isCourseSelected: Boolean =
    checkBoxList.exists(checkBox => checkBox.isSelected)

  private def setButtonEnable(buttons: Button*): Unit = buttons.foreach(button => button.setDisable(!isCourseSelected))
