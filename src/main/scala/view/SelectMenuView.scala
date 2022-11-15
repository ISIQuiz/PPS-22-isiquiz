package view

import View.*
import controller.SelectMenuController
import scala.collection.mutable.Map
import model.Session

object SelectMenuView:

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView */
  class SelectMenuViewImpl extends SelectMenuView :

    override val actionsMap: Map[Int, Enumeration] = Map(
      0 -> SelectMenuController.AvailableActions.Back
    )

    override def draw[T](update: Option[T]): String =
      println("Menu selezione:")
      
      if update.isDefined then
        // Get courses from Option
        val savedCourses = update.get.asInstanceOf[Session].savedCourses
        // Map courses with index and number of quiz
        val printCourses = savedCourses.map(savedCourse =>
          s"${savedCourses.indexOf(savedCourse)+1}) ${savedCourse.courseId.courseName} (${savedCourse.quizList.size} quiz)")
        // Print courses list
        printCourses.foreach(course => println(course))
        savedCourses.foreach(course => actionsMap += (savedCourses.indexOf(course)+1 -> SelectMenuController.AvailableActions.Selection(Option(savedCourses.indexOf(course)+1))))

      println("0) Menu principale")
      println("Seleziona un corso:")
      "SelectMenu"
