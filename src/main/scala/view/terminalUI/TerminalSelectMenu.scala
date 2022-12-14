package view.terminalUI

import controller.SelectMenuController
import controller.SelectMenuController.*
import controller.actions.{Action, ParameterlessAction}
import model.Session
import model.SavedCourse.*
import view.View.TerminalView
import view.updates.{ViewUpdate}
import view.SelectMenuView.*
import scala.collection.mutable.{ListBuffer, Map}

object TerminalSelectMenu

/** Select menu terminal interface */
class TerminalSelectMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "M" -> Back,
    "S" -> Start,
    "C" -> Custom
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu selezione:")
      println("M) Menu principale")
      println("S) Inizia il gioco")
      println("C) Impostazioni personalizzate")
      println("Seleziona un corso:")
    case CourseUpdate(updateParameter) =>
      if update.updateParameter.isDefined then
        val savedCourses: List[(SavedCourse, Boolean)] = updateParameter.get.asInstanceOf[List[(SavedCourse, Boolean)]]
        case class CourseToPrint(savedCourse: SavedCourse, isSelected: Boolean)
        var coursesToPrint: ListBuffer[CourseToPrint] = ListBuffer()

        savedCourses.foreach(course =>
          coursesToPrint += CourseToPrint(course._1, course._2)
        )
        coursesToPrint.foreach(courseToPrint =>
          val courseIndex = coursesToPrint.indexOf(courseToPrint) + 1
          val courseSelection = if courseToPrint.isSelected then "X" else " "
          val courseName = courseToPrint.savedCourse.courseId.courseName
          val courseQuizzesNumber = courseToPrint.savedCourse.quizList.size
          println(s"${courseIndex}) [${courseSelection}] ${courseName} (${courseQuizzesNumber} quiz)")
          actionsMap += (courseIndex.toString -> SelectMenuController.Selection(Option(courseIndex)))
        )
