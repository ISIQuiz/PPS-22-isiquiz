import Quiz.{Answer, changeCorrect, changeText, getText, isCorrect, makeCorrect, makeWrong}
import org.scalatest.funsuite.AnyFunSuite

class QuizTest extends AnyFunSuite:


  test("Creation Answer") {
    Answer("risposta", true)
    Answer("risposta", false)
    assert(true)
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

  test("Change Text Answer") {
    val a = Answer("risposta", true)
    assert(getText(a)== "risposta")
    val b = changeText(a, "nuovarisposta")
    assert(getText(b) == "nuovarisposta")
  }


end QuizTest
