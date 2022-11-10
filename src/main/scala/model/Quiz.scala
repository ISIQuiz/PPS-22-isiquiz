package model

import model.Answer.Answer
import model.Quiz.AnswerList
import model.Quiz.AnswerList.{Cons, Empty}

/**
 * Module quiz contains all the definitions of a single question and answers
 * and the functions to operate with it
 */
object Quiz:

  enum AnswerList:
    private case Cons(head: Answer, tail: AnswerList)
    private case Empty()

  object AnswerList:

    def cons(h: Answer, t: AnswerList): AnswerList = Cons(h, t)

    def empty(): AnswerList = Empty()

    def countAnswers(answerList: AnswerList): Int = answerList match
      case Cons(_, tail) => 1 + countAnswers(tail)
      case Empty() => 0

    def countAnswersTR(answerList: AnswerList): Int =
      @annotation.tailrec
      def countAnswersTRCounter(counter: Int)(answerList: AnswerList): Int = answerList match
        case Cons(_, tail) => countAnswersTRCounter(counter + 1)(tail)
        case Empty() => counter
      countAnswersTRCounter(0)(answerList)

    import model.Answer.isCorrect
    def getCorrect(answerList: AnswerList): AnswerList = answerList match
      case Cons(h, tail) if isCorrect(h) => Cons(h, getCorrect(tail))
      case Cons(h, tail) => getCorrect(tail)
      case Empty() => Empty()

    def getCorrectIndex(answerList: AnswerList): Int =
      @annotation.tailrec
      def getCorrectIndexCounter(counter: Int)(answerList: AnswerList): Int = answerList match
        case Cons(h, _) if isCorrect(h) => counter
        case Cons(_, tail) => getCorrectIndexCounter(counter + 1)(tail)
        case Empty() => -1
      getCorrectIndexCounter(0)(answerList)

    def addAnswer(answerList: AnswerList)(answer: Answer): AnswerList = Cons(answer, answerList)

    def printAnswers(answerList: AnswerList, wSolutions: Boolean = false): String =
      def _printAnswers(wSolutions: Boolean = wSolutions)(statingCounter: Int)(answerList: AnswerList): String = answerList match
        case Cons(ans, t) => s"\t$statingCounter)" + (if (wSolutions) ans.isCorrect else "") + " " + ans.text + "\n" + _printAnswers(wSolutions)(statingCounter + 1)(t)
        case _ => ""

      _printAnswers(wSolutions)(1)(answerList)


  /**
   *  A quiz with quesiton and answers
   * @param question
   * @param answerList
   * @param maxScore
   * @param imagePath
   */
  case class Quiz(question: String, answerList: AnswerList, maxScore:Int, imagePath:Option[String]=None) {
    // Overriding toString method to print directly requires parenthesis
    override def toString(): String = printQuiz(this)
  }

  def getCorrectAnswers(quiz: Quiz): AnswerList = AnswerList.getCorrect(quiz.answerList)

  import model.Quiz.AnswerList.printAnswers
  val printQuiz: Quiz => String = quiz => quiz.question + "\n" + printAnswers(quiz.answerList)
  val printQuizFull: Quiz => String = quiz => quiz.question + "\n" + printAnswers(quiz.answerList, true)

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
  def changeAnswerList(quiz: Quiz, answerList: AnswerList): Quiz = Quiz(quiz.question, answerList, quiz.maxScore, quiz.imagePath)
