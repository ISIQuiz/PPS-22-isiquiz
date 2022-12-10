package model

import model.Answer.*
import model.Quiz.*

import java.util.UUID

/**
 * Object for a quiz
 */
object Quiz:

  /**
   *  A quiz has an identifier, a question, some answers,
   *  a max score and optionally an image
   *
   * @param quizId a random UUID for the quiz
   * @param question the question of the quiz
   * @param answerList a list of answers
   * @param maxScore a max score
   * @param imagePath an image path (optional)
   */
  case class Quiz(quizId: UUID = UUID.randomUUID(), question: String, answerList: List[Answer], maxScore:Int, imagePath:Option[String]=None) {
    override def toString(): String = printQuiz(this)
  }

  val printQuiz: Quiz => String = quiz => quiz.question + "\n" + quiz.answerList.map(answer => s"${quiz.answerList.indexOf(answer)+1}) ${answer.text}")
  val printQuizFull: Quiz => String = quiz => quiz.question + "\n" +
    quiz.answerList.map(answer => s"${quiz.answerList.indexOf(answer)+1}) ${answer.text} ${answer.isCorrect}")

  /**
   * Change max score of a quiz
   *
   * @param quiz the saved quiz to edit
   * @param maxScore a new quiz max score
   * @return Quiz
   */
  def changeMaxScore(quiz: Quiz, maxScore: Int): Quiz = Quiz(quiz.quizId, quiz.question, quiz.answerList, maxScore, quiz.imagePath)

  /**
   * Change image path of a quiz
   *
   * @param quiz the quiz to edit
   * @param imagePath a new quiz image path (optional)
   * @return Quiz
   */
  def changeImagePath(quiz: Quiz, imagePath: Option[String]): Quiz = Quiz(quiz.quizId, quiz.question, quiz.answerList, quiz.maxScore, imagePath)

  /**
   * Change question of a quiz
   *
   * @param quiz the quiz to edit
   * @param questionText a new quiz text question
   * @return Quiz
   */
  def changeQuestion(quiz: Quiz, questionText: String): Quiz = Quiz(quiz.quizId, questionText, quiz.answerList, quiz.maxScore, quiz.imagePath)

  /**
   * Change the list of answers
   *
   * @param quiz the quiz to edit
   * @param answerList a new answer list
   * @return Quiz
   */
  def changeAnswerList(quiz: Quiz, answerList: List[Answer]): Quiz = Quiz(quiz.quizId, quiz.question, answerList, quiz.maxScore, quiz.imagePath)

  /**
   * Return the list of the correct answers of the quiz
   *
   * @param quiz the quiz
   * @return List[Answer]
   */
  def getCorrectAnswers(quiz: Quiz): List[Answer] = quiz.answerList.filter(answer => answer.isCorrect)