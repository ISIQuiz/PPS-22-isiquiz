package view.graphicUI

import controller.AppController
import controller.StatisticsMenuController.{Back, SelectQuizAction}
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Button, ButtonBar, ButtonType, Label, ToggleGroup}
import javafx.stage.Stage
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import model.stats.{PlayerStats, QuizInStats}
import model.stats.PlayerStats.{calculateTotalAnswerPrecision, initStats, updatePlayerStats}
import scalafx.scene.control.RadioButton
import utils.GUILoader
import utils.GUILoader.loadGUI
import utils.storage.ExportHandler
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import view.StatisticsMenuView.*
import view.Vocabulary

import scala.util.Try

object GraphicStatisticsMenu

/** Statistics menu graphic interface */
class GraphicStatisticsMenu(stage: Stage) extends GraphicView:

  val toggleCourseInStatsGroup: ToggleGroup = ToggleGroup()

  val toggleQuizGroup: ToggleGroup = ToggleGroup()

  val yesButton = ButtonType(Vocabulary.YES, ButtonBar.ButtonData.YES);

  val cancelButton = ButtonType(Vocabulary.CANCEL, ButtonBar.ButtonData.CANCEL_CLOSE);

  @FXML
  var coursesVBox: VBox = _

  @FXML
  var quizVBox: VBox = _

  @FXML
  var backButton: Button = _

  @FXML
  var menuLabel: Label = _

  @FXML
  var selectCourseLabel: Label = _

  @FXML
  var selectQuizLabel: Label = _

  @FXML
  var textLabel: Label = _

  @FXML
  var totalScoreTitleLabel: Label = _

  @FXML
  var totalScoreLabel: Label = _

  @FXML
  var quizAnsweredLabel: Label = _

  @FXML
  var totalAnsweredQuestionsLabel: Label = _

  @FXML
  var correctAnswersLabel: Label = _

  @FXML
  var totalCorrectAnswersLabel: Label = _

  @FXML
  var precisionLabel: Label = _

  @FXML
  var totalAnswerPrecisionLabel: Label = _

  @FXML
  var averageTimeAnswerLabel: Label = _

  @FXML
  var totalAverageTimeAnswerLabel: Label = _

  @FXML
  var textLabel2: Label = _

  @FXML
  var globalTotalScoreTitleLabel: Label = _

  @FXML
  var globalTotalScoreLabel: Label = _

  @FXML
  var globalTotalAnsweredQuestionsTitleLabel: Label = _

  @FXML
  var globalTotalAnsweredQuestionsLabel: Label = _

  @FXML
  var globalTotalCorrectAnswersTitleLabel: Label = _

  @FXML
  var globalTotalCorrectAnswersLabel: Label = _

  @FXML
  var globalTotalAnswerPrecisionTitleLabel:Label = _

  @FXML
  var globalTotalAnswerPrecisionLabel: Label = _

  @FXML
  var globalTotalAverageTimeAnswerTitleLabel: Label = _

  @FXML
  var globalTotalAverageTimeAnswerLabel: Label = _

  @FXML
  var resetStatsButton: Button = _

  @FXML
  def backButtonClicked: Unit =
    sendEvent(Back)

  @FXML
  def resetStatsButtonClicked: Unit =
    val alert: Alert = Alert(AlertType.CONFIRMATION, "", yesButton, cancelButton)
    alert.setTitle("Reset statistiche")
    alert.setHeaderText("Sei sicuro di voler azzerare tutte le statistiche?")
    alert.showAndWait();
    if (alert.getResult == yesButton) {
      AppController.changePlayerStats(initStats)
      ExportHandler.exportDataToPersonalDirectory(initStats)
      sendEvent(Back)
    }

  loadGUI(stage, this, "statistics_menu.fxml")
  backButton.setText(Vocabulary.BACK)
  menuLabel.setText(Vocabulary.STATISTICS)
  selectCourseLabel.setText(Vocabulary.SELECT_COURSE)
  selectQuizLabel.setText(Vocabulary.SELECT_QUIZ)
  textLabel.setText(Vocabulary.QUIZ_STATS)
  totalScoreTitleLabel.setText(Vocabulary.POINTS)
  quizAnsweredLabel.setText(Vocabulary.QUIZ_ANSWERED)
  correctAnswersLabel.setText(Vocabulary.CORRECT_ANSWERS)
  precisionLabel.setText(Vocabulary.PRECISION)
  averageTimeAnswerLabel.setText(Vocabulary.AVERAGE_TIME_ANSWER)
  textLabel2.setText(Vocabulary.GLOBAL_STATS)
  globalTotalScoreTitleLabel.setText(Vocabulary.POINTS)
  globalTotalAnsweredQuestionsTitleLabel.setText(Vocabulary.QUIZ_ANSWERED)
  globalTotalCorrectAnswersTitleLabel.setText(Vocabulary.CORRECT_ANSWERS)
  globalTotalAnswerPrecisionTitleLabel.setText(Vocabulary.PRECISION)
  globalTotalAverageTimeAnswerTitleLabel.setText(Vocabulary.AVERAGE_TIME_ANSWER)
  resetStatsButton.setText(Vocabulary.RESET_STATS)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      Platform.runLater { () =>
        val p = AppController.session.playerStats
        globalTotalScoreLabel.setText(p.totalScore.toString)
        globalTotalAnsweredQuestionsLabel.setText(p.totalAnsweredQuestions.toString)
        globalTotalCorrectAnswersLabel.setText(p.totalCorrectAnswers.toString)
        globalTotalAnswerPrecisionLabel.setText(p.totalAnswerPrecision.toString + " %")
        globalTotalAverageTimeAnswerLabel.setText(p.totalAverageTimeAnswer.toString + " s")
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
          val question = QuizInStats.getQuizQuestionById(quizInStats.quizId, AppController.session.savedCourses)
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

  // Set the text of the labels in stats
  private def setTextLabels(title: String, totalScore: Int, totalAnsweredQuestions: Int, totalCorrectAnswers: Int, totalAnswerPrecision: Int, totalAverageTimeAnswer: Double): Unit =
    textLabel.setText(title)
    totalScoreLabel.setText(totalScore.toString)
    totalAnsweredQuestionsLabel.setText(totalAnsweredQuestions.toString)
    totalCorrectAnswersLabel.setText(totalCorrectAnswers.toString)
    totalAnswerPrecisionLabel.setText(totalAnswerPrecision.toString + " %")
    totalAverageTimeAnswerLabel.setText(totalAverageTimeAnswer.toString +" s")
