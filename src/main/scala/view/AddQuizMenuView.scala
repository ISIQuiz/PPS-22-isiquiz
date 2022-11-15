package view

import controller.AddQuizMenuController
import scala.collection.mutable.Map
import view.View.*

object AddQuizMenuView:

  /** AddQuizMenuView define aspects of a general AddQuizMenuView */
  trait AddQuizMenuView extends PageView

  /** A basic implementation of a AddQuizMenuView  */
  class AddQuizMenuViewImpl extends AddQuizMenuView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> AddQuizMenuController.AvailableActions.Back
    )

    override def draw[T](update: Option[T]): String =
      println("Menu aggiunta quiz:\n1) Menu principale")
      "AddQuizMenu"
