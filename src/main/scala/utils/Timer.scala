package utils
/** a timer tracks the amount of time passed since the start,
 * can also be a countdown when maxTime is set, returning the remaining time and if is expired
 * attention: stopping the timer doesn't interfere with its expiration
 */
trait Timer:

  val maxTime: Long

  def startTimer(): Unit
  def stopTimer(): Unit
  def getTime: Double
  def getRemainingTime: Double
  def getCompletionPercentage: Double
  def isExpired: Boolean
  def isStopped: Boolean

object Timer:

  def apply(maxTime: Long): Timer = new TimerImpl(maxTime * 1000)

  class TimerImpl(val maxTime: Long) extends Timer:

    var initialTime: Long = _

    var stopTime: Long = _

    def currentTime(): Long = System.currentTimeMillis()

    override def startTimer(): Unit =
      stopTime = 0
      initialTime = currentTime()

    override def stopTimer(): Unit = stopTime = currentTime()

    override def getTime: Double = millisToSeconds(currentTime() - initialTime)

    override def getRemainingTime: Double = millisToSeconds(maxTime) - getTime

    override def getCompletionPercentage: Double =
      val percentage: Double = (currentTime() - initialTime) / maxTime.toDouble
      percentage match
        case n if n < 100 && n >= 0 => n
        case n if n >= 100 => 100
        case _ => throw new IllegalArgumentException()

    override def isExpired: Boolean = getRemainingTime < 0

    override def isStopped: Boolean = stopTime != 0

    override def toString: String = s"Current timer: ${this.getTime} / ${this.maxTime/1000}"

    def millisToSeconds(time: Double) = time / 1000


