package controllers

import akka.actor.Actor.Receive
import akka.actor.{Props, ActorSystem, ActorRef, Actor}
import model.{GraphProperties, Point, Graph}
import model.canvas.{GUICanvas, Canvas, CanvasProperties}
import swing.{SwingCanvas, AppWindow, CanvasController}

import scala.collection.immutable.HashMap

/**
 * Created by Stanislaw Robak on 2014-12-27.
 */
class MainController (guiCanvas: GUICanvas){

  val system = ActorSystem()

  def newCanvas(canvasProperties: CanvasProperties) = {
    val canvP = new CanvasProperties(GUICanvas = guiCanvas)
    val canv = Canvas.props(canvP)

    val canvasActor = system.actorOf(canv)
    canvasActor ! "init"
    canvasActor
  }

  def newGraph(canvasRef: ActorRef, func: Double => Double,
               graphProperties: GraphProperties = new GraphProperties(),
                initialPoint: Point = new Point(0,0)) = {

    val graphProps = Graph.props(func)
    canvasRef ! (graphProps, graphProperties, initialPoint)
    println("Adding " + func)
  }
}
