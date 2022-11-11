package model

import model.Answer.Answer
import model.Quiz.AnswerList.{cons, empty}
import model.Quiz.{AnswerList, Quiz}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import resources.SampleCourseList


class SessionTest extends AnyFunSuite with Matchers{

  val session = Session(SampleCourseList.courseList)

  println(session.toString)

  test("New session test") {
    session.savedCourses.size shouldEqual SampleCourseList.courseList.size
  }

  test("Check if the course identifier of the first element of course list in Session changed") {

    val courseList = session.savedCourses
    val course = courseList.apply(0)

    // Change course identifier
    val courseIdChanged = CourseIdentifier("Course", "Degree", "University")
    val courseChanged = SavedCourse.changeCourseIdentifier(course, courseIdChanged)

    // Change the first course identifier
    val courseListChanged = courseList.updated(0, courseChanged)

    // Session with edited saved course list
    val sessionChanged = Session.changeSavedCourses(courseListChanged)

    // Check if course id has changed
    sessionChanged.savedCourses.apply(0).courseId shouldEqual courseIdChanged

    //Check if second course still exist
    sessionChanged.savedCourses.apply(1).courseId.courseName shouldEqual session.savedCourses.apply(1).courseId.courseName
  }
}
