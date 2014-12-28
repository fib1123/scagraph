import akka.actor.{Actor, ActorRef}
import model.{Point, GraphProperties, Graph}

/**
 * Created by fazzou on 28.12.14.
 */
class TestActor(val replyTo: ActorRef, val period: Int) extends Actor {

  def receive = {
    case id: Int => loop(0, id)
    case "register" => replyTo ! new Graph((x) => x, new GraphProperties)
  }

  def loop(x: Double, id: Int): Unit = {
    Thread.sleep(period)
    replyTo !(id, new Point(x, x))
    loop(x + 0.001, id)
  }

}
