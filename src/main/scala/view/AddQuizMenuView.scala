package view

import controller.actions.Action
import controller.AddQuizMenuController
import view.View.*
import view.updates.ViewUpdate

object AddQuizMenuView:

  /** AddQuizMenuView define aspects of a general AddQuizMenuView */
  trait AddQuizMenuView extends PageView

  /** A basic implementation of a AddQuizMenuView  */
  class AddQuizMenuViewImpl extends AddQuizMenuView:

    override def actionsMap[T]: Map[Int, Action[T]] = Map(
      1 -> AddQuizMenuController.Back.asInstanceOf[Action[T]]
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu aggiunta quiz:\n1) Menu principale")
      "AddQuizMenu"
