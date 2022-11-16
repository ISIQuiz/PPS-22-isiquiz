package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.SelectMenuController
import controller.actions.{Action, ParameterlessAction}
import scala.collection.mutable.Map
import model.Session

object SelectMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class CourseUpdate[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView */
  class SelectMenuViewImpl extends SelectMenuView :

    override val actionsMap: Map[Int, Action[Any]] = Map(
      0 -> SelectMenuController.Back.asInstanceOf[Action[Any]]
    )

    override def draw[T](update: ViewUpdate[T]): String = update match
      case DefaultUpdate =>
        println("Menu selezione:")
        println("0) Menu principale")
        println("Seleziona un corso:")
        "DefaultUpdate"
      case CourseUpdate(updateParameter) =>
        if update.updateParameter.isDefined then
          // Get courses from Option
          val savedCourses = update.updateParameter.get.asInstanceOf[Session].savedCourses
          // Map courses with index and number of quiz
          val printCourses = savedCourses.map(savedCourse =>
            s"${savedCourses.indexOf(savedCourse)+1}) ${savedCourse.courseId.courseName} (${savedCourse.quizList.size} quiz)")
          // Print courses list
          printCourses.foreach(course => println(course))
          savedCourses.foreach(course => actionsMap += (savedCourses.indexOf(course)+1 -> SelectMenuController.Selection(actionParameter = Option(savedCourses.indexOf(course)+1))))
        "CourseUpdate"
      case _ => "Errore"