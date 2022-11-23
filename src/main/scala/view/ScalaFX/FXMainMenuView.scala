package view.ScalaFX

import controller.MainMenuController
import controller.MainMenuController.*
import controller.actions.{Action, ParameterlessAction}
import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import utils.{TerminalInput, TerminalInputImpl}
import view.View.{PageView, TerminalView}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.Map
import view.View.ScalaFXView

object FXMainMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate

  /** MainMenuView define aspects of a general MainMenuView */
  trait MainMenuFXView extends ScalaFXView

  /** A basic implementation of a MainMenuView  */
  class FXMainMenuViewImpl extends MainMenuFXView, JFXApp3:
    override def start(): Unit = {
      stage = new JFXApp3.PrimaryStage {
        title.value = "Hello Stage"
        width = 600
        height = 450
        scene = new Scene {
          fill = LightGreen
          content = new Rectangle {
            x = 25
            y = 40
            width = 100
            height = 100
          }
        }
      }
    }

    override def updateUI[T](update: ViewUpdate[Any]): Unit =
//      start()
      println("ScalaFX Menu Principale")

//      View.handleInput()
