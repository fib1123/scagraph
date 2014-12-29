package swing

import model.{Point, GraphProperties}
import model.canvas.GUICanvas

import scala.collection.immutable.HashMap

/**
 * Created by fazzou on 27.12.14.
 */
class CanvasController(val canvas: Canvas) extends GUICanvas {

  def update(points: Map[Double, Map[Double, GraphProperties]]) = {
    var hm = new HashMap[Point, GraphProperties]
    for (x <- points.toMap.keySet) {
      for (y <- points(x).keySet) {
        hm = hm + (new Point(x, y) -> points(x)(y))
      }
    }
    canvas.update(hm)
  }
}
