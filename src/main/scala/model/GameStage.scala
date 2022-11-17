package model

import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.Quiz.*
import model.Answer.*

case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

case class GameStage(var coursesInGame: List[SavedCourse] = List(), var quizInGame: QuizInGame = null)