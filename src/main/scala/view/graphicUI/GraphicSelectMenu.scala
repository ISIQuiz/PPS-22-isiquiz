package view.graphicUI

import controller.SelectMenuController.{Back, Custom, Start, Selection}
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.GUILoader
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, CheckBox, ScrollPane}
import javafx.scene.layout.VBox
import javafx.stage.Stage
import view.SelectMenuView.*
import javafx.application.Platform
import javafx.scene.input.MouseEvent
import model.SavedCourse

import java.util
import scala.collection.mutable.ListBuffer

object GraphicSelectMenu

/** Select menu graphic interface  */
class GraphicSelectMenu(stage: Stage) extends GraphicView:

  @FXML
  var backButton: Button = _

  @FXML
  var coursesScrollPane: ScrollPane = _

  @FXML
  var standardGameButton: Button = _

  @FXML
  var reviewGameButton: Button = _

  @FXML
  var customGameButton: Button = _

  @FXML
  var coursesVBox: VBox = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def standardGameButtonClicked(): Unit =
    sendEvent(Start)

  @FXML
  def reviewGameButtonClicked(): Unit = ???

  @FXML
  def customGameButtonClicked(): Unit =
    sendEvent(Custom)

  loadGUI(stage, this, "select_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate => { }
    case CourseUpdate(updateParameter) =>
      if update.updateParameter.isDefined then
        val savedCourses: List[(SavedCourse, Boolean)] = updateParameter.get.asInstanceOf[List[(SavedCourse, Boolean)]]
        case class CourseToPrint(savedCourse: SavedCourse, isSelected: Boolean)
        var coursesToPrint: ListBuffer[CourseToPrint] = ListBuffer()

        savedCourses.foreach(course =>
          coursesToPrint += CourseToPrint(course._1, course._2)
        )
        Platform.runLater { () =>
          coursesVBox.getChildren.clear()
          coursesToPrint.foreach(courseToPrint =>
            val checkbox: CheckBox = new CheckBox(s"${courseToPrint.savedCourse.courseId.courseName} (${courseToPrint.savedCourse.quizList.size} quiz)");
            checkbox.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
              sendEvent(Selection(Option(coursesVBox.getChildren.indexOf(checkbox))))
            );
            checkbox.setSelected(courseToPrint.isSelected);
            coursesVBox.getChildren.add(checkbox)
          )

        }