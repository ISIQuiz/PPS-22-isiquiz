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
import view.graphicUI.GraphicSelectMenu

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(Array(classOf[ApplicationExtension]))
class TestGraphicSelectMenu extends ViewTest with ButtonTest:

  private var graphicSelectMenu: GraphicSelectMenu = _

  @Start
  def start(stage: Stage): Unit =
    stage.scene = Scene(1280, 720)
    graphicSelectMenu = new GraphicSelectMenu(stage)

  @Test def testBackButton(robot: FxRobot): Unit =
    testButton(graphicSelectMenu.backButton, "Indietro")(robot)

  @Test def testStandardGameButton(robot: FxRobot): Unit =
    testButton(graphicSelectMenu.standardGameButton, "Partita standard")(robot)

  @Test def testCustomGameButton(robot: FxRobot): Unit =
    testButton(graphicSelectMenu.customGameButton, "Partita personalizzata")(robot)
