package swing

import model.Point

import scala.swing.Dimension

/**
 * Created by fazzou on 30.12.14.
 */
object SwingCanvasProperties {

  def getZeroCoordinates(scp: SwingCanvasProperties, canvSize: Dimension): scala.swing.Point = {
    val centerX = canvSize.getWidth/2 + scp.centerPoint.x
    val centerY = canvSize.getHeight/2 - scp.centerPoint.y
    new scala.swing.Point(centerX.toInt, centerY.toInt)
  }

}

class SwingCanvasProperties(val centerPoint: Point = new Point(-150, 0),
                            val zoom: Double = 5) {

}
