package view.terminalUI

import controller.ReviewMenuController
import controller.ReviewMenuController.End
import controller.actions.Action
import model.Answer.Answer
import model.SavedCourse
import view.ReviewMenuView.*
import view.View.*
import view.updates.ViewUpdate

import java.util.UUID
import scala.io.StdIn.readLine
import scala.collection.mutable.Map

object TerminalReviewMenu

/** Review terminal interface  */
class TerminalReviewMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> End,
  )

  var showAllAnswers = false

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu revisione :\n1) Menu principale")
      println("Revisione:")
    case CurrentReviewUpdate(updateParameter) => if updateParameter.isDefined then
      updateParameter.get.quizAnsweredList foreach (quizAnswered =>
        println(quizAnswered.quizInGame.quiz.question);
        println(s"(Punti: ${quizAnswered.quizInGame.quiz.maxScore}) - ${quizAnswered.quizInGame.course.courseId.courseName}");
        quizAnswered.quizInGame.answers
          .filter(ans => showAllAnswers || ans.isCorrect || (if quizAnswered.answerPlayer.isDefined then quizAnswered.answerPlayer.get == ans else false))
          .foreach(answerQuiz =>
            println(s"${answerQuiz.text}"+(if answerQuiz.isCorrect then "CORRETTA" else "SBAGLIATA"))
          );
      )
    case TotalPointsUpdate(updateParameter) =>
      println("Punti totali: "+updateParameter.get)
    case TotalCorrectAnswersUpdate(updateParameter) =>
      println("Risposte giuste totali: " + updateParameter.get)
    case _ => {}