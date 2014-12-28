package model.canvas

import akka.actor.Actor.Receive
import model.{GraphProperties, Point, Graph}
import akka.actor.{Actor, Props}
import utils.ActorReminder

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Canvas (graphs: List[Graph],
              canvasProperties: CanvasProperties,
               private var newPoints: Map[Point, Int],
               var points: Map[Double, Map[Double, GraphProperties]])
  extends Actor {

  def start() = {
    val cr = new ActorReminder(self, canvasProperties.drawingMode.period)
    cr.self ! "LetsGo"
  }

  def receive = {
    case (id: Int, point: Point) => draw(id, point)
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
    newPoints.size match {
      case 0 =>
        currentPoints
      case _ =>
        val point = newPoints.keySet.head
        val newCurrent = currentPoints +
          (point.x -> (currentPoints(point.x) +
            (point.y -> graphs(newPoints(point)).graphProperties)))
        insertPoints(newPoints - point, newCurrent)
    }
  }
}

