package swing

import java.awt.Color

import akka.actor.Props
import model.{Point, GraphProperties}

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.swing.{Graphics2D, Panel}

/**
 * Created by fazzou on 27.12.14.
 */
class SwingCanvas(private var points : Map[Point, GraphProperties] =
                  new HashMap[Point, GraphProperties],
                   private var currentProperties: SwingCanvasProperties =
                   new SwingCanvasProperties,
                   var shouldRefreshView: Boolean =
                   true,
                   private var newPoints : Map[Point, GraphProperties] =
                   new HashMap[Point, GraphProperties]) extends Panel {

  override protected def paintComponent(g: Graphics2D): Unit = {
    shouldRefreshView match {
      case true => println("Should refresh!")
        drawAxes(g)
        drawAllPoints(g)
      case false => drawNewPoints(g)
    }
  }

  def update(points: Map[Point, GraphProperties],
             guiCanvasProperties: SwingCanvasProperties = this.currentProperties) = {
    shouldRefreshView = this.currentProperties != guiCanvasProperties
    this.newPoints = points
    repaint()
  }

  def changeProperties(guiCanvasProperties: SwingCanvasProperties) = {
    println("blbl")
    this.currentProperties = guiCanvasProperties
  }

  def drawAxes(g: Graphics2D) = {
    println("Drawing axes")
    g.clearRect(0, 0, size.width, size.height)
    val edge = this.size.getHeight.toInt
    val center = SwingCanvasProperties
      .getZeroCoordinates(this.currentProperties, this.size)
    g.setColor(Color.lightGray)
    g.fillRect(center.x, 0, 1, this.size.getHeight.toInt)
    g.fillRect(0, center.y, this.size.getWidth.toInt, 1)
    println(center.x)
    println(this.size.getWidth.toInt)
  }

  def drawAllPoints(g: Graphics2D) = {
    println("Printing all points again!")
    points = newPoints
    drawPointsFrom(points, g)
  }

  def drawNewPoints(g: Graphics2D) = {
    val toDraw = newPoints -- points.keySet
    points = newPoints
    drawPointsFrom(toDraw, g)
  }

  def drawPointsFrom(p: Map[Point, GraphProperties], g: Graphics2D) = {
    var transMap = new HashMap[scala.swing.Point, GraphProperties]
    for(point <- points.keys) {
      transMap = transMap + (translate(point) -> points(point))
    }
    for (p <- transMap.keySet) {
      val gp = transMap(p)
      val col = gp.color
      val width = gp.width.toInt
      g.setColor(col)
      g.fillOval(p.x - width/2, p.y - width/2, width, width)
    }
  }

  def translate(point: Point) : scala.swing.Point = {
    val center = SwingCanvasProperties.getZeroCoordinates(this.currentProperties, this.size)
    val translatedX = center.x + point.x*currentProperties.zoom
    val translatedY = center.y - point.y*currentProperties.zoom
    new scala.swing.Point(translatedX.toInt, translatedY.toInt)
  }

}
