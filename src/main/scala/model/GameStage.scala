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

  class GameStageImpl(selectedCourses: List[SavedCourse]) extends GameStage:

    var quizInGame: QuizInGame = QuizInGame(
      courseInGame.head,
      courseInGame.head.quizList.head,
      courseInGame.head.quizList.head.answerList
    )

    override def courseInGame = selectedCourses