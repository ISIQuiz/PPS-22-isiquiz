package view

import View.*
import controller.SelectMenuController
import model.Session

object SelectMenuView:

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView */
  class SelectMenuViewImpl extends SelectMenuView :

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> SelectMenuController.AvailableActions.Back,
      2 -> SelectMenuController.AvailableActions.Start
    )

    override def draw[T](update: Option[T]): String =
      println("Menu selezione:")
      if (update.isDefined) {
        // Get courses from Option
        val savedCourses = update.get.asInstanceOf[Session].savedCourses
        // Map courses with index and number of quiz
        val printCourses = savedCourses.map(savedCourse =>
          s"${savedCourses.indexOf(savedCourse)+1}) ${savedCourse.courseId.courseName} (${savedCourse.quizList.size} quiz)")
        // Print courses list
        printCourses.foreach(course => println(course))
      }
      println("0) Menu principale")
      println("Selezione corsi/o (separati da -):")
      "SelectMenu"
