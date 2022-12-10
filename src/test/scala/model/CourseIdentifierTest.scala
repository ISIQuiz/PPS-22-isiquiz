package model

import model.CourseIdentifier.*
import org.scalatest.funsuite.AnyFunSuite

/** Tests for the [[CourseIdentifier]] object */
class CourseIdentifierTest extends AnyFunSuite :

  val course = CourseIdentifier("PPS","LM","Unibo")

  test("Creation Course Identifier") {
    assert(CourseIdentifier("PPS","LM","Unibo") == course)
  }

  test("Get course name") {
    assert(course.courseName == "PPS")
  }

  test("Get degree name") {
    assert(course.degreeName == "LM")
  }

  test("Get university name") {
    assert(course.universityName == "Unibo")
  }

  test("Change course name") {
    val newCourse1 = changeCourseName(course, "PCD")
    assert(newCourse1.courseName == "PCD")

    val newCourse2 = changeCourseName(newCourse1, "PERVASIVE")
    assert(newCourse2.courseName == "PERVASIVE")
  }

  test("Change degree name") {
    val newCourse1 = changeDegreeName(course, "Laurea")
    assert(newCourse1.degreeName == "Laurea")

    val newCourse2 = changeDegreeName(newCourse1, "Laurea Magistrale")
    assert(newCourse2.degreeName == "Laurea Magistrale")
  }

  test("Change university name") {
    val newCourse1 = changeUniversityName(course, "Unimi")
    assert(newCourse1.universityName == "Unimi")

    val newCourse2 = changeUniversityName(newCourse1, "Università di Bologna")
    assert(newCourse2.universityName == "Università di Bologna")
  }

end CourseIdentifierTest
