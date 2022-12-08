package model

import org.scalatest.funsuite.AnyFunSuite
import model.Answer.*
import Quiz.*
import List.*

/** Tests for the [[Quiz]] object */
class QuizTest extends AnyFunSuite :

  val a1 = Answer("answer1", true)
  val a2 = Answer("answer2", false)
  val a3 = Answer("answer3", false)
  val answerList = List(a1, a2, a3)
  val quiz: Quiz = Quiz(question = "question", answerList = answerList, maxScore = 10)

  test("Creation Quiz") {
    assert(quiz != Quiz(question = "question", answerList = List(a1, a2, a3), maxScore = 10))
  }

  test("Change max score"){
    val quiz1 = changeMaxScore(quiz, 50)
    assert(quiz1.maxScore == 50)

    val quiz2 = changeMaxScore(quiz1, 30)
    assert(quiz2.maxScore == 30)
  }

  test("Change image path") {
    assert(!quiz.imagePath.isDefined && quiz.imagePath.isEmpty)

    val quiz1 = changeImagePath(quiz, Option("resources/img.png"))
    assert(quiz1.imagePath == Option("resources/img.png"))
    assert(quiz1.imagePath.isDefined && quiz1.imagePath.get == "resources/img.png")
  }

  test("Change question") {
    val quiz1 = changeQuestion(quiz, "New Quiz")
    assert(quiz1.question == "New Quiz")

    val quiz2 = changeQuestion(quiz1, "QUESTION")
    assert(quiz2.question == "QUESTION")
  }

  test("Change answer list"){
    val a4 = Answer("answer4", true)
    val answerList1 = answerList ::: List(a4)
    val quiz1 = changeAnswerList(quiz, answerList1)

    assert(answerList.size == 3)
    assert(answerList1.size == 4 && quiz1.answerList.size == 4)
    assert(quiz1.answerList.contains(a4))
  }

  test("Get correct answers"){
    assert(getCorrectAnswers(quiz) == List(a1))

    val a4 = Answer("answer4", true)
    val answerList1 = answerList ::: List(a4)
    val quiz1 = changeAnswerList(quiz, answerList1)

    assert(getCorrectAnswers(quiz1) == List(a1, a4))
  }

end QuizTest
