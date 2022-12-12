package view

import org.testfx.assertions.api.Assertions
import org.testfx.api.FxRobot
import scalafx.scene.control.Button

trait ButtonTest:

  protected def testButton(button: Button, buttonText: String)(robot: FxRobot): Unit =
    Assertions.assertThat(button).isVisible
    Assertions.assertThat(button).hasText(buttonText)
