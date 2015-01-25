/**
 * Created by Stanislaw Robak on 2014-12-26.
 */

import java.awt.Color

import akka.actor.{ActorSystem, Props, Actor, ActorRef}
import controllers.MainController
import eval.Evaluator
import model.canvas.{Canvas, CanvasProperties}
import model.{Point, GraphProperties}
import swing._

import scala.collection.immutable.{Range, HashMap}

object Main extends App {
//  println("Welcome to Scagraph!")
//  val cc = new CanvasController(new Canvas(new HashMap[Point, GraphProperties]))
//  val aw = new AppWindow(cc)
//  aw.startup(Array())
//  var testMap = new HashMap[Double, Map[Double, GraphProperties]]
//  for (i <- 1 to 100) {
//    var hm = new HashMap[Double, GraphProperties]
//    val gp = new GraphProperties
//    hm = hm + (i.toDouble -> gp)
//    testMap = testMap + (i.toDouble -> hm)
//  }
//  cc.update(testMap)

//
//  val system = ActorSystem()
//
//  val ta = system.actorOf(TestProps.getProps._1)
//  val tc = system.actorOf(TestProps.getProps._2)
//
//  tc ! "init"
//  ta ! tc
//  ta ! "register"

  val cc = new CanvasController(new SwingCanvas)
  val aw = new AppWindow(cc)
  aw.startup(Array())

  val controller = new MainController(cc)
  val canvasRef = controller.newCanvas(null)

//  controller.newGraph(canvasRef, x => Math.sin(x) - 15, new GraphProperties(color = Color.green))
//  controller.newGraph(canvasRef, x => Math.sin(x) - 10, new GraphProperties(color = Color.MAGENTA))
//  controller.newGraph(canvasRef, x => Math.sin(x) - 5, new GraphProperties(color = Color.green))
//  controller.newGraph(canvasRef, x => Math.sin(x) + 5, new GraphProperties(color = Color.MAGENTA))
//  controller.newGraph(canvasRef, x => Math.sin(x) + 10, new GraphProperties(color = Color.green))
//  controller.newGraph(canvasRef, x => Math.sin(x) + 15, new GraphProperties(color = Color.MAGENTA))
//  controller.newGraph(canvasRef, x => 20/(x-30), new GraphProperties(color = Color.black))

  val eval = new Evaluator
  controller.newGraph(canvasRef, eval.getFunction("Math.sin(x) - 15"), new GraphProperties(color = Color.green))
  controller.newGraph(canvasRef, eval.getFunction("20/(x-30)"), new GraphProperties(color = Color.black))
  controller.newGraph(canvasRef,  eval.getFunction("Math.sin(x) + 10"), new GraphProperties(color = Color.green))
  controller.newGraph(canvasRef,  eval.getFunction("Math.sin(x) + 15"), new GraphProperties(color = Color.MAGENTA))
  
}

