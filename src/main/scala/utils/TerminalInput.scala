package utils

import CancellableFuture.*
import controller.AppController
import controller.StandardGameController.SelectAnswer
import java.util.concurrent.{CountDownLatch, TimeUnit}
import scala.concurrent.{Future, Promise}
import concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine
import scala.util.{Failure, Success}

trait TerminalInput:

  var cancellable: (Future[String], () => Boolean)
  def readInput(): Future[String]
  def stopInput(): Unit

case class TerminalInputImpl() extends TerminalInput:

  var cancellable: (Future[String], () => Boolean) = _

  override def readInput(): Future[String] =
    cancellable = interruptableFuture[String] { () =>
      readLine()
    }
    cancellable._1

  override def stopInput(): Unit =
    cancellable._2.apply()
