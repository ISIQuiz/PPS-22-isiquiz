package model
/* TODO: need to be completed */

/** Case class for [[Course]] model
 * @param courseName the name of the course (E.g. Paradigmi di Programmazione e Sviluppo)
 * @param degreeName the name of the degree course (E.g. Laurea Magistrale in Ingegneria e Scienze Informatiche)
 * @param universityName the name of the university (E.g. University of Bologna)
 * @param description description of the course
 * @param quizList quiz list of saved quiz
 */
case class SavedCourse(courseName: String, degreeName: String, universityName: String, description: String, quizList: List[SavedQuiz]) extends Course
  /*def changeCourseName
  def changeDegreeName
  def changeUniversityName
  def addQuizToList*/

