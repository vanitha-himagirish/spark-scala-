import org.apache.spark.sql.SparkSession
object SparkSessionTest extends App {
  val spark = SparkSession.builder()
      .master("local[1]")
      .appName("mysparkapp")
      .getOrCreate();
  println(spark)
  println(
