package model

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class SavedCourseTest extends AnyFunSuite with Matchers:

  val a1: List[Answer] = List()

  val q1 = SavedQuiz("quiz1", 12, Some("image1.png"), a1)
  val q2 = SavedQuiz("quiz2", 13, Some("image2.png"), a1)
  val q3 = SavedQuiz("quiz3", 8, Some("image3.png"), a1)
  val q4 = SavedQuiz("quiz4", 20, Some("image4.png"), a1)

  /* Simple list of SavedQuiz */
  val quizList = List(q1, q2, q3, q4)

  val courseId: CourseIdentifier = CourseIdentifier("Paradigmi di Programmazione e Sviluppo", "Laurea Magistrale in Ingegneria e Scienze Informatiche", "Università di Bologna")

  /* Simple saved course */
  val savedCourse: SavedCourse = SavedCourse(courseId, Some("descrizione"), quizList)

  println(savedCourse.toString)

  test("The name should be equal to Paradigmi di Programmazione e Sviluppo") {
    savedCourse.courseId.courseName shouldEqual "Paradigmi di Programmazione e Sviluppo"
  }

  test("The course degree name should be equal to Laurea Magistrale in Ingegneria e Scienze Informatiche") {
    savedCourse.courseId.degreeName shouldEqual "Laurea Magistrale in Ingegneria e Scienze Informatiche"
  }

  test("The university name should be equal to Università di Bologna") {
    savedCourse.courseId.universityName shouldEqual "Università di Bologna"
  }

  test("The quiz list should be equal to the starting list") {
    savedCourse.quizList shouldEqual quizList
  }

  test("Testing change Course Id") {
    val courseIdChanged = CourseIdentifier("Course", "Degree", "University")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.courseName shouldEqual "Course"
    savedCourseChanged.courseId.degreeName shouldEqual "Degree"
    savedCourseChanged.courseId.universityName shouldEqual "University"
  }

  test("Testing change id CourseName") {
    val courseIdChanged = CourseIdentifier.changeCourseName(savedCourse.courseId, "Sistemi Operativi")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.courseName shouldEqual "Sistemi Operativi"
  }

  test("Testing change id DegreeName") {
    val courseIdChanged = CourseIdentifier.changeDegreeName(savedCourse.courseId, "Laurea in Ingegneria Informatica")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.degreeName shouldEqual "Laurea in Ingegneria Informatica"
  }

  test("Testing change id UniversityName") {
    val courseIdChanged = CourseIdentifier.changeUniversityName(savedCourse.courseId, "Università di Milano")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.universityName shouldEqual "Università di Milano"
  }

  test("Testing change description") {
    val savedCourseChanged = SavedCourse.changeDescription(savedCourse, Some("Corso per lo studio di..."))
    savedCourseChanged.description shouldEqual Some("Corso per lo studio di...")
  }

  test("Testing change quiz list") {
    val quizListChanged: List[SavedQuiz] = List(q1, q2)

    val savedCourseChanged = SavedCourse.changeQuizList(savedCourse, quizListChanged)
    savedCourseChanged.quizList.size shouldEqual 2
    println(savedCourseChanged.quizList.toString())
  }







