
object Quiz extends App:

  case class Answer(text: String, isCorrect: Boolean)

  def isCorrect(a: Answer): Boolean = a match
    case Answer(_, isCorrect) => isCorrect

  def changeCorrect(c: Boolean)(a: Answer): Answer = a match
    case Answer(text, _) => Answer(text, c)

  def makeCorrect: Answer => Answer = changeCorrect(true)

  def makeWrong: Answer => Answer = changeCorrect(false)

  def getText(a: Answer): String = a match
    case Answer(text, _) => text

  def changeText(a: Answer, c: String): Answer = a match
    case Answer(_, isCorrect) => Answer(c, isCorrect)
