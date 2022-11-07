package model

import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.Quiz.*
import model.Answer.Answer
import AnswerList.*


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
          Quiz(
            question = "Domanda 1: 2+2 = ?",
            maxScore = 10,
            imagePath = Option.empty,
            answerList =
              cons(Answer(text = "1", false),
              cons(Answer(text = "2", false), empty())

            )
          )
        )
      )
    )