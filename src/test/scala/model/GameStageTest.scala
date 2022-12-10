package model

import model.Answer.*
import model.CourseIdentifier.CourseIdentifier
import model.Quiz.Quiz
import model.SavedCourse.SavedCourse
import model.settings.StandardGameSettings
import org.scalatest.funsuite.AnyFunSuite

import java.util.UUID

/** Tests for the [[GameStage]] */
class GameStageTest extends AnyFunSuite :

  val a1 = Answer("answer1", true)
  val a2 = Answer("answer2", false)
  val a3 = Answer("answer3", false)
  val a4 = Answer("answer4", true)
  val answerList1 = List(a1, a2, a3)
  val answerList2 = List(a4, a2, a3)

  val quiz1 = Quiz(question = "quiz1", answerList = answerList1, maxScore = 12, imagePath = Some("image1.png"))
  val quiz2 = Quiz(question = "quiz2", answerList = answerList2, maxScore = 12, imagePath = Some("image2.png"))
  val quiz3 = Quiz(question = "quiz3", answerList = answerList1, maxScore = 12, imagePath = Some("image3.png"))
  val quizList = List(quiz1, quiz2,quiz3)

  val course = Course(CourseIdentifier("PPS","LM","Unibo"))
  val quizInGame1 = QuizInGame(course, quiz1, answerList2)
  val quizInGame2 = QuizInGame(course, quiz2, answerList2)

  val savedCourse: SavedCourse = SavedCourse(CourseIdentifier("PPS","LM","Unibo"), Some("descrizione"), quizList)
  val gameStage = GameStage(List(savedCourse), quizInGame1, StandardGameSettings())

  /* tests for QuizInGame case class */
  test("Get course QuizInGame"){
    assert(quizInGame1.course == Course(CourseIdentifier("PPS","LM","Unibo")))
  }

  test("Get quiz QuizInGame") {
    assert(quizInGame1.quiz != Quiz(question = "quiz1", answerList = answerList1, maxScore = 12, imagePath = Some("image1.png")))
  }

  test("Get answer list QuizInGame") {
    assert(quizInGame1.answers == List(a4, a2, a3))
  }

  /* tests for GameStage class */
  test("Get courses in game GameStage") {
    assert(gameStage.coursesInGame == List(savedCourse))
  }

  test("Get quiz in game GameStage") {
    assert(gameStage.quizInGame == QuizInGame(course, quiz1, answerList2))
  }

  test("Get game settings GameStage") {
    assert(gameStage.gameSettings == StandardGameSettings())
  }

  test("Set quiz in game GameStage"){
    gameStage.quizInGame_(quizInGame2)
    assert(gameStage.quizInGame == quizInGame2)
  }

end GameStageTest
