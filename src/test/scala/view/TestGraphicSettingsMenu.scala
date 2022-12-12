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
import view.graphicUI.GraphicSettingsMenu

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(Array(classOf[ApplicationExtension]))
class TestGraphicSettingsMenu extends ViewTest with ButtonTest:

  private var graphicSettingsMenu: GraphicSettingsMenu = _

  @Start
  def start(stage: Stage): Unit =
    stage.scene = Scene(1280, 720)
    graphicSettingsMenu = new GraphicSettingsMenu(stage)

  @Test def testBackButton(robot: FxRobot): Unit =
    testButton(graphicSettingsMenu.backButton, "Indietro")(robot)

  @Test def testImportButton(robot: FxRobot): Unit =
    testButton(graphicSettingsMenu.importButton, "Importa quiz")(robot)

  @Test def testExportButton(robot: FxRobot): Unit =
    testButton(graphicSettingsMenu.exportButton, "Esporta quiz")(robot)

  @Test def testEditCourseButton(robot: FxRobot): Unit =
    testButton(graphicSettingsMenu.editCourseButton, "Modifica corso")(robot)

  @Test def testEditQuizButton(robot: FxRobot): Unit =
    testButton(graphicSettingsMenu.editQuizButton, "Modifica quiz")(robot)
