package model

/**
 *  Trait for a saved quiz
 */
trait SavedQuiz extends Quiz:
  /**
   * @return a list of answers
   */
  def answerList: List[Answer]

/**
 *  Object used for managing a saved quiz
 */
object SavedQuiz:

  /**
   * Creates a new [[SavedQuiz]]
   * @param text text of the quiz
   * @param maxScore maximum score when answering a quiz correctly
   * @param imagePath image path (optional)
   * @param answerList list of answers
   * @return SavedQuiz
   */
  def apply(text: String, maxScore: Int, imagePath: Option[String], answerList: List[Answer]): SavedQuiz =
    SavedQuizImpl(text, maxScore, imagePath, answerList)

  /**
  * Case class for saved quiz model
  * @param quizText text of the quiz
  * @param maxScore max score if answered correctly
  * @param imagePath image path (optional)
 */
  case class SavedQuizImpl(override val text: String,
                           override val maxScore: Int,
                           override val imagePath: Option[String],
                           override val answerList: List[Answer]) extends SavedQuiz

  /**
   * Change text of a saved quiz
   * @param savedQuiz the saved quiz to edit
   * @param text a new quiz text
   * @return SavedQuiz
   */
  def changeText(savedQuiz: SavedQuiz, text: String): SavedQuiz = savedQuiz match
    case SavedQuizImpl(_, maxScore, imagePath, answerList) => SavedQuiz(text, maxScore, imagePath, answerList)

  /**
   * Change max score of a saved quiz
   * @param savedQuiz the saved quiz to edit
   * @param maxScore a new quiz max score
   * @return SavedQuiz
   */
  def changeMaxScore(savedQuiz: SavedQuiz, maxScore: Int): SavedQuiz = savedQuiz match
    case SavedQuizImpl(text, _, imagePath, answerList) => SavedQuiz(text, maxScore, imagePath, answerList)

  /**
   * Change image path of a saved quiz
   * @param savedQuiz the saved quiz to edit
   * @param imagePath a new quiz image path (optional)
   * @returna SavedQuiz
   */
  def changeImagePath(savedQuiz: SavedQuiz, imagePath: Option[String]): SavedQuiz = savedQuiz match
    case SavedQuizImpl(text, maxScore, _, answerList) => SavedQuiz(text, maxScore, imagePath, answerList)

  /**
   * Change the list of answers
   * @param savedQuiz the saved quiz to edit
   * @param answerList a new answer list
   * @return SavedQuiz
   */
  def changeAnswerList(savedQuiz: SavedQuiz, answerList: List[Answer]): SavedQuiz = savedQuiz match
    case SavedQuizImpl(text, maxScore, imagePath, _) => SavedQuiz(text, maxScore, imagePath, answerList)
