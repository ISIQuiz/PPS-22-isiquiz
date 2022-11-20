package view

import View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import controller.SelectMenuController
import controller.SelectMenuController.*
import controller.actions.{Action, ParameterlessAction}
import scala.collection.mutable.{ListBuffer, Map}
import model.{SavedCourse, Session}

object SelectMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView */
  class SelectMenuViewImpl extends SelectMenuView :

    override val actionsMap: Map[String, Action[Any]] = Map(
      "M" -> Back,
      "S" -> Start
    )

    override def updateUI[T](update: ViewUpdate[T]): String = update match
      case DefaultUpdate =>
        println("Menu selezione:")
        println("M) Menu principale")
        println("S) Inizia il gioco")
        println("Seleziona un corso:")
        handleInput()
        "DefaultUpdate"
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
        "CourseUpdate"
      case _ => "Errore"