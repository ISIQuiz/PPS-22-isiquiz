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

object GraphicReviewMenu

/** Review menu graphic interface  */
class GraphicReviewMenu(stage: Stage) extends GraphicView:


  @FXML
  var rightAnswersLabel: Label = _


  @FXML
  var totPointsLabel: Label = _
  @FXML
  var rispTest: Label = _


  @FXML
  var quizAnsweredVBox: VBox = _

  @FXML
  var endButton: Button = _
  @FXML
  def endButtonClicked(): Unit =
    sendEvent(End)


  loadGUI(stage, this, "review_menu.fxml")

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case TotalCorrectAnswersUpdate(updateParameter) => if updateParameter.isDefined then
      Platform.runLater(() => {
        rightAnswersLabel.textProperty().set(s"Risposte corrette: ${updateParameter.get}")
      })
    case TotalPointsUpdate(updateParameter) => if updateParameter.isDefined then
      Platform.runLater(() => {
        totPointsLabel.textProperty().set(s"Punti totali: ${updateParameter.get}")
      })
    case CurrentReviewUpdate(updateParameter) => if updateParameter.isDefined then fillAnswerBox(updateParameter.get.quizAnsweredList)
    case _ => {}

    def fillAnswerBox(quizAnsweredList: List[QuizAnswered]): Unit =
      Platform.runLater(() => {
        quizAnsweredVBox.getChildren.clear()
        quizAnsweredList foreach (quizAnswered =>
          val quizVBox = VBox();
          quizVBox.getStyleClass.setAll("review-quiz-box");
          val quizQuestionLabel = Label(s"${quizAnswered.quizInGame.quiz.question} ${quizAnswered.quizInGame.course.courseId.courseName} " +
            s"Points: ${quizAnswered.quizInGame.quiz.maxScore}");
          quizQuestionLabel.getStyleClass.setAll("review-question");
          quizVBox.getChildren.addAll(quizQuestionLabel);
          quizAnswered.quizInGame.answers foreach (answerQuiz =>
            val quizAnswerLabel = Label(s"${answerQuiz.text}");
            quizAnswerLabel.getStyleClass.setAll("review-answer");
            quizVBox.getChildren.addAll(quizAnswerLabel);
            );
          quizAnsweredVBox.getChildren.addAll(quizVBox);
          )
      })
