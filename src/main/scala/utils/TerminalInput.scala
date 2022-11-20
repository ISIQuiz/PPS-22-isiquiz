package utils

import CancellableFuture.*
import controller.AppController
import controller.StandardGameController.{SelectAnswer, TimeExpired}

import java.util.concurrent.{CountDownLatch, TimeUnit}
import scala.concurrent.{Future, Promise}
import concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine

trait TerminalInput[T]:

  var cancellable: (Future[T], () => Boolean)
  def readInput(): Unit
  def stopInput(): Unit

case class TerminalInputImpl[T]() extends TerminalInput[T]:

  var cancellable: (Future[T], () => Boolean) = _

  override def readInput(): Unit =
    cancellable = interruptableFuture[T] { () =>
      AppController.currentPage.pageController.handle(SelectAnswer(Option(readLine.toInt))).asInstanceOf[T]
    }

    cancellable._1.onComplete( { case _ => println("read completed") } )

  override def stopInput(): Unit =
    cancellable._2.apply()




