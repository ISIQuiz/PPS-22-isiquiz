package view.terminalUI

import controller.AddQuizMenuController
import controller.AddQuizMenuController.*
import controller.actions.Action
import model.Answer.Answer
import model.SavedCourse.SavedCourse
import view.View.*
import view.updates.ViewUpdate
import view.AddQuizMenuView.*
import model.Quiz.*
import java.util.UUID
import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object TerminalAddQuizMenu

/** Add quiz menu terminal interface  */
class TerminalAddQuizMenu extends TerminalView:

  var courseList:List[SavedCourse] = Nil

  override val actionsMap: Map[String, Action[Any]] = Map(
    "B" -> Back,
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu aggiunta quiz:\n1) Menu principale")
      println("Aggiunta quiz:")
    case CourseListUpdate(updateParameter) =>
      courseList = updateParameter.get
    case AskCourseSelectUpdate =>
      println("Seleziona il corso al quale aggiungere la domanda")
      courseList.map(course=>course.courseId.courseName).zipWithIndex.foreach{ case (e, i) => println(i+"] "+e) }
      val courseIndex = readLine.toInt
      sendEvent(SelectCourseAction(courseList.lift(courseIndex)))
    case AskQuizUpdate =>
      println("Inserisci domanda:")
      val question = readLine
      println("Inserisci score:")
      val score = readLine.toInt
      var answerList:List[Answer] = Nil
      while {println("Vuoi aggiungere una risposta? S:si"); "s" == readLine.toLowerCase} do
        println("Inserisci riposta")
        val answerText = readLine
        println("La risposta è corretta? S:si")
        val isCorrect = "s" == readLine.toLowerCase
        answerList = answerList.::(Answer(answerText, isCorrect))
      println("Vuoi aggiungere un'image path? S:si")
      var imagePath:Option[String] = None
      if "s"==readLine.toLowerCase then
        println("inserisci image path:")
        imagePath = Some(readLine)
      sendEvent(AddQuizAction(Option(Quiz(question = question, answerList = answerList, maxScore = score, imagePath = imagePath))))
    case QuizPrintUpdate(updateParameter) =>
      println(printQuizFull(updateParameter.get))
    case QuizAddedUpdate =>
      println("Quiz Aggiunto!")
      sendEvent(Back)
    case _ => {}
