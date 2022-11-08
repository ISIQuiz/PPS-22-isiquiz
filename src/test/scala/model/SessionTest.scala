package model

import model.Answer.Answer
import model.Quiz.AnswerList.{cons, empty}
import model.Quiz.{AnswerList, Quiz}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class SessionTest extends AnyFunSuite with Matchers{

  val ansList: AnswerList = AnswerList.empty()

  val q1 = Quiz("quiz1", ansList, 12, Some("image1.png"))
  val q2 = Quiz("quiz2", ansList, 13, Some("image2.png"))
  val q3 = Quiz("quiz3", ansList, 8, Some("image3.png"))
  val q4 = Quiz("quiz4", ansList, 20, Some("image4.png"))


  val courseId: CourseIdentifier = CourseIdentifier("Paradigmi di Programmazione e Sviluppo",
    "Laurea Magistrale in Ingegneria e Scienze Informatiche",
    "Università di Bologna")
  val courseId2: CourseIdentifier = CourseIdentifier("Sistemi Operativi",
    "Laurea in Ingegneria e Scienze Informatiche",
    "Università di Bologna")

  val quizList = List(q1, q2)
  val quizList2 = List(q3, q4)

  val savedCourse: SavedCourse = SavedCourse(courseId, Some("descrizione"), quizList)
  val savedCourse2: SavedCourse = SavedCourse(courseId2, Some("descrizione2"), quizList2)

  val session = Session(List(savedCourse, savedCourse2))

  println(session.toString)

  test("Session test") {
    session.savedCourses.size shouldEqual 2
  }

  test("Testing change saved course list") {
    val courseIdChanged = CourseIdentifier("Course", "Degree", "University")
    val quizChanged = Quiz("quiz5", ansList, 20, Some("image5.png"))
    val savedCourse3: SavedCourse = SavedCourse(courseIdChanged, Some("descrizione2"), List(quizChanged))
    val sessionChanged = Session.changeSavedCourses(List[SavedCourse](savedCourse3))

    sessionChanged.savedCourses.size shouldEqual 1
  }



}
