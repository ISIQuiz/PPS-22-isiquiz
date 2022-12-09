package model

import model.Answer.Answer

case class QuizAnswered(quizInGame: QuizInGame, answer: Option[Answer])

case class Review(var quizAnsweredList: List[QuizAnswered] = Nil):
  def addQuizAnswered(quizAnswered: QuizAnswered) = quizAnsweredList = quizAnsweredList :+ quizAnswered

  def numberQuizAnswered: Int = quizAnsweredList.length

  def totalCorrectAnswers: Int = quizAnsweredList.count(quizAnswered =>
    if quizAnswered.answer.isDefined then quizAnswered.answer.get.isCorrect else false)

  def totalPoints: Int = quizAnsweredList.foldLeft(0)((acc, quizAnswered) => quizAnswered.answer match
    case Some(ans) if ans.isCorrect => acc+quizAnswered.quizInGame.quiz.maxScore
    case _ => acc
  )