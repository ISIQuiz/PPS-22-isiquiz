package model

import model.Answer.Answer
import model.Quiz.Quiz
import model.Review.*
import model.settings.StandardGameSettings
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class GameStageTest extends AnyFunSuite with Matchers:

  val course: Course = Course(CourseIdentifier("courseName", "s", "s"))
  val quizInGame: QuizInGame = QuizInGame(course,Quiz(question = "question", List(Answer("ans", false)), maxScore = 10), List(Answer("ansSelected", true)))
  val quizAnswered: QuizAnswered = QuizAnswered(quizInGame,None)
  val quizAnswered2: QuizAnswered = QuizAnswered(quizInGame, Option(Answer("ans", true)))
  val review: Review = Review()
  review.addQuizAnswered(quizAnswered)
  review.addQuizAnswered(quizAnswered2)

  val gameStage = GameStage()
  println(gameStage.toString)

  test("New quizInGame test") {
    quizInGame.quiz.question shouldEqual "question"
    quizInGame.quiz.answerList.head.text shouldEqual "ans"
    quizInGame.quiz.maxScore shouldEqual 10
    quizInGame.answers shouldEqual List(Answer("ansSelected", true))
    quizInGame.course.courseId.courseName shouldEqual "courseName"
  }

  test("New gameStage test") {
    gameStage.coursesInGame shouldEqual Nil
    gameStage.quizInGame shouldEqual null
    gameStage.quizInGamePresent shouldEqual false
    gameStage.review shouldEqual Review()
    gameStage.currentQuizNumber shouldEqual 1
    gameStage.gameSettings shouldEqual StandardGameSettings()
  }


  test("Add quizInGame in GameStage test") {
    gameStage.quizInGame_(quizInGame)
    gameStage.quizInGamePresent shouldEqual true
    gameStage.addReviewQuizNotAnswered
    gameStage.review.quizAnsweredList shouldEqual List(QuizAnswered(quizInGame, None))
    gameStage.currentQuizNumber shouldEqual 2
    gameStage.maxQuizzesReached shouldEqual false
    val answer: Answer = Answer("ans", true)
    gameStage.addReviewQuizAnswer(answer)
    gameStage.review.quizAnsweredList shouldEqual List(QuizAnswered(quizInGame, None), QuizAnswered(quizInGame, Option(answer)))
    gameStage.currentQuizNumber shouldEqual 3
    gameStage.maxQuizzesReached shouldEqual false
  }


