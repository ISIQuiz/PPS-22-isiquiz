import controller.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

object Main extends JFXApp3:

  override def start(): Unit =
    AppController.startApp()
