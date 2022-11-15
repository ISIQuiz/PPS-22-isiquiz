package view

import controller.AddCourseMenuController
import controller.AddCourseMenuController.Back
import controller.actions.Action
import view.View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}

object AddCourseMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

/** AddCourseMenuView define aspects of a general AddCourseMenuView */
  trait AddCourseMenuView extends PageView

  /** A basic implementation of a AddCourseMenuView  */
  class AddCourseMenuViewImpl extends AddCourseMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> Back.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu aggiunta corsi:\n1) Menu principale")
      "AddCourseMenu"
