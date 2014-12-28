package model

/**
 * Created by Stanislaw Robak on 2014-12-26.
 */
class Graph (val func: Double => Double, val graphProperties: GraphProperties) {

  // newPoint loop - with sending messages after period

  // <receive>
  //  (Id, gFrequency, gDelta, InitialPoint)
  //  (collision)
}


object Graph {

  def apply(func: Double => Double) =
    new Graph(func, new GraphProperties) // creating graph with default properties

}