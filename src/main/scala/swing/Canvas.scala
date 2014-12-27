package swing

import model.{Point, GraphProperties}

import scala.collection.mutable
import scala.swing.{Graphics2D, Panel}

/**
 * Created by fazzou on 27.12.14.
 */
class Canvas(var points : Map[Point, GraphProperties]) extends Panel {

  override protected def paintComponent(g: Graphics2D): Unit = {
    g.clearRect(0, 0, size.width, size.height)
    val iter = points.keysIterator
    for (point <- points.keySet) {
        val col = points(point).color
        val width = points(point).width.toInt
        g.setColor(col)
        g.drawOval(point.x.toInt, point.y.toInt, width, width)
    }
  }

  def update(points: Map[Point, GraphProperties]) = {
    this.points = points
    repaint()
  }

}
