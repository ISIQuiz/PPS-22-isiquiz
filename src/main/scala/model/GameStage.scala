package model

import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.Quiz.*
import model.Answer.*

case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

object GameStage:

  trait GameStage:
    var quizInGame: QuizInGame
    def courseInGame: List[SavedCourse]

  class GameStageImpl extends GameStage:

    var quizInGame: QuizInGame = QuizInGame(
      courseInGame.head,
      courseInGame.head.quizList.head,
      courseInGame.head.quizList.head.answerList
    )

    override def courseInGame = List(
      SavedCourseImpl(
        courseId = CourseIdentifierImpl(
          courseName = "Corso di test",
          degreeName = "Corso",
          universityName = "ISIQuiz"
        ),
        description = Option("Test course"),
        quizList = List(
          Quiz(
            question = "Domanda 1: 2+2 = ?",
            maxScore = 10,
            imagePath = Option.empty,
            answerList = List(Answer(text = "1", false),Answer(text = "2", false),Answer(text = "4", true),Answer(text = "8", false))
          )
        )
      )
    )