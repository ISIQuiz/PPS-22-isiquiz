package model

import model.Answer.*
import model.Quiz.*

/**
 * Module quiz contains all the definitions of a single question and answers
 * and the functions to operate with it
 */
object Quiz:
  
  /**
   *  A quiz with quesiton and answers
   * @param question
   * @param answerList
   * @param maxScore
   * @param imagePath
   */
  case class Quiz(question: String, answerList: List[Answer], maxScore:Int, imagePath:Option[String]=None) {
    // Overriding toString method to print directly requires parenthesis
    override def toString(): String = printQuiz(this)
  }

  def getCorrectAnswers(quiz: Quiz): List[Answer] = quiz.answerList.filter(answer => isCorrect(answer))

  val printQuiz: Quiz => String = quiz => quiz.question + "\n" + quiz.answerList.map(answer => s"${quiz.answerList.indexOf(answer)+1}) ${getText(answer)}")
  val printQuizFull: Quiz => String = quiz => quiz.question + "\n" +
    quiz.answerList.map(answer => s"${quiz.answerList.indexOf(answer)+1}) ${getText(answer)} ${isCorrect(answer)}")

  /**
   * Change max score of a quiz
   *
   * @param Quiz the saved quiz to edit
   * @param maxScore  a new quiz max score
   * @return Quiz
   */
  def changeMaxScore(quiz: Quiz, maxScore: Int): Quiz = Quiz(quiz.question, quiz.answerList, maxScore, quiz.imagePath)

  /**
   * Change image path of a quiz
   *
   * @param Quiz the quiz to edit
   * @param imagePath a new quiz image path (optional)
   * @returna Quiz
   */
  def changeImagePath(quiz: Quiz, imagePath: Option[String]): Quiz = Quiz(quiz.question, quiz.answerList, quiz.maxScore, imagePath)

  /**
   *  Gets the question of a quiz
   * @param quiz
   * @return
   */
  def getQuestion(quiz:Quiz):String = quiz match
    case Quiz(q,_,_,_) => q

  /**
   * Change question of a quiz
   *
   * @param Quiz the quiz to edit
   * @param text a new quiz text question
   * @return Quiz
   */
  def changeText(quiz: Quiz, text: String): Quiz = Quiz(text, quiz.answerList, quiz.maxScore, quiz.imagePath)

  /**
   * Change the list of answers
   *
   * @param quiz  the quiz to edit
   * @param answerList a new answer list
   * @return Quiz
   */
  def changeAnswerList(quiz: Quiz, answerList: List[Answer]): Quiz = Quiz(quiz.question, answerList, quiz.maxScore, quiz.imagePath)