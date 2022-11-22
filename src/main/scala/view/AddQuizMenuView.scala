package view

import controller.actions.Action
import controller.AddQuizMenuController
import scala.collection.mutable.Map
import controller.AddQuizMenuController.*
import view.View.*
import view.updates.ViewUpdate

object AddQuizMenuView:

  /** AddQuizMenuView define aspects of a general AddQuizMenuView */
  trait AddQuizMenuView extends PageView

  /** A basic implementation of a AddQuizMenuView  */
  class AddQuizMenuViewImpl extends AddQuizMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit =
      println("Menu aggiunta quiz:\n1) Menu principale")
      handleInput()
