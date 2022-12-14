package model

import model.Answer.*
import model.CourseIdentifier.*
import model.stats.PlayerStats.PlayerStats
import model.stats.PlayerStats.initStats
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import utils.storage.DefaultDataList.defaultCourseList
import utils.storage.DefaultDataList

class SessionTest extends AnyFunSuite with Matchers:

  import model.Session.*

  val session = Session(defaultCourseList, initStats)

  test("Creation Session") {
    session.savedCourses shouldEqual defaultCourseList
    session.playerStats shouldEqual initStats
  }

  import model.Session

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

  test("Change player stats") {
    val sessionChanged = Session.changePlayerStats(session, PlayerStats(1,2,3,4,5.5, List()))
    sessionChanged.playerStats.totalScore shouldEqual 1
    sessionChanged.playerStats.totalAnsweredQuestions shouldEqual 2
    sessionChanged.playerStats.totalCorrectAnswers shouldEqual 3
    sessionChanged.playerStats.totalAnswerPrecision shouldEqual 4
    sessionChanged.playerStats.totalAverageTimeAnswer shouldEqual 5.5
  }
