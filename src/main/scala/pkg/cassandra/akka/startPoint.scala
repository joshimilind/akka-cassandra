package pkg.cassandra.akka
import java.sql.ResultSet

import akka.actor._
import akka.actor.{ActorRef, ActorSystem, Props}
import com.datastax.driver.core.ResultSet
import pkg.cassandra.akka.Simple.session

class Initiator(FireQuery: ActorRef, query: String){

  session.execute("DROP TABLE IF EXISTS base_pos_data;")

    session.execute("CREATE TABLE IF NOT EXISTS base_pos_data(year int, month int,day int,age int,frequency int," +
    " income int,value_segment int,brand text,category text,class text, style text,color_type text,choice_code int," +
    "color_family text,tier text, region text,location text,tx_time timeuuid,tx_id int,unit int," +
    "Basket_value int, PRIMARY KEY ((year, age, brand), month, day, region, location))" +
    " WITH caching = { 'keys' : 'ALL', 'rows_per_partition' : '10' };")

  session.execute(
    "insert into base_pos_data (year, month, day, age, frequency , income, value_segment ," +
      " brand, category, class, style, color_type, choice_code, color_family, tier, region," +
      " location, tx_time, tx_id, unit, Basket_value) values " +
      "(2000,5,21,19,18,15000,10,'xyzBrand','CatogoricalVariable','Aclass','style','redColor'," +
      "1234,'warmColor','threeTier','southwestRegion','India',now(),01,5,1221)")

  FireQuery ! QueryToFire(query)

  //todo

  def receive = {
    case ResultSet() =>
      println(result.all)
  }
}
object Initiator extends App{
  println("starting here")
  val _system = ActorSystem("NewToCassandra")
  val FireQuery = _system.actorOf(Props[SubmitActor], "QuerySubmit")
  val start = _system.actorOf(Props[Initiator](new Initiator(FireQuery, "select * from base_pos_data;")))
}
