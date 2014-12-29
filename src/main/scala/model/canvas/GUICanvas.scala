package model.canvas

import model.GraphProperties

/**
 * Created by Stanislaw Robak on 2014-12-27.
 */
trait GUICanvas {
  def update(points: Map[Double, Map[Double, GraphProperties]])
}
