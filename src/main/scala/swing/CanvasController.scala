package swing

import model.{Point, GraphProperties}
import model.canvas.GUICanvas

import scala.collection.immutable.HashMap

/**
 * Created by fazzou on 27.12.14.
 */
class CanvasController(val canvas: SwingCanvas) extends GUICanvas {

  def update(points: Map[GraphProperties, List[Point]]) = {
    canvas.update(points)
  }
}
