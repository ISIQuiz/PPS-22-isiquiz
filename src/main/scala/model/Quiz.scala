package model

/**
 *  Trait for a quiz
 */
trait Quiz:
  def quizText: String
  def maxScore: Int
  def imagePath: String