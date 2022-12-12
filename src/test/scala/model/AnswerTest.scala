package model

import model.Answer.*
import org.scalatest.funsuite.AnyFunSuite
import java.util.UUID

/** Tests for the [[Answer]] object */
class AnswerTest extends AnyFunSuite :

  val a1 = Answer("answer1", true)
  val a2 = Answer("answer2", false)
  val a3 = Answer("answer3", false)

  test("Creation Answer") {
    assert(Answer("answer1", true) == a1)
    assert(Answer("answer2", false) == a2)
    assert(Answer("answer3", false) == a3)
  }

  test("Get text") {
    assert(a1.text == "answer1")
    assert(a2.text == "answer2")
    assert(a3.text == "answer3")
  }

  test("Get isCorrect") {
    assert(a1.isCorrect)
    assert(!a2.isCorrect)
    assert(!a3.isCorrect)
  }

  test("Change text") {
    val newAnswer1 = changeText(a1, "ANSWER")
    assert(newAnswer1.text == "ANSWER")

    val newAnswer2 = changeText(newAnswer1, "New Answer")
    assert(newAnswer2.text == "New Answer")
  }

  test("Change isCorrect") {
    val newAnswer1 = changeCorrect(false)(a1)
    assert(!newAnswer1.isCorrect)

    val newAnswer2 = changeCorrect(true)(a2)
    assert(newAnswer2.isCorrect)

    assert(changeCorrect(true)(Answer("answer", false)).isCorrect)
  }

  test("Make correct/wrong answer") {
    val newAnswer1 = makeCorrect(a1)
    assert(newAnswer1.isCorrect)

    val newAnswer2 = makeWrong(newAnswer1)
    assert(!newAnswer2.isCorrect)
  }

  test("Answer list") {
    val answerList1: List[Answer] = List(a1, a2, a3)

    assert(answerList1.size == 3)

    val a4 = Answer("answer4", true)
    val answerList2 = answerList1 ::: List(a4)

    assert(answerList2.size == 4)

    assert(answerList1.filter(answer => answer.isCorrect) == List(a1))
    assert(answerList2.filter(answer => answer.isCorrect) == List(a1, a4))
  }

end AnswerTest
