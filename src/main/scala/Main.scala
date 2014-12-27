/**
 * Created by Stanislaw Robak on 2014-12-26.
 */

import model.{Point, GraphProperties}
import swing._

import scala.collection.immutable.{Range, HashMap}

object Main extends App {
  println("Welcome to Scagraph!")
  val cc = new CanvasController(new Canvas(new HashMap[Point, GraphProperties]))
  val aw = new AppWindow(cc)
  aw.startup(Array())
  var testMap = new HashMap[Double, Map[Double, GraphProperties]]
  for (i <- 1 to 100) {
    var hm = new HashMap[Double, GraphProperties]
    val gp = new GraphProperties
    hm = hm + (i.toDouble -> gp)
    testMap = testMap + (i.toDouble -> hm)
  }
  cc.update(testMap)
}

