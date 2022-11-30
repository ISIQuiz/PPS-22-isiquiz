package model

import model.Answer.*
import model.stats.PlayerStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import utils.DefaultDataList
import utils.DefaultDataList.{defaultCourseList, defaultPlayerStats}


class SessionTest extends AnyFunSuite with Matchers:

  val session = Session(defaultCourseList, defaultPlayerStats)

  println(session.toString)

  test("New session test") {
    session.savedCourses shouldEqual defaultCourseList
    session.playerStats shouldEqual defaultPlayerStats
  }

  test("Check if the course identifier of the first element of course list in Session changed") {

    val courseList = session.savedCourses
    val course = courseList.head

    // Change course identifier
    val courseIdChanged = CourseIdentifier("Course", "Degree", "University")
    val courseChanged = SavedCourse.changeCourseIdentifier(course, courseIdChanged)

    // Change the first course identifier
    val courseListChanged = courseList.updated(0, courseChanged)

    // Session with edited saved course list
    val sessionChanged = Session.changeSavedCourses(session, courseListChanged)

    // Check if course id has changed
    sessionChanged.savedCourses.head.courseId shouldEqual courseIdChanged

    //Check if second course still exist
    sessionChanged.savedCourses.apply(1).courseId.courseName shouldEqual session.savedCourses.apply(1).courseId.courseName
  }

  test("Check if player stats in Session changed") {
    val sessionChanged = Session.changePlayerStats(session, PlayerStats(1,2,3, List()))
    sessionChanged.playerStats.totalScore shouldEqual 1
    sessionChanged.playerStats.totalAnsweredQuestions shouldEqual 2
    sessionChanged.playerStats.totalCorrectAnswers shouldEqual 3
  }

