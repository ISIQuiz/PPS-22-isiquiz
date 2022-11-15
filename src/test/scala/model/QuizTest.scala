package model

import org.scalatest.funsuite.AnyFunSuite
import model.Answer.*
class QuizTest extends AnyFunSuite :

  val a1 = Answer("answer", true)
  val a2 = Answer("answer", false)
  val a3 = Answer("answer", false)

  test("Creation Answer") {
    Answer("answer", true)
    Answer("answer", false)
    assert(true)
  }

  test("Get/Change Text Answer") {
    assert(getText(a1) == "answer")
    val b = changeText(a1, "new-answer")
    assert(getText(b) == "new-answer")
  }

  test("isCorrect Answer") {
    assert(isCorrect(a1))
    val b = Answer("answer", false)
    assert(!isCorrect(b))
  }

  test("Change Right Answer") {
    assert(isCorrect(a1))
    val b = changeCorrect(false)(a1)
    assert(!isCorrect(b))
    assert(isCorrect(changeCorrect(true)(Answer("answer", false))))

    val corrAns = makeCorrect(a1)
    assert(isCorrect(corrAns))
    val d = makeWrong(corrAns)
    assert(!isCorrect(d))
  }

  test("AnswerList") {
    import model.Quiz.*
    val answerList: List[Answer] = List(a1, a2, a3)

    assert(answerList.size == 3)

    val a4 = Answer("answer", true)
    val answerList2 = answerList ::: List(a4)

    assert(answerList2.size == 4)

    assert(answerList.filter(answer => answer.isCorrect) == List(a1))
    assert(answerList2.filter(answer => answer.isCorrect) == List(a4, a1))

    println(answerList2.toString())
  }

  test("Quiz") {
    import Quiz.*
    val answerList: List[Answer] = List(a1, a2, a3)

    val quiz: Quiz = Quiz("question", answerList, 10)
    assert(getCorrectAnswers(quiz) == List(a1))

    println(quiz)
    println(printQuiz(quiz))
    println(printQuizFull(quiz))
    println(getAllAnswers(quiz).toString)
  }

end QuizTest
