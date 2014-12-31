package model.canvas

import model.{Point, GraphProperties}

/**
 * Created by Stanislaw Robak on 2014-12-27.
 */
trait GUICanvas {
  def update(points: Map[GraphProperties, List[Point]])
}
