import Quiz.*
import org.scalatest.funsuite.AnyFunSuite

class QuizTest extends AnyFunSuite:


  test("Creation Answer") {
    Answer("risposta", true)
    Answer("risposta", false)
    assert(true)
  }

  test("Get/Change Text Answer") {
    val a = Answer("risposta", true)
    assert(getText(a) == "risposta")
    val b = changeText(a, "nuovarisposta")
    assert(getText(b) == "nuovarisposta")
  }

  test("isCorrect Answer") {
    val a = Answer("risposta", true)
    assert(isCorrect(a))
    val b = Answer("risposta", false)
    assert(!isCorrect(b))
  }

  test("Change Right Answer") {
    val a = Answer("risposta", true)
    assert(isCorrect(a))
    val b = changeCorrect(false)(a)
    assert(!isCorrect(b))
    assert(isCorrect(changeCorrect(true)(Answer("risposta", false))))

    val c = makeCorrect(a)
    assert(isCorrect(c))
    val d = makeWrong(b)
    assert(!isCorrect(d))
  }

  test("AnswerList") {
    val a = Answer("risposta", true)
    val a2 = Answer("risposta", false)
    val a3 = Answer("risposta", false)

    import AnswerList.*
    val answerList: AnswerList = cons(a , cons(a2 , cons(a3 , empty() )))


    assert(countAnswers(answerList) == 3)
    assert(countAnswersTR(answerList) == 3)

    val a4 = Answer("risposta", true)
    val answerList2 = addAnswer(answerList)(a4)

  }

end QuizTest
