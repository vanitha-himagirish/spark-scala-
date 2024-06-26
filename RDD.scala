//Using Parallelize to create RDD
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ParallelizeRDD {

    def main(args:Array[String]) : Unit = {
        val spark=SparkSession.builder().master("local[1]")
          .appName("rdd_spark")
          .getOrCreate()       
        println(spark.version)
        val rdd=spark.sparkContext.parallelize(List(1,2,3,4))
        val rddCollect=rdd.collect()
        println("# of partitions:" + rdd.getNumPartitions)
        println("Action: Get first " + rdd.first())
        println("Action: RDD converted to Array[Int] : ")
        rddCollect.foreach(println)

        //Creating empty RDD with parallelize
        val emptyRDD=spark.sparkContext.parallelize(Seq.empty[String])
        emptyRDD.collect()
        
        //Create emptyRDD using emptyRDD
        val emptyRDD = spark.sparkContext.emptyRDD[String]
        emptyRDD.collect()
    }

}
ParallelizeRDD.main(Array("spark_example"))

