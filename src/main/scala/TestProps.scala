import akka.actor.{Props, Actor, ActorRef}
import model.canvas.{Canvas, CanvasProperties}
import model.{Graph, GraphProperties, Point}
import swing.{AppWindow, CanvasController}

import scala.collection.immutable.HashMap

/**
 * Created by fazzou on 28.12.14.
 */
object TestProps {

  def getProps: Props = {
    val cc = new CanvasController(new swing.Canvas(new HashMap[Point, GraphProperties]))
    val aw = new AppWindow(cc)
    aw.startup(Array())

    val canvP = new CanvasProperties(GUICanvas = cc)
    val canv = new Canvas(canvasProperties = canvP)

    Props(classOf[TestActor], canv.self, 10)
  }
}
