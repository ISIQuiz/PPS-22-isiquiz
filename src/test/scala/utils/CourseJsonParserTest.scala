package utils

import model.SavedCourse
import org.scalatest.funsuite.AnyFunSuite
import scala.util.{Failure, Success}
import DefaultCourseList.defaultCourseList

class CourseJsonParserTest extends AnyFunSuite:
  val courseJsonParser: CourseJsonParser = CourseJsonParser()
  val savedCourses : List[SavedCourse] = defaultCourseList

  test("Test serializing and deserializing json string of saved courses"){
    courseJsonParser.deserializeSavedCourses(courseJsonParser.serializeSavedCourses(savedCourses)) match
      case Success(savedCourses) => savedCourses.equals(savedCourses)
      case _ => fail()
  }


