package view

import org.testfx.assertions.api.Assertions
import org.testfx.api.FxRobot
import org.testfx.framework.junit5.{ApplicationExtension, Start}
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.{Test, TestInstance}
import org.junit.jupiter.api.extension.ExtendWith
import javafx.stage.Stage
import scalafx.scene.Scene
import scalafx.Includes.jfxStage2sfx
import scalafx.Includes.jfxButton2sfx

import view.graphicUI.GraphicMainMenu


@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(Array(classOf[ApplicationExtension]))
class TestGraphicMainMenu extends ViewTest with ButtonTest:


  private var graphicMainMenu: GraphicMainMenu = _

  @Start
  def start(stage: Stage): Unit =
    stage.scene = Scene(1280, 720)
    graphicMainMenu = new GraphicMainMenu(stage)


  @Test def testSelectButton(robot: FxRobot): Unit =
    testButton(graphicMainMenu.selectButton, "Gioca")(robot)
