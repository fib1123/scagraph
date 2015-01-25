package eval

/**
 * Created by Stanislaw on 2015-01-25.
 */

// Only testing purposes
object EvalMain extends App {
  
  val eval = new Evaluator
  eval.getFunction("x*2")
  println("ok")
}
