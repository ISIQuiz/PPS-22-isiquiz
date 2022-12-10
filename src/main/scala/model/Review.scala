package model

import model.Answer.Answer
import model.stats.PlayerStats.PlayerStats

/** A quiz answered is a quiz in game, accompanied with an optional answer that the player gave */
case class QuizAnswered(quizInGame: QuizInGame, answerPlayer: Option[Answer], var score: Int = 0, var timeToAnswer: Double = 0)

/** A review is list of quiz answered by the player during a game */
case class Review(var quizAnsweredList: List[QuizAnswered] = Nil):
  def addQuizAnswered(quizAnswered: QuizAnswered): Unit = quizAnsweredList = quizAnsweredList :+ quizAnswered

  def numberQuizAnswered: Int = quizAnsweredList.length
/*
  def totalCorrectAnswers: Int = quizAnsweredList.count(quizAnswered =>
    if quizAnswered.answerPlayer.isDefined then quizAnswered.answerPlayer.get.isCorrect else false)

  def totalPoints: Int = quizAnsweredList.foldLeft(0)((acc, quizAnswered) => quizAnswered.answerPlayer match
    case Some(ans) if ans.isCorrect => acc + quizAnswered.quizInGame.quiz.maxScore
    case _ => acc
  )*/