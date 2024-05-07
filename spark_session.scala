//Creating Spark Session
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ParallelizeRDD {

    def main(args:Array[String]) : Unit = {
        val spark=SparkSession.builder().master("local[1]")
          .appName("rdd_spark")
          .getOrCreate()       
        println(spark.version)
    }

}
ParallelizeRDD.main(Array("spark_example"))
