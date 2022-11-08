package model

import org.scalatest.funsuite.AnyFunSuite
import model.Answer.*
import model.Quiz.AnswerList

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
    import model.Quiz.AnswerList.*
    val answerList: AnswerList = cons(a1, cons(a2, cons(a3, empty())))


    assert(countAnswers(answerList) == 3)
    assert(countAnswersTR(answerList) == 3)

    val a4 = Answer("answer", true)
    val answerList2 = addAnswer(answerList)(a4)

    assert(countAnswers(answerList2) == 4)
    assert(countAnswersTR(answerList2) == 4)

    assert(getCorrect(answerList) == cons(a1, empty()))
    assert(getCorrect(answerList2) == cons(a4, cons(a1, empty())))

    println(printAnswers(answerList2))
  }


  test("Quiz") {
    import AnswerList.*
    import Quiz.*
    val answerList: AnswerList = cons(a1, cons(a2, cons(a3, empty())))

    val quiz: Quiz = Quiz("question", answerList, 10)
    assert(getCorrectAnswers(quiz) == cons(a1, empty()))

    println(quiz)
    println(printQuiz(quiz))
    println(printQuizFull(quiz))

  }

end QuizTest
