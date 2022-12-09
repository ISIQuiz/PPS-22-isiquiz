package view.graphicUI

import controller.{AppController, StandardGameController}
import controller.StandardGameController.{Back, NextQuiz, SelectAnswer}
import javafx.application.Platform
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import utils.{GUILoader, Timer}
import utils.GUILoader.loadGUI
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, ProgressBar}
import javafx.stage.Stage
import model.GameStage
import model.settings.StandardGameSettings
import view.StandardGameMenuView.{AnswerFeedbackUpdate, DefaultUpdate, CurrentGameUpdate, TimeExpiredUpdate, TimerUpdate}

object GraphicStandardGameMenu

/** Standard Game menu graphic interface */
class GraphicStandardGameMenu(stage: Stage) extends GraphicView :

  @FXML
  var backButton: Button = _

  @FXML
  var courseLabel: Label = _

  @FXML
  var quizNumberLabel: Label = _

  @FXML
  var timeRemainingLabel: Label = _

  @FXML
  var pointsLabel: Label = _

  @FXML
  var timeProgressBar: ProgressBar = _

  @FXML
  var quizLabel: Label = _

  @FXML
  var firstAnswerButton: Button = _

  @FXML
  var secondAnswerButton: Button = _

  @FXML
  var thirdAnswerButton: Button = _

  @FXML
  var fourthAnswerButton: Button = _

  @FXML
  var nextButton: Button = _

  @FXML
  def backButtonClicked(): Unit =
    sendEvent(Back)

  @FXML
  def firstAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(0)))
    disableButton(answerButtons)

  @FXML
  def secondAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(1)))
    disableButton(answerButtons)

  @FXML
  def thirdAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(2)))
    disableButton(answerButtons)

  @FXML
  def fourthAnswerButtonClicked(): Unit =
    sendEvent(SelectAnswer(Option(3)))
    disableButton(answerButtons)

  @FXML
  def nextButtonClicked(): Unit =
    resetAnswerButton(answerButtons)
    sendEvent(NextQuiz)

  loadGUI(stage, this, "standard_game.fxml")

  val answerButtons: List[Button] = List(firstAnswerButton, secondAnswerButton, thirdAnswerButton, fourthAnswerButton)

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate => {}
    case CurrentGameUpdate(updateParameter) =>
      Platform.runLater(() => {
        val gameStage: GameStage = updateParameter.get
        courseLabel.setText(gameStage.quizInGame.course.courseId.courseName)
        quizNumberLabel.setText(s"${gameStage.review.numberQuizAnswered+1}/${gameStage.gameSettings.asInstanceOf[StandardGameSettings].maxQuizzes}")
        pointsLabel.setText(gameStage.quizInGame.quiz.maxScore.toString + " punti")
        quizLabel.setText(gameStage.quizInGame.quiz.question)
        firstAnswerButton.setText(gameStage.quizInGame.answers(0).text)
        secondAnswerButton.setText(gameStage.quizInGame.answers(1).text)
        thirdAnswerButton.setText(gameStage.quizInGame.answers(2).text)
        fourthAnswerButton.setText(gameStage.quizInGame.answers(3).text)
      })
    case AnswerFeedbackUpdate(updateParameter) =>
      val feedback = updateParameter.get
      feedback._1 match
        case 0 => if feedback._2 then firstAnswerButton.getStyleClass.add("correct-answer") else firstAnswerButton.getStyleClass.add("wrong-answer")
        case 1 => if feedback._2 then secondAnswerButton.getStyleClass.add("correct-answer") else secondAnswerButton.getStyleClass.add("wrong-answer")
        case 2 => if feedback._2 then thirdAnswerButton.getStyleClass.add("correct-answer") else thirdAnswerButton.getStyleClass.add("wrong-answer")
        case 3 => if feedback._2 then fourthAnswerButton.getStyleClass.add("correct-answer") else fourthAnswerButton.getStyleClass.add("wrong-answer")
    case TimerUpdate(updateParameter) =>
      val timer: Timer = updateParameter.get
      Platform.runLater(() => {
        timeRemainingLabel.setText(s"${timer.getRemainingTime.toInt}")
        timeProgressBar.setProgress(timer.getCompletionPercentage)
      })
    case TimeExpiredUpdate =>
      disableButton(answerButtons)

  def resetAnswerButton(buttons: List[Button]): Unit =
    buttons.foreach(button =>
      button.getStyleClass.remove("correct-answer");
      button.getStyleClass.remove("wrong-answer");
      button.setDisable(false);
    )

  def disableButton(buttons: List[Button]): Unit =
    buttons.foreach(button =>
      button.setDisable(true);
    )
