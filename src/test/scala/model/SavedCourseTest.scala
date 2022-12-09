package model

import model.Answer.Answer
import model.Quiz.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class SavedCourseTest extends AnyFunSuite with Matchers:

  val ansList: List[Answer] = List()

  val q1 = Quiz(question = "quiz1", answerList = ansList, maxScore = 12, imagePath = Some("image1.png"))
  val q2 = Quiz(question = "quiz2", answerList = ansList, maxScore = 13, imagePath = Some("image2.png"))
  val q3 = Quiz(question = "quiz3", answerList = ansList, maxScore = 8, imagePath = Some("image3.png"))
  val q4 = Quiz(question = "quiz4", answerList = ansList, maxScore = 20, imagePath = Some("image4.png"))
  
  /* Simple list of SavedQuiz */
  val quizList = List(q1, q2, q3, q4)

  val courseId: CourseIdentifier = CourseIdentifier("Paradigmi di Programmazione e Sviluppo", "Laurea Magistrale in Ingegneria e Scienze Informatiche", "Università di Bologna")

  /* Simple saved course */
  val savedCourse: SavedCourse = SavedCourse(courseId, Some("descrizione"), quizList)

  test("Get course identifier") {
    savedCourse.courseId shouldEqual courseId
  }

  test("Get course description") {
    assert(savedCourse.description.isDefined)
    savedCourse.description shouldEqual Some("descrizione")
    savedCourse.description.get shouldEqual "descrizione"
  }

  test("Get quiz list") {
    savedCourse.quizList shouldEqual quizList
  }

  test("Get course name") {
    savedCourse.courseId.courseName shouldEqual "Paradigmi di Programmazione e Sviluppo"
  }

  test("Get degree name") {
    savedCourse.courseId.degreeName shouldEqual "Laurea Magistrale in Ingegneria e Scienze Informatiche"
  }

  test("Get university name") {
    savedCourse.courseId.universityName shouldEqual "Università di Bologna"
  }

  test("Change course identifier") {
    val courseIdChanged = CourseIdentifier("PCD", "Laurea Triennale", "UniBo")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.courseName shouldEqual "PCD"
    savedCourseChanged.courseId.degreeName shouldEqual "Laurea Triennale"
    savedCourseChanged.courseId.universityName shouldEqual "UniBo"
  }

  test("Change description") {
    val savedCourseChanged = SavedCourse.changeDescription(savedCourse, Some("Corso per lo studio di..."))
    savedCourseChanged.description shouldEqual Some("Corso per lo studio di...")
  }

  test("Change quiz list") {
    val quizListChanged: List[Quiz] = List(q1, q2)

    val savedCourseChanged = SavedCourse.changeQuizList(savedCourse, quizListChanged)
    savedCourseChanged.quizList.size shouldEqual 2
  }

  test("Change id Course Name") {
    val courseIdChanged = CourseIdentifier.changeCourseName(savedCourse.courseId, "Sistemi Operativi")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.courseName shouldEqual "Sistemi Operativi"
  }

  test("Change id Degree Name") {
    val courseIdChanged = CourseIdentifier.changeDegreeName(savedCourse.courseId, "Laurea in Ingegneria Informatica")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.degreeName shouldEqual "Laurea in Ingegneria Informatica"
  }

  test("Change id University Name") {
    val courseIdChanged = CourseIdentifier.changeUniversityName(savedCourse.courseId, "Università di Milano")
    val savedCourseChanged = SavedCourse.changeCourseIdentifier(savedCourse, courseIdChanged)

    savedCourseChanged.courseId.universityName shouldEqual "Università di Milano"
  }