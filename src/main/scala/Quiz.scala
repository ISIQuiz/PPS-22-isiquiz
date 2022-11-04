import Quiz.Quiz.printQuiz

import scala.annotation.tailrec

/**
 * Module quiz contains all the definitions of a single question and answers
 * and the functions to operate with it
 */
object Quiz:

  case class Answer(text: String, isCorrect: Boolean)

  def getText(a: Answer): String = a match
    case Answer(text, _) => text

  def changeText(a: Answer, c: String): Answer = a match
    case Answer(_, isCorrect) => Answer(c, isCorrect)

  def isCorrect(a: Answer): Boolean = a match
    case Answer(_, isCorrect) => isCorrect

  def changeCorrect(c: Boolean)(a: Answer): Answer = a match
    case Answer(text, _) => Answer(text, c)

  def makeCorrect: Answer => Answer = changeCorrect(true)

  def makeWrong: Answer => Answer = changeCorrect(false)


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

    def getCorrect(answerList: AnswerList): AnswerList = answerList match
      case Cons(h, tail) if isCorrect(h) => Cons(h, getCorrect(tail))
      case Cons(h, tail) => getCorrect(tail)
      case Empty() => Empty()

    def addAnswer(answerList: AnswerList)(answer: Answer): AnswerList = Cons(answer, answerList)

    def printAnswers(answerList: AnswerList, wSolutions: Boolean = false): String =
      def _printAnswers(wSolutions: Boolean = wSolutions)(statingCounter: Int)(answerList: AnswerList): String = answerList match
        case Cons(ans, t) => s"\t$statingCounter)" + (if (wSolutions) ans.isCorrect else "") + " " + ans.text + "\n" + _printAnswers(wSolutions)(statingCounter + 1)(t)
        case _ => ""

      _printAnswers(wSolutions)(1)(answerList)

  case class Quiz(question: String, answerList: AnswerList) {
    // Overriding toString method to print directly
    override def toString(): String = printQuiz(this)

  }

  object Quiz:

    import AnswerList.*

    def getCorrectAnswers(quiz: Quiz): AnswerList = AnswerList.getCorrect(quiz.answerList)

    val printQuiz: Quiz => String = quiz => quiz.question + "\n" + printAnswers(quiz.answerList)
    val printQuizFull: Quiz => String = quiz => quiz.question + "\n" + printAnswers(quiz.answerList, true)

