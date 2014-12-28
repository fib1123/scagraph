package model.canvas

import akka.actor.{ActorRef, Actor}
import model.{GraphProperties, Point}
import org.scalatest.FunSuite
import swing.{AppWindow, Canvas, CanvasController}

import scala.collection.immutable.HashMap

/**
 * Created by fazzou on 28.12.14.
 */
class CanvasTest extends FunSuite {

//  test("draw line") {
//    println("Welcome to Scagraph test!")
//  val cc = new CanvasController(new swing.Canvas(new HashMap[Point, GraphProperties]))
//  val aw = new AppWindow(cc)
//  aw.startup(Array())
//
//  class TestActor(val replyTo: ActorRef, val period: Int) extends Actor {
//
//    def start() = {
//      replyTo ! this
//    }
//
//    def receive = {
//      case id: Int => loop(0, id)
//      case _ =>
//    }
//
//    def loop(x: Double, id: Int): Unit = {
//      Thread.sleep(period)
//      replyTo ! (id, new Point(x, x))
//      loop(x + 0.001, id)
//    }
//
//  }
//
//  val canvP = new CanvasProperties(GUICanvas = cc)
//  val canv = new Canvas(canvasProperties = canvP)
//  val ta = new TestActor(canv.self, 10)
//
//  ta.start()
//  }
}
