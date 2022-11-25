import controller.*
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.shape.Rectangle
import view.View.ViewFactory
import view.View.ViewFactory.GUIType.*

object Main extends JFXApp3:
  override def start(): Unit =
    ViewFactory.currentGUIType_(Terminal)
    new Thread(AppController).start
