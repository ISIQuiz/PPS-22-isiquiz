package model

import model.Answer.AnswerImpl
import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.SavedQuiz.SavedQuizImpl

object GameStage:

  trait GameStage:
    def courseInGame: List[SavedCourse]

  class GameStageImpl extends GameStage:
    override def courseInGame = List(
      SavedCourseImpl(
        courseId = CourseIdentifierImpl(
          courseName = "Corso di test",
          degreeName = "Corso",
          universityName = "ISIQuiz"
        ),
        description = Option("Test course"),
        quizList = List(
          SavedQuizImpl(
            text = "Domanda 1: 2+2 = ?",
            maxScore = 10,
            imagePath = Option.empty,
            answerList = List(
              AnswerImpl(text = "1", isCorrect = false),
              AnswerImpl(text = "2", isCorrect = false),
              AnswerImpl(text = "4", isCorrect = true),
              AnswerImpl(text = "6", isCorrect = false)
            )
          )
        )
      )
    )