package modelTest

import model.{SavedCourse, SavedQuiz}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class SavedCourseTest extends AnyFunSuite with Matchers:

  val q1 = SavedQuiz("quiz1", 12, "image1.png")
  val q2 = SavedQuiz("quiz2", 13, "image2.png")
  val q3 = SavedQuiz("quiz3", 8, "image3.png")

  /* Simple list of SavedQuiz */
  val quizList = List(q1, q2, q3)

  /* Simple saved course */
  val savedCourse: SavedCourse = SavedCourse("Paradigmi di Programmazione e Sviluppo", "Laurea Magistrale in Ingegneria e Scienze Informatiche", "University of Bologna", "Descrizione", quizList)

  test("The name should be equal to Paradigmi di Programmazione e Sviluppo") {
    savedCourse.courseName shouldEqual "Paradigmi di Programmazione e Sviluppo"
  }

  test("The course degree name should be equal to Laurea Magistrale in Ingegneria e Scienze Informatiche") {
    savedCourse.degreeName shouldEqual "Laurea Magistrale in Ingegneria e Scienze Informatiche"
  }

  test("The university name should be equal to University of Bologna") {
    savedCourse.universityName shouldEqual "University of Bologna"
  }

  test("The quiz list should be equal to the starting list") {
    savedCourse.quizList shouldEqual quizList
  }







