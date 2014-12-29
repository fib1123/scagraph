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

object Canvas {
  def props(canvP: CanvasProperties) = {
    Props(new Canvas(canvasProperties = canvP))
  }
}

class Canvas (var graphs: List[GraphProperties] = List(),
              canvasProperties: CanvasProperties,
               private var newPoints: Map[Point, Int] = new HashMap[Point, Int],
               var points: Map[Double, Map[Double, GraphProperties]] =
               new HashMap[Double, Map[Double, GraphProperties]])
  extends Actor {

  val log = Logging(context.system, this)

  def start() = {
    val reminderProps = Props(new ActorReminder(canvasProperties.drawingMode.period))
    val cr = context.actorOf(reminderProps)
    cr ! "start"
    log.info("Reminder started")
  }

  def receive = {
    case "init" => start()
    case (id: Int, point: Point) => draw(id, point)
    case graphProps: GraphProperties => graphs = graphs :+ graphProps
      sender() ! graphs.indexOf(graphProps)
      log.info("New graph: " + graphProps + " sender: " + sender())
    case "PeriodEnd" => periodLoop()
  }

  def draw(id: Int, point: Point) = {
    newPoints = newPoints + (point -> id)
  }

  def periodLoop(): Unit = {
    points = insertPoints(newPoints, points)
    canvasProperties.GUICanvas.update(points)
  }

  private def insertPoints(newPoints: Map[Point, Int],
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

  private def currentPointsContainsXFromFirstNew(newPoints: Map[Point, Int],
                                                 currentPoints: Map[Double, Map[Double, GraphProperties]])
  : Boolean = {
    newPoints.size match {
      case 0 => false
      case _ => currentPoints.contains(newPoints.keySet.head.x)
    }
  }
}

