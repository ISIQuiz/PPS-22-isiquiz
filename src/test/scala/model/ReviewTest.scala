package model

import model.Quiz.Quiz
import model.Answer.Answer
import model.Review.*
import model.CourseIdentifier.CourseIdentifier
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ReviewTest extends AnyFunSuite with Matchers:

  val course: Course = Course(CourseIdentifier("courseName", "s", "s"))
  val quizInGame: QuizInGame = QuizInGame(course,Quiz(question = "question", List(Answer("ans", false)), maxScore = 10), List(Answer("ansSelected", true)))
  val quizAnswered: QuizAnswered = QuizAnswered(quizInGame,None)

  val review: Review = Review()

  test("New quizAnswered test") {
    quizAnswered.answerPlayer shouldEqual None
    quizAnswered.quizInGame.answers shouldEqual List(Answer("ansSelected", true))
    quizAnswered.score shouldEqual 0
    quizAnswered.timeToAnswer shouldEqual 0
    quizAnswered.quizInGame.course.courseId.courseName shouldEqual "courseName"
  }

  test("New review test") {
    review.quizAnsweredList shouldEqual Nil
  }

  test("Add quizAnswered in review test") {
    review.addQuizAnswered(quizAnswered)
    review.quizAnsweredList shouldEqual List(quizAnswered)
    review.numberQuizAnswered shouldEqual 1
    val quizAnswered2: QuizAnswered = QuizAnswered(quizInGame,Option(Answer("ans", true)))
    review.addQuizAnswered(quizAnswered2)
    review.numberQuizAnswered shouldEqual 2

  }