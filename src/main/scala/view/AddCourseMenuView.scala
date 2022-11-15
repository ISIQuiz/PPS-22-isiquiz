package view

import controller.AddCourseMenuController
import view.View.*
import scala.collection.mutable.Map

object AddCourseMenuView:

  /** AddCourseMenuView define aspects of a general AddCourseMenuView */
  trait AddCourseMenuView extends PageView

  /** A basic implementation of a AddCourseMenuView  */
  class AddCourseMenuViewImpl extends AddCourseMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> AddCourseMenuController.AvailableActions.Back
    )

    override def draw[T](update: Option[T]): String =
      println("Menu aggiunta corsi:\n1) Menu principale")
      "AddCourseMenu"
