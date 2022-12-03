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
import view.graphicUI.GraphicStatisticsMenu

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(Array(classOf[ApplicationExtension]))
class TestGraphicStatisticsMenu extends ViewTest with ButtonTest with LabelTest:

  private var graphicStatisticsMenu: GraphicStatisticsMenu = _

  @Start
  def start(stage: Stage): Unit =
    stage.scene = Scene(1280, 720)
    graphicStatisticsMenu = new GraphicStatisticsMenu(stage)

  @Test def testBackButton(robot: FxRobot): Unit =
    testButton(graphicStatisticsMenu.backButton, "back")(robot)

  @Test def testTotalScoreLabel(robot: FxRobot): Unit =
    testLabel(graphicStatisticsMenu.totalScoreLabel)(robot)

  @Test def testTotalAnsweredQuestionsLabel(robot: FxRobot): Unit =
    testLabel(graphicStatisticsMenu.totalAnsweredQuestionsLabel)(robot)

  @Test def testTotalCorrectAnswersLabel(robot: FxRobot): Unit =
    testLabel(graphicStatisticsMenu.totalCorrectAnswersLabel)(robot)

  @Test def testTotalAnswerPrecisionLabel(robot: FxRobot): Unit =
    testLabel(graphicStatisticsMenu.totalAnswerPrecisionLabel)(robot)

  @Test def testTotalAverageTimeAnswerLabel(robot: FxRobot): Unit =
    testLabel(graphicStatisticsMenu.totalAverageTimeAnswerLabel)(robot)




