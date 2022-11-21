package view

import controller.CustomMenuController
import controller.CustomMenuController.*
import controller.actions.Action
import view.View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map

object CustomMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** CustomMenuView define aspects of a general CustomMenuView */
  trait CustomMenuView extends PageView

  /** A basic implementation of a CustomMenuView  */
  class CustomMenuViewImpl extends CustomMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back
    )

    override def updateUI[T](update: ViewUpdate[T]): String =
      println("Menu impostazioni personalizzate:\n1) Menu principale")
      handleInput()
      "CustomMenu"
