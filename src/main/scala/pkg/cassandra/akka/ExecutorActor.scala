package pkg.cassandra.akka
import akka.actor._
import com.datastax.driver.core.Cluster

case class executeQuery(query: String){}

class ExecutorActor(query: String) extends Actor {

  val cluster = Cluster.builder
    .withClusterName("myCluster")
    .addContactPoint("localhost")
    .build

  //val table = "base_pos_data"
  val session = cluster.connect("sample")
//todo
  def receive = {

//    session.execute

  }

}

