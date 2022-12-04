package view

import javafx.stage.Stage
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.{Test, TestInstance}
import org.testfx.api.FxRobot
import org.testfx.assertions.api.Assertions
import org.testfx.framework.junit5.{ApplicationExtension, Start}
import scalafx.Includes.{jfxButton2sfx, jfxStage2sfx}
import scalafx.scene.Scene
import view.graphicUI.GraphicCustomMenu

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(Array(classOf[ApplicationExtension]))
class TestGraphicCustomMenu extends ViewTest with ButtonTest:

  private var graphicCustomMenu: GraphicCustomMenu = _

  @Start
  def start(stage: Stage): Unit =
    stage.scene = Scene(1280, 720)
    graphicCustomMenu = new GraphicCustomMenu(stage)

  @Test def testBackButton(robot: FxRobot): Unit =
    testButton(graphicCustomMenu.backButton, "Indietro")(robot)

  @Test def testImportButton(robot: FxRobot): Unit =
    testButton(graphicCustomMenu.startButton, "Avvia")(robot)
