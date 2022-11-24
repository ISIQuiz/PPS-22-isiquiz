package view

import javafx.fxml.FXMLLoader

trait ViewComponent[A] {

  /** Returns the inner JavaFX component wrapped by this [[ViewComponent]]. */
//  val innerComponent: A
}

object ViewComponent:
  abstract class AbstractViewComponent[A](fxmlFileName: String) extends ViewComponent[A]:
    protected val loader: FXMLLoader = FXMLLoader()
    loader.setController(this)
    loader.setLocation(getClass.getResource(s"/fxml/$fxmlFileName"))

//
//  given fromComponentToInner[A]: Conversion[ViewComponent[A], A] with
//    def apply(component: ViewComponent[A]): A = component.innerComponent