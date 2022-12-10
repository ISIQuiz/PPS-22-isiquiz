package model

import model.CourseIdentifier.CourseIdentifier
import model.SavedCourse.SavedCourse
import model.Quiz.*
import model.Answer.*
import model.settings.{GameSettings, StandardGameSettings}

/**
 * Case class for a quiz used during the game
 *
 * @param course the course of the quiz
 * @param quiz a quiz used during the game
 * @param answers a list of answers of the quiz
 */
case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

/**
 * Class for the game stage
 *
 * @param coursesInGame the courses selected by the user
 * @param quizInGame the quiz the user is currently taking
 * @param gameSettings the game mode settings
 */
class GameStage(var coursesInGame: List[SavedCourse] = List(), var quizInGame: QuizInGame = null, var gameSettings: GameSettings = StandardGameSettings()):

  /**
   * Set the quizInGame value
   * @param _quizInGame the saved course to edit
   */
  def quizInGame_(_quizInGame: QuizInGame) = quizInGame = _quizInGame
