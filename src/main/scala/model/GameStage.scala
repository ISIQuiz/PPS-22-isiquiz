package model

import model.CourseIdentifier.CourseIdentifier
import model.SavedCourse.SavedCourse
import model.Quiz.*
import model.Quiz
import model.Answer.*
import model.settings.{GameSettings, StandardGameSettings}
import model.stats.PlayerStats.{PlayerStats, addQuizInGameToStats, initStats}

/**
 * Case class for a quiz used during the game
 *
 * @param course  the course of the quiz
 * @param quiz    a quiz used during the game
 * @param answers a list of answers of the quiz
 */
case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

/**
 * Game Stage with the list of the full-courses selected, a QuizInGame current, a Review of the quizzes played, and some settings
 *
 * @param coursesInGame     a list of the curses selected to play in this game, sublist of all courses
 * @param quizInGame        a quiz to play, with a sub list of answers selected and the relative course
 * @param review            a list of quizzes played
 * @param gameSettings      settings like maximum number question, time
 * @param playerStatsInGame the stats of the game currently playing
 */
class GameStage(var coursesInGame: List[SavedCourse] = List(), var quizInGame: QuizInGame = null, var review: Review = Review(), var gameSettings: GameSettings = StandardGameSettings(), var playerStatsInGame: PlayerStats = initStats):

  /**
   * Set the quizInGame value
   *
   * @param _quizInGame the saved course to edit
   */
  def quizInGame_(_quizInGame: QuizInGame) = quizInGame = _quizInGame

  def quizInGamePresent: Boolean = quizInGame.isInstanceOf[QuizInGame]

  def addReviewQuizNotAnswered() = review.addQuizAnswered(QuizAnswered(quizInGame, None))

  def addReviewQuizAnswer(answer: Answer, score: Int, timeToAnswer: Double) =
    if (answer.isCorrect)
      review.addQuizAnswered(QuizAnswered(quizInGame, Option(answer), score, timeToAnswer))
    else
      review.addQuizAnswered(QuizAnswered(quizInGame, Option(answer)))

  //Add a quiz in game to stats
  def addQuizToStats(isCorrect: Boolean, score: Int, timeToAnswer: Double): Unit =
    playerStatsInGame = addQuizInGameToStats(playerStatsInGame, quizInGame, isCorrect, score, timeToAnswer)

  def currentQuizNumber: Int = review.numberQuizAnswered + 1

  def maxQuizzesReached: Boolean = gameSettings match
    case StandardGameSettings(_, maxQuizzes) => review.numberQuizAnswered >= maxQuizzes
    case _ => false
