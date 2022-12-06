package utils


import controller.AppController
import model.Answer.Answer
import model.Quiz.Quiz
import model.{CourseIdentifier, SavedCourse, Session}
import org.scalatest.funsuite.AnyFunSuite
import utils.Configuration.{HomeDirectoryPath, PlayerCoursesFilePath}

import java.io.FileNotFoundException
import java.util.UUID
import scala.util.{Failure, Success}

class StorageHandlerTest extends AnyFunSuite:

  val session: Session = Session()

  // Export
  test("Test if can export session file in player directory") {
    StorageHandler.exportSessionToPersonalDirectory(session) match
      case Success(s) => succeed
      case Failure(f) => fail()
  }

  test("Test if can export saved courses in chosen path") {
    StorageHandler.exportSavedCoursesToPath(session, HomeDirectoryPath) match
      case Success(s) => succeed
      case Failure(f) => fail()
  }

  // Import
  test("Test if can import session file from player directory") {
    StorageHandler.importSessionFromPersonalDirectory(session) match
      case Success(s) => succeed
      case Failure(f) => fail()
  }

  test("Test if can import saved courses from chosen path") {
    println(PlayerCoursesFilePath)
    StorageHandler.importSavedCourseListFromFile(session, HomeDirectoryPath) match
      case Success(scl) => succeed
      case Failure(f) => Failure(f)
  }

  // Merge
  test("Test if merge two list of SavedCourse is done correctly") {

    // Answers list
    val al1 = List(Answer("dddd", true), Answer("eeee", false), Answer("equal", false))
    val al2 = List(Answer("equal", true), Answer("aaaa", false))
    val al3 = List(Answer("gggg", false), Answer("bbbb", true))

    // Quiz list
    val ql1 = List(Quiz(quizId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), question = "quiz1", answerList = al1, maxScore = 7))

    val ql2 = List(
      Quiz(quizId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), question = "quiz1", answerList = al2, maxScore = 12),
      Quiz(quizId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), question = "quiz2", answerList = al3, maxScore = 10)
    )

    // Course Identifier
    val ci = CourseIdentifier("corso", "degree", "university")

    // Saved course list
    val scl1 = List(SavedCourse(ci, Option("descrizione1"), ql1))
    val scl2 = List(SavedCourse(ci, Option("descrizione2"), ql2))

    // Expected answer list after merge
    val alJoined = List(Answer("equal", false),Answer("aaaa", false), Answer("dddd", true), Answer("eeee", false))

    // Expected SavedCourse List after merge
    val sclSolutionWanted = List(
      SavedCourse(
        ci,
        Option("descrizione1"),
        List(
          Quiz(quizId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), question = "quiz1", answerList = alJoined, maxScore = 7),
          Quiz(quizId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), question = "quiz2", answerList = al3, maxScore = 10)
        )
      )
    )

    // Merge of saved course lists
    val sclJoined = StorageHandler.mergeSavedCourseLists(scl1, scl2)

    // Check
    assert(sclSolutionWanted.equals(sclJoined))
  }

