package model.canvas

import akka.actor.Actor.Receive
import akka.event.Logging
import model.{GraphProperties, Point, Graph}
import akka.actor.{ActorRef, Actor, Props}
import utils.ActorReminder

import scala.collection.immutable.HashMap

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */

class Canvas (canvasProperties: CanvasProperties) extends Actor {

  val log = Logging(context.system, this)

  var graphs: Map[ActorRef, GraphProperties] = new HashMap[ActorRef, GraphProperties]
  private var newPoints: Map[Point, ActorRef] = new HashMap[Point, ActorRef]
  var points: Map[GraphProperties, List[Point]] = new HashMap[GraphProperties, List[Point]]
  //var pointsXMap: Map[Double, List[(Int, Int)]]

  def receive = {
    case "init" => start()

    case (point: Point) => draw(sender(), point)

    case (props: Props, graphProperties: GraphProperties, initialPoint: Point) =>
      val graphRef = context.actorOf(props)
      graphs = graphs + (graphRef -> graphProperties)
      graphRef ! (canvasProperties.drawingMode.period, 0.1, initialPoint)

    case "PeriodEnd" => periodLoop()
  }

  def start() = {
    val reminderProps = Props(new ActorReminder(canvasProperties.drawingMode.period))
    val cr = context.actorOf(reminderProps)
    cr ! "start"
    log.info("Reminder started")
  }

  def draw(graph: ActorRef, point: Point) = {
    newPoints = newPoints + (point -> graph)
  }

  def periodLoop(): Unit = {
    points = insertPoints(newPoints, points)
    newPoints = new HashMap[Point, ActorRef]
    canvasProperties.GUICanvas.update(points)
  }

  def insertPoints(newPoints: Map[Point, ActorRef],
                    currentPoints: Map[GraphProperties, List[Point]])
  : Map[GraphProperties, List[Point]] = {
    var newCurrentPoints = currentPoints
    newPoints.size match {
      case 0 => currentPoints
      case _ =>
        val newPoint = newPoints.keySet.head
        val newPointProps = graphs(newPoints(newPoint))
        currentPoints.contains (newPointProps) match {
          case false => newCurrentPoints = currentPoints + (newPointProps -> List[Point] () )
          case true =>
        }
        newCurrentPoints = newCurrentPoints +
        (newPointProps -> (newCurrentPoints (newPointProps) :+ newPoint) )
        insertPoints (newPoints - newPoint, newCurrentPoints)
    }
  }
}

object Canvas {
  def props(canvP: CanvasProperties) = {
    Props(new Canvas(canvasProperties = canvP))
  }
}