package model

import model.CourseIdentifier.CourseIdentifierImpl
import model.SavedCourse.SavedCourseImpl
import model.Quiz.*
import model.Answer.*
import model.settings.{GameSettings, StandardGameSettings}

/** a quiz to play with a sub list of answers and the relative course */
case class QuizInGame(course: Course, quiz: Quiz, answers: List[Answer])

/**
 *
 * @param coursesInGame a list of the curses selected to play in this game, sublist of all courses
 * @param quizInGame    a quiz to play, with a sub list of answers selected and the relative course
 * @param gameSettings  settings like maximum number question, time
 */
class GameStage(var coursesInGame: List[SavedCourse] = List(), var quizInGame: QuizInGame = null, var review: Review = Review(), var gameSettings: GameSettings = StandardGameSettings()):
  def quizInGame_(_quizInGame: QuizInGame) = quizInGame = _quizInGame

  def quizInGamePresent:Boolean = quizInGame.isInstanceOf[QuizInGame]

  def addReviewQuizNotAnswered = review.addQuizAnswered(QuizAnswered(quizInGame, None))

  def addReviewQuizAnswer(answer: Answer) = review.addQuizAnswered(QuizAnswered(quizInGame, Option(answer)))

  def maxQuizzesReached: Boolean = gameSettings match
    case StandardGameSettings(_, maxQuizzes, _) => println(review.numberQuizAnswered ); review.numberQuizAnswered >= maxQuizzes
    case _ => false
