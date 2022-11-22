package view

import controller.AddCourseMenuController
import controller.AddCourseMenuController.*
import controller.actions.Action
import model.SavedCourse.SavedCourseImpl
import view.View.*
import view.updates.{ParameterlessViewUpdate, ViewUpdate}
import scala.collection.mutable.Map
import model.SavedCourse
import scala.io.StdIn.readLine

object AddCourseMenuView:

  case object DefaultPrint extends ParameterlessViewUpdate
  case class CoursePrint(override val updateParameter: Option[SavedCourse]) extends ViewUpdate(updateParameter)
  case object AskCoursePrint extends ParameterlessViewUpdate

/** AddCourseMenuView define aspects of a general AddCourseMenuView */
  trait AddCourseMenuView extends PageView

  /** A basic implementation of a AddCourseMenuView  */
  class AddCourseMenuViewImpl extends AddCourseMenuView:

    override val actionsMap: Map[String, Action[Any]] = Map(
      "1" -> Back
    )

    override def updateUI[T](update: ViewUpdate[Any]): Unit = update match
      case DefaultPrint =>
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
        import model.CourseIdentifier.CourseIdentifierImpl
        import model.Quiz.Quiz
        import model.Answer.Answer
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
