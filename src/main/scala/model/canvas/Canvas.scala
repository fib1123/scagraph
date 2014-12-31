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
  var points: Map[Double, Map[Double, GraphProperties]] = new HashMap[Double, Map[Double, GraphProperties]]

  def receive = {
    case "init" => start()

    case (point: Point) => draw(sender(), point)

    case (props: Props, graphProperties: GraphProperties) =>
      val graphRef = context.actorOf(props)
      graphs = graphs + (graphRef -> graphProperties)
      graphRef ! (canvasProperties.drawingMode.period, 0.1, new Point(0, 30))

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
    canvasProperties.GUICanvas.update(points)
  }

  private def insertPoints(newPoints: Map[Point, ActorRef],
                           currentPoints: Map[Double, Map[Double, GraphProperties]]):
  Map[Double, Map[Double, GraphProperties]] = {

    (newPoints.size, currentPointsContainsXFromFirstNew(newPoints, currentPoints)) match {
      case (0, _) =>
        currentPoints
      case (_, true) =>
        val point = newPoints.keySet.head
        val newCurrent = currentPoints +
          (point.x -> (currentPoints(point.x) +
            (point.y -> graphs(newPoints(point)))))
        insertPoints(newPoints - point, newCurrent)
      case (_, false) =>
        val point = newPoints.keySet.head
        val newCurrent = currentPoints +
          (point.x -> (new HashMap[Double, GraphProperties] +
            (point.y -> graphs(newPoints(point)))))
        insertPoints(newPoints - point, newCurrent)
    }
  }

  private def currentPointsContainsXFromFirstNew(newPoints: Map[Point, ActorRef],
                                                 currentPoints: Map[Double, Map[Double, GraphProperties]])
  : Boolean = {
    newPoints.size match {
      case 0 => false
      case _ => currentPoints.contains(newPoints.keySet.head.x)
    }
  }
}

object Canvas {
  def props(canvP: CanvasProperties) = {
    Props(new Canvas(canvasProperties = canvP))
  }
}