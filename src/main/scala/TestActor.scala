import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import model.{Point, GraphProperties, Graph}

import scala.annotation.tailrec

/**
 * Created by fazzou on 28.12.14.
 */
class TestActor(var replyTo: ActorRef = null, val period: Int) extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case ar: ActorRef => this.replyTo = ar
      log.info("Registered ActorRef")
    case id: Int => loop(0, id)
      log.info("Starting to draw")
    case "register" => replyTo ! new GraphProperties
      log.info("Registration message sent")
  }

  @tailrec final def loop(x: Double, id: Int): Unit = {
    Thread.sleep(period)
    replyTo ! (id, new Point(x, x))
    loop(x + 0.01, id)
  }

}
