package view.terminalUI

import controller.AddCourseMenuController
import controller.AddCourseMenuController.*
import controller.actions.Action
import model.SavedCourse
import model.SavedCourse.SavedCourseImpl
import view.View.*
import view.terminalUI.TerminalAddCourseMenu.{AskCoursePrint, CoursePrint, DefaultUpdate}
import view.updates.{ParameterlessViewUpdate, ViewUpdate}

import scala.collection.mutable.Map
import scala.io.StdIn.readLine

object TerminalAddCourseMenu:

  case object DefaultUpdate extends ParameterlessViewUpdate
  case class CoursePrint(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)
  case object AskCoursePrint extends ParameterlessViewUpdate

/** Add course menu terminal interface */
class TerminalAddCourseMenu extends TerminalView:

  override val actionsMap: Map[String, Action[Any]] = Map(
    "1" -> Back
  )

  override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
    case DefaultUpdate =>
      println("Menu aggiunta corsi:\n1) Menu principale")
      println("Aggiunta corso:")
      handleInput()
    case CoursePrint(course) =>
      println("Corso Aggiunto!")
      import model.SavedCourse
      println(course.get)
    case AskCoursePrint =>
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
      import model.Answer.Answer
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
      import controller.AddCourseMenuController.AddCourseAction
      sendEvent(AddCourseAction(Option(course)))
