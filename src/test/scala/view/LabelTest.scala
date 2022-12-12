package view

import javafx.scene.control.Label
import org.testfx.api.FxRobot
import org.testfx.assertions.api.Assertions

trait LabelTest:

  protected def testLabel(label: Label)(robot: FxRobot): Unit =
    Assertions.assertThat(label).isVisible
    Assertions.assertThat(label).isEnabled
