package model

/**
 *  Trait for an answer
 */
trait Answer:
  /**
   * @return text of the answer
   */
  def text: String

  /**
   * @return correctness of the answer
   */
  def isCorrect: Boolean

/**
 *  Object used for managing an answer
 */
object Answer:

  /**
   * Creates a new [[Answer]]
   * @param text text of the answer
   * @param isCorrect whether the question is to be considered right or wrong
   * @return Answer
   */
  def apply(text: String, isCorrect: Boolean): AnswerImpl =
    AnswerImpl(text, isCorrect)

  /**
   * Case class for answer model
   * @param text text of the answer
   * @param isCorrect whether the question is to be considered right or wrong
   */
  case class AnswerImpl(val text: String, val isCorrect: Boolean) extends Answer

