package controller

import model.Answer.Answer
import model.Quiz.Quiz
import model.{QuizInGame, SavedCourse}

trait GameController:

  def nextQuiz(): QuizInGame

  def chooseQuiz(course: SavedCourse): Quiz

  def chooseAnswers(quiz: Quiz): List[Answer]

  def endQuiz(): Unit
