package utils

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.{CancellationException, ExecutionContext, Future, Promise}
import scala.concurrent.ExecutionContext.global

object CancellableFuture:

  def interruptableFuture[T](fun: () => T)(implicit ex: ExecutionContext = ExecutionContext.global): (Future[T], () => Boolean) = {
    val p = Promise[T]()
    val f = p.future
    val aref = new AtomicReference[Thread](null)
    p completeWith Future {
      val thread = Thread.currentThread
      aref.synchronized {
        aref.set(thread)
      }
      try fun() finally {
        val wasInterrupted = (aref.synchronized {
          aref getAndSet null
        }) ne thread
        //Deal with interrupted flag of this thread in desired
      }
    }

    (f, () => {
      aref.synchronized {
        Option(aref getAndSet null) foreach {
          _.interrupt()
        }
      }
      p.tryFailure(new CancellationException)
    })
  }
