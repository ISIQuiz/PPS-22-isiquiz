package model

import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.Quiz.*
import model.Answer.*
import model.settings.{GameSettings, StandardGameSettings}

case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

class GameStage(var coursesInGame: List[SavedCourse] = List(), var quizInGame: QuizInGame = null, var gameSettings: GameSettings = StandardGameSettings())
