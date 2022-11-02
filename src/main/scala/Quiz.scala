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

    def cons(h:Answer, t:AnswerList): AnswerList = Cons(h,t)

    def empty():AnswerList = Empty()

    def countAnswers(answerList: AnswerList): Int = answerList match
      case Cons(_,tail) => 1 + countAnswers(tail)
      case Empty() => 0

    def countAnswersTR(answerList: AnswerList):Int =
      @annotation.tailrec
      def countAnswersTRCounter(counter:Int)(answerList: AnswerList): Int = answerList match
        case Cons(_, tail) => countAnswersTRCounter(counter+1)(tail)
        case Empty() => counter

      countAnswersTRCounter(0)(answerList)

    def addAnswer(answerList: AnswerList)(answer: Answer): AnswerList = Cons(answer, answerList)

  case class Quiz(question: String, answerList: AnswerList)

  def getCorrect(quiz: Quiz): AnswerList = ???

