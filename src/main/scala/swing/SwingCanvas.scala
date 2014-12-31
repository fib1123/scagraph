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
class SwingCanvas() extends Panel {

  private var points : Map[GraphProperties, List[Point]] =
    new HashMap[GraphProperties, List[Point]]

  private var currentProperties: SwingCanvasProperties =
    new SwingCanvasProperties

  private var newPoints : Map[GraphProperties, List[Point]] =
    new HashMap[GraphProperties, List[Point]]

  var shouldRefreshView: Boolean =
    true

  override protected def paintComponent(g: Graphics2D): Unit = {
    shouldRefreshView match {
      case true => println("Should refresh!")
        drawAxes(g)
        drawAllPoints(g)
      case false => drawNewPoints(g)
    }
  }

  def update(points: Map[GraphProperties, List[Point]],
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
    val toDraw = subtract(points, newPoints)
    points = newPoints
    drawPointsFrom(toDraw, g)
  }

  def drawPointsFrom(p: Map[GraphProperties, List[Point]], g: Graphics2D) = {
    for(gp <- p.keys) {
      val zipped = p(gp).zip(p(gp).tail)
      for ((first, second) <- zipped) {
        val transFirst = translate(first)
        val transSecond = translate(second)
        val col = gp.color
        val width = gp.width.toInt
        g.setColor(col)
        g.drawLine(transFirst.x, transFirst.y, transSecond.x, transSecond.y)
      }
    }
  }

  def subtract(points1: Map[GraphProperties, List[Point]],
               points2: Map[GraphProperties, List[Point]]):
  Map[GraphProperties, List[Point]] = {
    var result = points1
    for (gp <- points1.keys) {
      points2.contains(gp) match {
        case true => result = result + (gp -> points1(gp)
          .filter((p: Point) => points2(gp).contains(p)))
        case false =>
      }
    }
    result
  }

  def translate(point: Point) : scala.swing.Point = {
    val center = SwingCanvasProperties.getZeroCoordinates(this.currentProperties, this.size)
    val translatedX = center.x + point.x*currentProperties.zoom
    val translatedY = center.y - point.y*currentProperties.zoom
    new scala.swing.Point(translatedX.toInt, translatedY.toInt)
  }

}
