package model

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class SavedQuizTest extends AnyFunSuite with Matchers:

  val a1: List[Answer] = List()

  /* Simple quiz */
  val savedQuiz:SavedQuiz = SavedQuiz("quiz1", 12, Some("image1.png"), a1)

  test("The text should be equal to quiz1") {
    savedQuiz.text shouldEqual "quiz1"
  }

  test("The quiz max score point should be equal to 12") {
    savedQuiz.maxScore shouldEqual 12
  }

  test("The quiz image path should be equal to image1.png") {
    savedQuiz.imagePath shouldEqual Some("image1.png")
  }

  test("Testing change quiz text") {
    val savedQuizChanged = SavedQuiz.changeText(savedQuiz, "Domanda1")

    savedQuizChanged.text shouldEqual "Domanda1"
  }

  test("Testing change quiz max score") {
    val savedQuizChanged = SavedQuiz.changeMaxScore(savedQuiz, 22)

    savedQuizChanged.maxScore shouldEqual 22
  }

  test("Testing change quiz image path") {
    val savedQuizChanged = SavedQuiz.changeImagePath(savedQuiz, Some("image3.jpg"))

    savedQuizChanged.imagePath shouldEqual Some("image3.jpg")
  }


  // TODO
  /*test("Testing change quiz answer list") {
    val savedQuizChanged = SavedQuiz.changeAnswerList(savedQuiz, List[Answer]())
  }*/











