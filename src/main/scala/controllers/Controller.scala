package controllers

import akka.actor.Actor.Receive
import akka.actor.{Props, ActorSystem, ActorRef, Actor}
import model.{GraphProperties, Point, Graph}
import model.canvas.{Canvas, CanvasProperties}
import swing.{SwingCanvas, AppWindow, CanvasController}

import scala.collection.immutable.HashMap

/**
 * Created by Stanislaw Robak on 2014-12-27.
 */
class Controller{

  val system = ActorSystem()

  def newCanvas(canvasProperties: CanvasProperties) = {
    val cc = new CanvasController(new SwingCanvas(new HashMap[Point, GraphProperties]))
    val aw = new AppWindow(cc)
    aw.startup(Array())

    val canvP = new CanvasProperties(GUICanvas = cc)
    val canv = Canvas.props(canvP)

    val canvasActor = system.actorOf(canv)
    canvasActor ! "init"
    canvasActor
  }

  def newGraph(canvasRef: ActorRef, func: Double => Double) = {
    val graphProperties = new GraphProperties

    canvasRef ! (func, graphProperties)
  }
}
