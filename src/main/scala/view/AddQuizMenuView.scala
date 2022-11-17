package view

import controller.actions.Action
import controller.AddQuizMenuController
import scala.collection.mutable.Map
import view.View.*
import view.updates.ViewUpdate

object AddQuizMenuView:

  /** AddQuizMenuView define aspects of a general AddQuizMenuView */
  trait AddQuizMenuView extends PageView

  /** A basic implementation of a AddQuizMenuView  */
  class AddQuizMenuViewImpl extends AddQuizMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> AddQuizMenuController.Back.asInstanceOf[Action[Any]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu aggiunta quiz:\n1) Menu principale")
      "AddQuizMenu"
