package view

import controller.actions.Action
import controller.AddQuizMenuController

import scala.collection.mutable.Map
import controller.AddQuizMenuController.Back
import model.Answer.Answer
import model.SavedCourse
import view.View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.io.StdIn.readLine

object AddQuizMenuView:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class AskCoursePrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  case class QuizPrint[T](override val updateParameter: Option[T]) extends ViewUpdate(updateParameter)
  case object AskQuizPrint extends ParameterlessViewUpdate


  /** AddQuizMenuView define aspects of a general AddQuizMenuView */
  trait AddQuizMenuView extends TerminalView

  /** A basic implementation of a AddQuizMenuView  */
  class AddQuizMenuViewImpl extends AddQuizMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "B" -> Back,
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
      case DefaultUpdate =>
        println("Menu aggiunta quiz:\n1) Menu principale")
        println("Aggiunta quiz:")
        handleInput()
      case AskCoursePrint(param) =>
        println("Seleziona il corso al quale aggiungere la domanda")
        val courseList = param.get.asInstanceOf[List[SavedCourse]]
        courseList.map(course=>course.courseId.courseName).zipWithIndex.foreach{ case (e, i) => println(i+"] "+e) }
        val courseIndex = readLine.toInt
        import controller.AddQuizMenuController.AddCourseAction
        sendEvent(AddCourseAction(courseList.lift(courseIndex)))
      case QuizPrint(quiz) =>
        import model.Quiz.*
        println("Quiz Aggiunto!")
        println(printQuizFull(quiz.get.asInstanceOf[Quiz]))
      case AskQuizPrint =>
        println("Inserisci domanda:")
        val question = readLine
        println("Inserisci score:")
        val score = readLine.toInt
        import model.Answer.Answer
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
        import model.Quiz.Quiz
        import controller.AddQuizMenuController.AddQuizAction
        sendEvent(AddQuizAction(Option(Quiz(question, answerList, score, imagePath))))
