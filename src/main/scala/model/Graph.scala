package model

import akka.actor.{Props, ActorRef, Actor}
import akka.event.Logging
import utils.ActorReminder

import scala.annotation.tailrec

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Graph (val func: Double => Double) extends Actor {

  val log = Logging(context.system, this)

  @tailrec
  private def genPointLoop(canvasRef: ActorRef, gPeriod: Int, gDelta: Double, currentPoint: Point): Unit = {
    val newPoint = currentPoint.afterDelta(gDelta, func)
    canvasRef ! newPoint
    Thread.sleep(gPeriod)

    genPointLoop(canvasRef, gPeriod, gDelta, newPoint)
  }

  def receive: Receive = {
    case (gPeriod: Int, gDelta: Double, initialPoint: Point) =>
      log.info("graph " + self + " initialised\n")

      genPointLoop(context.parent, gPeriod, gDelta, initialPoint)
  }
}


object Graph {
  def props(func: Double => Double): Props = Props(new Graph(func))
}