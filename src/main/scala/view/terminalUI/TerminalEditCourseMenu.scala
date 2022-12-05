package view.terminalUI

import controller.EditCourseMenuController
import controller.EditCourseMenuController.*
import controller.actions.Action
import model.SavedCourse
import model.SavedCourse.SavedCourseImpl
import view.EditCourseMenuView.*
import view.View.*
import view.updates.ViewUpdate

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object TerminalEditCourseMenu

/** Edit course menu terminal interface */
class TerminalEditCourseMenu extends TerminalView:

  var courseList: List[SavedCourse] = Nil
  
  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu modifica corso:\n1) Menu principale")
      println("Modifica Corso")
    case CourseUpdate(updateParameter) =>
      courseList = updateParameter.get
    case AskCourseUpdate =>
      println("Seleziona il corso da modificare")
      courseList.map(course => course.courseId.courseName).zipWithIndex.foreach { case (e, i) => println(i + "] " + e) }
      val courseIndex = readLine.toInt
      sendEvent(SelectCourseAction(courseList.lift(courseIndex)))
    case AskCourseEditUpdate =>
      println("Inserisci nome corso:")
      val courseNameIns = readLine
      println("Inserisci nome corso di laurea:")
      val degreeNameIns = readLine
      println("Inserisci nome università:")
      val universityNameIns = readLine
      println("Vuoi aggiungere una descrizione del corso? S:si")
      var descriptionIns: Option[String] = None
      if "s" == readLine.toLowerCase then
        println("inserisci descrizione del corso:")
        descriptionIns = Some(readLine)
      import model.CourseIdentifier.CourseIdentifierImpl
      import model.Quiz.Quiz
      val course = SavedCourseImpl(
        courseId = CourseIdentifierImpl(
          courseName = courseNameIns,
          degreeName = degreeNameIns,
          universityName = universityNameIns
        ),
        description = descriptionIns,
        quizList = Nil
      )
      import controller.EditCourseMenuController.EditCourseAction
      sendEvent(EditCourseAction(Option(course)))
    case CoursePrintUpdate(course) =>
      import model.SavedCourse
      println(course.get)
    case CourseEditedUpdate =>
      println("Corso Modificato!")
    case _ => {}