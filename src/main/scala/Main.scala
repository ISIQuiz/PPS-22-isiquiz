import controller.*
import view.View.ViewFactory
import view.View.ViewFactory.GUIType.*

@main def run() =
  ViewFactory.currentGUIType_(ScalaFX)
  AppController.startApp()
