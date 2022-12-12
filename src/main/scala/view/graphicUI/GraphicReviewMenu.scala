package view.graphicUI

import javafx.fxml.FXML
import javafx.scene.control.{Button, Label}
import javafx.stage.Stage
import utils.GUILoader
import utils.GUILoader.loadGUI
import view.View.{GraphicView, sendEvent}
import view.updates.ViewUpdate
import view.ReviewMenuView.*
import controller.ReviewMenuController.*
import javafx.application.Platform
import javafx.scene.layout.VBox
import model.QuizAnswered
import scalafx.geometry.Insets
import scalafx.scene.layout.Pane

import scala.util.Try

object GraphicReviewMenu

/** Review menu graphic interface */
class GraphicReviewMenu(stage: Stage) extends GraphicView:

  var showAllAnswers = false

  @FXML
  var totRightAnswersLabel: Label = _

  @FXML
  var totPointsLabel: Label = _

  @FXML
  var quizAnsweredVBox: VBox = _

  @FXML
  var endButton: Button = _

  @FXML
  def endButtonClicked(): Unit =
    sendEvent(End)

  @FXML
  def filterButtonClicked(): Unit =
    showAllAnswers = !showAllAnswers

  loadGUI(stage, this, "review_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case TotalCorrectAnswersUpdate(updateParameter) => if updateParameter.isDefined then
      Platform.runLater(() => {
        totRightAnswersLabel.textProperty().set(s"Risposte corrette: ${updateParameter.get}")
      })
    case TotalPointsUpdate(updateParameter) => if updateParameter.isDefined then
      Platform.runLater(() => {
        totPointsLabel.textProperty().set(s"Punti totali: ${updateParameter.get}")
      })
    case CurrentReviewUpdate(updateParameter) => if updateParameter.isDefined then
      fillAnswerBox(updateParameter.get.quizAnsweredList)
    case _ => {}

  def fillAnswerBox(quizAnsweredList: List[QuizAnswered]): Unit =
    Platform.runLater(() => {
      quizAnsweredVBox.getChildren.clear()
      for
        quizAnswered <- quizAnsweredList
      do
        val quizVBox = VBox()
        quizVBox.getStyleClass.setAll("review-quiz-box")
        val quizQuestionLabel = Label(quizAnswered.quizInGame.quiz.question)
        quizQuestionLabel.getStyleClass.setAll("review-question")
        quizVBox.getChildren.addAll(quizQuestionLabel)
        val quizDescLabel = Label(
          s"(Punti: ${quizAnswered.score}/${quizAnswered.quizInGame.quiz.maxScore}) " +
            getQuizDescriptionLabel(quizAnswered) +
            s"- ${quizAnswered.quizInGame.course.courseId.courseName}"
        );
        quizDescLabel.getStyleClass.setAll("label-dark")
        quizVBox.getChildren.addAll(quizDescLabel)
        for
          answerQuiz <- quizAnswered.quizInGame.answers;
          if (showAllAnswers || answerQuiz.isCorrect || (if quizAnswered.answerPlayer.isDefined then quizAnswered.answerPlayer.get == answerQuiz else false))
        do
          val quizAnswerLabel = Label(s"${answerQuiz.text}")
          if answerQuiz.isCorrect then quizAnswerLabel.getStyleClass.setAll("review-correct-answer") else quizAnswerLabel.getStyleClass.setAll("review-wrong-answer")
          quizVBox.getChildren.addAll(quizAnswerLabel);
        quizAnsweredVBox.setSpacing(10);
        quizAnsweredVBox.getChildren.addAll(quizVBox);
    })

  private def getQuizDescriptionLabel(quizAnswered: QuizAnswered): String =
    val cond = Try(quizAnswered.answerPlayer.get.isCorrect).getOrElse(false)
    if (cond)
      s"- (Tempo: "+ f"${quizAnswered.timeToAnswer}%.2f" +") "
    else
      ""
