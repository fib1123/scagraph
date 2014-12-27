package model.canvas

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class CanvasProperties (val xMin: Double = -100.0, val xMax: Double = 100.0,
                         val yMin: Double = -100.0, val yMax: Double = 100.0,
                          val GUICanvas: GUICanvas,
                           val drawingMode: DrawingMode = new DrawByX)
