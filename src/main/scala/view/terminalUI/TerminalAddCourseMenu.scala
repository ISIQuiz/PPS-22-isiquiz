package view.terminalUI

import controller.AddCourseMenuController
import controller.AddCourseMenuController.*
import view.AddCourseMenuView.*
import controller.actions.Action
import model.SavedCourse.SavedCourse
import view.View.*
import view.updates.ViewUpdate
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz
import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object TerminalAddCourseMenu

/** Add course menu terminal interface */
class TerminalAddCourseMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu aggiunta corsi:\n1) Menu principale")
      println("Aggiunta corso:")
    case AskCourseUpdate =>
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

      val course = SavedCourse(
        courseId = CourseIdentifier(
          courseName = courseNameIns,
          degreeName = degreeNameIns,
          universityName = universityNameIns
        ),
        description = descriptionIns,
        quizList = Nil
      )
      import controller.AddCourseMenuController.AddCourseAction
      sendEvent(AddCourseAction(Option(course)))
    case CoursePrintUpdate(course) =>
      println(course.get)
    case CourseAddedUpdate =>
      println("Corso Aggiunto!")
    case _ => {}
