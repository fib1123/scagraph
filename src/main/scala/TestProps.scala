import akka.actor.{Props, Actor, ActorRef}
import model.canvas.{Canvas, CanvasProperties}
import model.{Graph, GraphProperties, Point}
import swing.{AppWindow, CanvasController}

import scala.collection.immutable.HashMap

/**
 * Created by fazzou on 28.12.14.
 */
object TestProps {

  def getProps: (Props, Props) = {
    val cc = new CanvasController(new swing.SwingCanvas(new HashMap[Point, GraphProperties]))
    val aw = new AppWindow(cc)
    aw.startup(Array())

    val canvP = new CanvasProperties(GUICanvas = cc)

    val canv = Canvas.props(canvP)

    (Props(new TestActor(period = 1)), canv)
  }
}
