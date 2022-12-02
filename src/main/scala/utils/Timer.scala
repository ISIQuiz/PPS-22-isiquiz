package utils

trait Timer:

  def startTimer(): Unit
  def getTime(): Double
  def getCompletionPercentage(): Double
  def isExpired(): Boolean

case class TimerImpl(var maxTime: Long) extends Timer:

  var initialTime: Long = _

  def currentTime(): Long = System.currentTimeMillis()

  override def startTimer(): Unit = initialTime = currentTime()

  override def getTime(): Double = (currentTime() - initialTime) / 1000

  override def getCompletionPercentage(): Double = (currentTime() - initialTime) / maxTime match
      case n if n < 100 && n >= 0 => n
      case n if n >= 100 => 100
      case _ => throw new IllegalArgumentException()

  override def isExpired(): Boolean = currentTime() - initialTime match
    case n if n >= maxTime => true
    case n if n < maxTime => false
    case _ => throw new IllegalArgumentException()


