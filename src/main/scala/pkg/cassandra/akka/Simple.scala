package pkg.cassandra.akka
import com.datastax.driver.core._
object Simple extends App {

  val cluster = Cluster.builder
    .withClusterName("myCluster")
    .addContactPoint("localhost")
    .build
  val session = cluster.connect("Test")

  //  session.execute("CREATE KEYSPACE keyspaceTest WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 2};")
  session.execute("DROP TABLE IF EXISTS base_pos_data;")
/*  session.execute("CREATE TABLE IF NOT EXISTS base_pos_data " +
  "(year int PRIMARY KEY ,month int,day int,age int,frequency int," +
  "income int,value_segment int,brand text,category text,class text," +
    "style text,color_type text,choice_code int,color_family text,tier text," +
    "region text,location text,tx_time timeuuid,tx_id int,unit int,Basket_value int PRIMARY KEY ((year), month, day, region, location));" +
    "WITH caching = { 'keys' : 'ALL', 'rows_per_partition' : '10' };")*/

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

  session.execute("drop table sample_times;")
  session.execute(
    "CREATE TABLE sample_times (a int, b timestamp, c timeuuid, d bigint, PRIMARY KEY (a,b,c,d));")
  session.execute(
    "INSERT INTO sample_times (a,b,c,d) VALUES (1, toUnixTimestamp(now()), 50554d6e-29bb-11e5-b345-feff819cdc9f, toTimestamp(now()));")

  val result = session.execute("select * from base_pos_data;")
  println(result.all)

  cluster.close()
}
