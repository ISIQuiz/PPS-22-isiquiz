import controller.*
import scalafx.application.JFXApp3
import view.View.ViewFactory
import view.View.ViewFactory.GUIType.*

object Main extends JFXApp3:

  override def start(): Unit =
    ViewFactory.currentGUIType_(Terminal)
    AppController.startApp()
