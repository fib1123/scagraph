package eval

import com.twitter.util.Eval

/**
 * Created by Stanislaw on 2015-01-25.
 */
class Evaluator {
  
  def getFunction(input: String): Double => Double = {
    new Eval()("(x: Double) => " ++ input)
  }
  
  def printResult(input: String): Unit = {
    println(new Eval()(input))
  }

}
