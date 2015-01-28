package swing

import java.awt.Color

import _root_.model.GraphProperties
import akka.actor.ActorRef
import com.twitter.util.Eval
import controllers.MainController
import eval.Evaluator

import scala.swing._
import scala.swing.event.ButtonClicked

/**
 * Created by fazzou on 25.01.15.
 */
class GraphAdderWindow(controller : MainController, evaluator: Evaluator, canvasRef: ActorRef) extends MainFrame {
  title = "Add Graph"
  preferredSize_=(new Dimension(400, 100))
  resizable = false

  val textField = new TextField {
    preferredSize_=(new Dimension(300, 30))
  }

  val evalButton = new Button("Evaluate!")

  val flowPanel = new FlowPanel {
    contents += textField
    contents += evalButton
  }

  contents = new BoxPanel(Orientation.Horizontal) {
    contents += flowPanel
  }

  listenTo(`evalButton`)

  reactions += {
    case ButtonClicked(`evalButton`) => {
      println("Evaluating " + textField.text)
      controller.newGraph(canvasRef, evaluator.getFunction(textField.text), new GraphProperties(color = Color.black))
    }
  }
}
