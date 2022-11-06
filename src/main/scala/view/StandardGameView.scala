package view

import View.*
import controller.StandardGameController

object StandardGameView:

  /** StandardGameView define aspects of a general StandardGameView */
  trait StandardGameView extends PageView

  /** A basic implementation of a SelectMenuView  */
  class StandardGameViewImpl extends StandardGameView:

    override val actionsMap: Map[Int, Enumeration] = Map(
      1 -> StandardGameController.AvailableActions.Back
    )

    override def draw(): String =
      println("Standard quiz:\n1) Termina quiz")
      "StandardGame"
