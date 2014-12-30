package model

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Point (val x: Double, val y: Double) {
  def afterDelta(delta: Double, func: (Double) => Double): Point = { new Point(x + delta, func(x + delta)) }
  override def toString: String = {
    "(" + x.toString + ", " + y.toString + ")"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Point]

  override def equals(other: Any): Boolean = other match {
    case that: Point =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}