package model

/* TODO: need to be completed */

/**
 * Case class for saved quiz
 * @param quizText text of the quiz
 * @param maxScore max score if answered correctly
 * @param imagePath image path
 */
case class SavedQuiz(quizText: String, maxScore: Int, imagePath: String) extends Quiz:
  /*def answer: List[Answer]*/