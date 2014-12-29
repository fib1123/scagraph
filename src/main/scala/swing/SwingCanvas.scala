package swing

import akka.actor.Props
import model.{Point, GraphProperties}

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.swing.{Graphics2D, Panel}

/**
 * Created by fazzou on 27.12.14.
 */
class SwingCanvas(var points : Map[Point, GraphProperties] = new HashMap[Point, GraphProperties]) extends Panel {

  override protected def paintComponent(g: Graphics2D): Unit = {
    g.clearRect(0, 0, size.width, size.height)
    val iter = points.keysIterator
    for (point <- points.keySet) {
        val col = points(point).color
        val width = points(point).width.toInt
        g.setColor(col)
        g.fillOval(point.x.toInt, point.y.toInt, width, width)
    }
  }

  def update(points: Map[Point, GraphProperties]) = {
    this.points = points
    repaint()
  }

}
