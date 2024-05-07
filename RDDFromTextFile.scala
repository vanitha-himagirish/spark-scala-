import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RDDReadTextFiles {

    def main(args:Array[String]) : Unit = {
        val spark=SparkSession.builder().master("local[1]")
          .appName("rdd_spark_textfiles")
          .getOrCreate()       
        
        println("##spark read text files from a directory into RDD")
        val rddFromFile=spark.sparkContext.textFile("/FileStore/text")
        rddFromFile.collect().foreach(f=>{
          println(f)
        })

        println("##read multiple text files into a RDD")
        val rdd2=spark.sparkContext.textFile("/FileStore/text/text01.txt,"+"/FileStore/text/text02.txt")
        rdd2.collect.foreach(f=>{println(f)})

        println("##read text files base on wildcard character")
        val rdd3=spark.sparkContext.textFile("/FileStore/text/text*.txt*")
        rdd3.collect.foreach(f=>{println(f)})

        println("##read all text files from a directory to single RDD")
        val rdd4=spark.sparkContext.textFile("/FileStore/text/")
        rdd4.collect.foreach(f=>{println(f)})

        println("##read whole text files")
        val rdd5=spark.sparkContext.wholeTextFiles("/FileStore/text/")
        rdd5.collect.foreach(f=>{println(f)})
  

    }

}
RDDReadTextFiles.main(Array("spark_example"))





