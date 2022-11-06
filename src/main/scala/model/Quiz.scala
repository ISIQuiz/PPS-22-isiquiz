package model

/**
 *  Trait for a quiz
 */
trait Quiz:
  /**
   * @return text of the quiz
   */
  def text: String

  /**
   * @return max score 
   */
  def maxScore: Int
  def imagePath: Option[String]