package view

import View.*
import view.updates.{ViewUpdate, ParameterlessViewUpdate}
import scala.collection.mutable.Map
import controller.SettingsMenuController
import controller.SettingsMenuController.*
import controller.actions.Action

object SettingsMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** SettingsMenuView define aspects of a general SettingsMenuView */
  trait SettingsMenuView extends PageView

  /** A basic implementation of a SettingsMenuView  */
  class SettingsMenuViewImpl extends SettingsMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back,
      "2" -> AddCourse,
      "3" -> AddQuiz
    )

    override def draw[T](update: ViewUpdate[T]): String =
      println("Menu impostazioni:\n1) Menu principale\n2) Aggiungi corso\n3) Aggiungi quiz")
      "SettingsMenu"
