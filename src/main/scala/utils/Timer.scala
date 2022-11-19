package utils

import CancellableFuture.*
import controller.AppController
import controller.StandardGameController.TimeExpired

import java.util.concurrent.{CountDownLatch, TimeUnit}
import scala.concurrent.{Future, Promise}
import concurrent.ExecutionContext.Implicits.global

trait Timer[T]:
//  var cancellableFuture: (Future[T], () => Boolean)
  var cancellable: (Future[T], () => Boolean)
  def startTimer(): Unit
  def stopTimer(): Unit

case class TimerImpl[T](var time: Long) extends Timer[T]:

  var cancellable: (Future[T], () => Boolean) = _

  override def startTimer(): Unit =
    cancellable = interruptableFuture[T] { () =>
      val latch = new CountDownLatch(1)
      latch.await(time, TimeUnit.SECONDS)
      AppController.currentPage.pageController.handle(TimeExpired).asInstanceOf[T]
    }

    cancellable._1.onComplete( { case _ => println("timer completed") } )

  override def stopTimer(): Unit =
    cancellable._2.apply()




