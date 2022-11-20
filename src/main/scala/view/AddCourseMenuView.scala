package view

import controller.AddCourseMenuController
import controller.AddCourseMenuController.*
import controller.actions.Action
import view.View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import scala.collection.mutable.Map

object AddCourseMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

/** AddCourseMenuView define aspects of a general AddCourseMenuView */
  trait AddCourseMenuView extends PageView

  /** A basic implementation of a AddCourseMenuView  */
  class AddCourseMenuViewImpl extends AddCourseMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back
    )

    override def updateUI[T](update: ViewUpdate[T]): String =
      println("Menu aggiunta corsi:\n1) Menu principale")
      handleInput()
      "AddCourseMenu"
