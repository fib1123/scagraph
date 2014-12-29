package model

import akka.actor.{Props, ActorRef, Actor}
import akka.event.Logging
import utils.ActorReminder

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Graph (val id: Int, val func: Double => Double) extends Actor {

  val log = Logging(context.system, this)

  def genPointLoop(canvasRef: ActorRef, gPeriod: Int, gDelta: Double, currentPoint: Point): Unit = {
    val newPoint = currentPoint.afterDelta(gDelta, func)
    canvasRef ! (id, newPoint)
    println(newPoint)
    Thread.sleep(gPeriod)

    genPointLoop(canvasRef, gPeriod, gDelta, newPoint)
  }

  def receive: Receive = {
    case (gPeriod: Int, gDelta: Double, initialPoint: Point) =>
      log.info("graph " + self + " initialised\n")

      genPointLoop(context.parent, gPeriod, gDelta, initialPoint)
    //      val reminderProps = Props(classOf[ActorReminder], this.self, gPeriod)
    //      val cr = context.actorOf(reminderProps)
    //      cr ! "start"


    //    case collision =>
  }
}


object Graph {

  def props(id: Int, func: Double => Double): Props = Props(new Graph(id, func))
}