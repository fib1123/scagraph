package model

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Point (val x: Double, val y: Double) {
  def afterDelta(delta: Double, func: (Double) => Double) = new Point(x + delta, func(x+delta))
}