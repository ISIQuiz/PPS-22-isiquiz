package model

/**
 * Object for an answer
 */
object Answer:

  /**
   * An answer has a text and is correct or wrong
   *
   * @param text the text of the answer
   * @param isCorrect an answer is correct or wrong
   */
  case class Answer(text: String, isCorrect: Boolean)

  /**
   * Change answer text
   *
   * @param answer the answer to edit
   * @param answerText a new answer text
   * @return Answer
   */
  def changeText(answer: Answer, answerText: String): Answer = Answer(answerText, answer.isCorrect)

  /**
   * Change whether or not the answer is correct
   *
   * @param isCorrect the new value of correctness
   * @param answer the answer to edit
   * @return Answer
   */
  def changeCorrect(isCorrect: Boolean)(answer: Answer): Answer = Answer(answer.text, isCorrect)

  /**
   * Make an answer correct
   *
   * @return Answer
   */
  def makeCorrect: Answer => Answer = changeCorrect(true)

  /**
   * Make an answer wrong
   *
   * @return Answer
   */
  def makeWrong: Answer => Answer = changeCorrect(false)