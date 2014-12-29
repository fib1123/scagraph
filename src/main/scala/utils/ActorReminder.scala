package utils

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive

/**
 * Created by fazzou on 28.12.14.
 */
object ActorReminder {

}

class ActorReminder(val period: Int) extends Actor {

  def receive = {
    case "start" => remind()
    case _ =>
  }

  def remind() = {
    while (true) {
      Thread.sleep(period)
      context.parent ! "PeriodEnd"
    }
  }
}
