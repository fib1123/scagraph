package swing

import java.awt.Color

import _root_.model.{Point, GraphProperties}

import scala.collection.immutable.HashMap
import scala.swing.BorderPanel.Position._
import scala.swing._

/**
 * Created by fazzou on 27.12.14.
 */
class AppWindow(cc : CanvasController,
                frameSize: Int = 500) extends SimpleSwingApplication {

    def top = new MainFrame {
        // top is a required method
        title = "Scagraph"

        // declare Components here
        val label = new Label {
          text = "Welcome to Scagraph!"
          font = new Font("Ariel", java.awt.Font.ITALIC, 10)
        }
        val swingCanvas = cc.canvas

        swingCanvas.preferredSize = new Dimension(frameSize, frameSize)

        val gridPanel = new GridPanel(1, 2) {
          contents += label
        }

        // choose a top-level Panel and put components in it
        // Components may include other Panels
        contents = new BorderPanel {
          layout(gridPanel) = North
          layout(swingCanvas) = Center
        }
        size = new Dimension(frameSize, frameSize)
        menuBar = new MenuBar {
          contents += new Menu("File") {
            contents += new MenuItem(Action("Exit") {
              sys.exit(0)
            })
          }
        }
        resizable = false

      cc.canvas.shouldRefreshView = true
    }
}
