package modelTest

import model.SavedQuiz
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class SavedQuizTest extends AnyFunSuite with Matchers:

  /* Simple quiz */
  val savedQuiz:SavedQuiz = SavedQuiz("quiz1", 12, "image1.png")

  test("The text should be equal to quiz1") {
    savedQuiz.quizText shouldEqual "quiz1"
  }

  test("The quiz max score point should be equal to 12") {
    savedQuiz.maxScore shouldEqual 12
  }

  test("The quiz image path should be equal to image1.png") {
    savedQuiz.imagePath shouldEqual "image1.png"
  }







