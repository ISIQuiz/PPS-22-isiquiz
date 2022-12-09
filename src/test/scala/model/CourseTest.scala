package model

import model.Course.*
import org.scalatest.funsuite.AnyFunSuite

import java.util.UUID

/** Tests for the [[Course]] object */
class CourseTest extends AnyFunSuite :

  val course = Course(CourseIdentifier("PPS","LM","Unibo"))

  test("Creation Course") {
    assert(Course(CourseIdentifier("PPS","LM","Unibo")) == course)
  }

  test("Get course name") {
    assert(course.courseId.courseName == "PPS")
  }

  test("Get degree name") {
    assert(course.courseId.degreeName == "LM")
  }

  test("Get university name") {
    assert(course.courseId.universityName == "Unibo")
  }

end CourseTest
