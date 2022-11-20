package utils

import CancellableFuture.*
import controller.AppController
import controller.StandardGameController.TimeExpired

import java.util.concurrent.{CountDownLatch, TimeUnit}
import scala.concurrent.{Future, Promise}
import concurrent.ExecutionContext.Implicits.global

trait Timer:

  var cancellable: (Future[Unit], () => Boolean)
  def startTimer(): Unit
  def stopTimer(): Unit

case class TimerImpl(var time: Long) extends Timer:

  var cancellable: (Future[Unit], () => Boolean) = _

  override def startTimer(): Unit =
    cancellable = interruptableFuture[Unit] { () =>
      val latch = new CountDownLatch(1)
      latch.await(time, TimeUnit.SECONDS)
      AppController.currentPage.pageController.handle(TimeExpired)
    }

    cancellable._1.onComplete( { case _ => println("timer completed") } )

  override def stopTimer(): Unit =
    cancellable._2.apply()




