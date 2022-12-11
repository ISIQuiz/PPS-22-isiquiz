package view.graphicUI

import controller.AppController
import controller.StatisticsMenuController.{Back, SelectQuizAction}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import javafx.stage.Stage
import javafx.scene.control.{Button, ButtonType, Label, ToggleGroup}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import model.stats.PlayerStats
import model.stats.PlayerStats.{calculateTotalAnswerPrecision, initStats, updatePlayerStats}
import scalafx.scene.control.RadioButton
import utils.GUILoader
import utils.GUILoader.loadGUI
import utils.storage.ExportHandler
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import view.StatisticsMenuView.*

import scala.util.Try

object GraphicStatisticsMenu

/** Statistics menu graphic interface */
class GraphicStatisticsMenu(stage: Stage) extends GraphicView:

  val toggleCourseInStatsGroup: ToggleGroup = ToggleGroup()

  val toggleQuizGroup: ToggleGroup = ToggleGroup()

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var quizVBox: VBox = _

  @FXML
  var textLabel: Label = _

  @FXML
  var backButton: Button = _

  @FXML
  var resetStatsButton: Button = _

  @FXML
  var totalScoreLabel: Label = _

  @FXML
  var totalAnsweredQuestionsLabel: Label = _

  @FXML
  var totalCorrectAnswersLabel: Label = _

  @FXML
  var totalAnswerPrecisionLabel: Label = _

  @FXML
  var totalAverageTimeAnswerLabel: Label = _

  @FXML
  var globalTotalScoreLabel: Label = _

  @FXML
  var globalTotalAnsweredQuestionsLabel: Label = _

  @FXML
  var globalTotalCorrectAnswersLabel: Label = _

  @FXML
  var globalTotalAnswerPrecisionLabel: Label = _

  @FXML
  var globalTotalAverageTimeAnswerLabel: Label = _


  @FXML
  def backButtonClicked: Unit =
    sendEvent(Back)

  @FXML
  def resetStatsButtonClicked: Unit =
    val alert: Alert = Alert(
      AlertType.CONFIRMATION,
      "Sei sicuro di voler azzerare tutte le statistiche  ?",
      ButtonType.YES,
      ButtonType.NO,
      ButtonType.CANCEL
    )
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      AppController.changePlayerStats(initStats)
      ExportHandler.exportDataToPersonalDirectory(initStats)
      sendEvent(Back)
    }

  loadGUI(stage, this, "statistics_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      Platform.runLater { () =>
        val p = AppController.session.playerStats
        globalTotalScoreLabel.setText(p.totalScore.toString)
        globalTotalAnsweredQuestionsLabel.setText(p.totalAnsweredQuestions.toString)
        globalTotalCorrectAnswersLabel.setText(p.totalCorrectAnswers.toString)
        globalTotalAnswerPrecisionLabel.setText(p.totalAnswerPrecision.toString + " %")
        globalTotalAverageTimeAnswerLabel.setText(p.totalAverageTimeAnswer.toString)
      }
    case CourseInStatsListUpdate(updateParameter) =>
      Platform.runLater { () =>
        coursesVBox.getChildren.clear()
        updateParameter.get.foreach(courseInStats =>
          val radioButton = RadioButton(s"${courseInStats.course.courseId.courseName} (${courseInStats.quizInStatsList.size} quiz)");
          radioButton.setToggleGroup(toggleCourseInStatsGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            val playerStats = updatePlayerStats(List(courseInStats))
            setTextLabels(
              courseInStats.course.courseId.courseName,
              playerStats.totalScore,
              playerStats.totalAnsweredQuestions,
              playerStats.totalCorrectAnswers,
              playerStats.totalAnswerPrecision,
              playerStats.totalAverageTimeAnswer
            )
            import controller.StatisticsMenuController.SelectCourseAction
            sendEvent(SelectCourseAction(Option(courseInStats)));
          );
          coursesVBox.getChildren.addAll(radioButton)
        )
      }
    case QuizInStatsListUpdate(updateParameter) =>
      Platform.runLater { () =>
        quizVBox.getChildren.clear()
        updateParameter.get.foreach(quizInStats =>
          val question = PlayerStats.getQuizQuestionById(quizInStats.quizId, AppController.session.savedCourses)
          val radioButton = RadioButton(question);
          radioButton.setToggleGroup(toggleQuizGroup);
          radioButton.getStyleClass.add("label-dark");
          radioButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event =>
            setTextLabels(
              question,
              quizInStats.totalScore,
              quizInStats.totalSeen,
              quizInStats.totalRightAnswers,
              calculateTotalAnswerPrecision(quizInStats.totalSeen, quizInStats.totalRightAnswers),
              quizInStats.averageTimeAnswer
            )
            import controller.StatisticsMenuController.SelectQuizAction
            sendEvent(SelectQuizAction(Option(quizInStats)));
          );
          quizVBox.getChildren.addAll(radioButton)
        )
      }
    case _ => {}


  private def setTextLabels(title: String, totalScore: Int, totalAnsweredQuestions: Int, totalCorrectAnswers: Int, totalAnswerPrecision: Int, totalAverageTimeAnswer: Double): Unit =
    textLabel.setText(title)
    totalScoreLabel.setText(totalScore.toString)
    totalAnsweredQuestionsLabel.setText(totalAnsweredQuestions.toString)
    totalCorrectAnswersLabel.setText(totalCorrectAnswers.toString)
    totalAnswerPrecisionLabel.setText(totalAnswerPrecision.toString + " %")
    totalAverageTimeAnswerLabel.setText(totalAverageTimeAnswer.toString)