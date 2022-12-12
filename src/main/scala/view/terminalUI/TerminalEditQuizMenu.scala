package view.terminalUI

import controller.EditQuizMenuController
import controller.EditQuizMenuController.*
import controller.actions.Action
import model.Quiz.Quiz
import model.SavedCourse
import model.SavedCourse.SavedCourse
import view.EditQuizMenuView.*
import view.View.*
import view.updates.ViewUpdate
import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object TerminalEditQuizMenu

/** Edit quiz menu terminal interface */
class TerminalEditQuizMenu extends TerminalView:

  var courseList: List[SavedCourse] = Nil
  var quizList: List[Quiz] = Nil

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu modifica quiz:\n1) Menu principale")
      println("Modifica Quiz")
    case CourseListUpdate(updateParameter) =>
      courseList = updateParameter.get
    case AskCourseSelectUpdate =>
      println("Seleziona il corso con il quiz da modificare")
      courseList.map(course => course.courseId.courseName).zipWithIndex.foreach { case (e, i) => println(i + "] " + e) }
      val courseIndex = readLine.toInt
      sendEvent(SelectCourseAction(courseList.lift(courseIndex)))
    case QuizListUpdate(updateParameter) =>
      quizList = updateParameter.get
    case AskQuizSelectUpdate =>
      println("Seleziona il quiz da modificare")
      quizList.map(quiz => quiz.question).zipWithIndex.foreach { case (e, i) => println(i + "] " + e) }
      val quizIndex = readLine.toInt
      sendEvent(SelectQuizAction(quizList.lift(quizIndex)))
    case AskQuizEditUpdate =>
      println("Inserisci domanda:")
      val question = readLine
      println("Inserisci score:")
      val score = readLine.toInt
      import model.Answer.Answer
      var answerList: List[Answer] = Nil
      while {
        println("Vuoi aggiungere una risposta? S:si"); "s" == readLine.toLowerCase
      } do
        println("Inserisci riposta")
        val answerText = readLine
        println("La risposta è corretta? S:si")
        val isCorrect = "s" == readLine.toLowerCase
        answerList = answerList.::(Answer(answerText, isCorrect))
      println("Vuoi aggiungere un'image path? S:si")
      var imagePath: Option[String] = None
      if "s" == readLine.toLowerCase then
        println("inserisci image path:")
        imagePath = Some(readLine)
      import model.Quiz.Quiz
      sendEvent(EditQuizAction(Option(Quiz(question = question, answerList = answerList, maxScore = score, imagePath = imagePath))))
    case QuizPrintUpdate(updateParameter) =>
      println(updateParameter.get)
    case QuizEditedUpdate =>
      println("Quiz Modificato!")
    case _ => {}
