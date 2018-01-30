package pkg.cassandra.akka
import akka.actor._

case class QueryToFire(query: String){}

class SubmitActor extends Actor {

  var executed_Query = Set.empty[String]

  def receive = {
    case QueryToFire(query) =>
      if(!executed_Query(query)) {
        query -> context.actorOf(Props[ExecutorActor](new ExecutorActor(query)))
      }
      executed_Query += query

  }
}
