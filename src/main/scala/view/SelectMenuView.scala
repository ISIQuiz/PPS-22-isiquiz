package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import controller.SelectMenuController
import controller.actions.{Action, ParameterlessAction}
import model.Session

object SelectMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general SelectMenuView */
  trait SelectMenuView extends PageView

  /** A basic implementation of a SelectMenuView */
  class SelectMenuViewImpl extends SelectMenuView :

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> SelectMenuController.Back.asInstanceOf[Action[T]],
      2 -> SelectMenuController.Start.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu selezione:\n1) Menu principale\n2) Avvia quiz")
      if (update.updateParameter.isDefined) {
        // Get courses from Option
        val savedCourses = update.updateParameter.get.asInstanceOf[Session].savedCourses
        // Map courses with index and number of quiz
        val printCourses = savedCourses.map(savedCourse =>
          s"${savedCourses.indexOf(savedCourse)+1}) ${savedCourse.courseId.courseName} (${savedCourse.quizList.size} quiz)")
        // Print courses list
        printCourses.foreach(course => println(course))
      }
      println("0) Menu principale")
      println("Selezione corsi/o (separati da -):")
      "SelectMenu"
