// Databricks notebook source
// MAGIC %md 
// MAGIC Difference Between Repartition and Partition By
// MAGIC
// MAGIC Repartition : 
// MAGIC 1. Used for In-Memory partitioning
// MAGIC 2. Allows to control over the number of partitions
// MAGIC 3. Useful for shuffling of the data across partitions that is useful for operations like joins and aggregations
// MAGIC 4. Since it involves shuffling of data, it might be expensive
// MAGIC
// MAGIC Partition By:
// MAGIC 1. Used for Partitioning the data on disk while writing files like parquet or ORC files that support partitioning
// MAGIC 2. Creates subdirectories within the output directory, with each subdirectory containing data for a specific partition value.\
// MAGIC 3. Improves query performance
// MAGIC 4. Does not shuffle data and does not affect in memory processing
// MAGIC

// COMMAND ----------

// Import 
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.Window

// COMMAND ----------

//Reading data
val df=spark.read
  .option("Header",true)
  .option("inferSchema", "true")
  .csv("/FileStore/scala/data/personal_transactions.csv")
df.printSchema
df.show()

// COMMAND ----------

df.rdd.getNumPartitions

// COMMAND ----------

df.count

// COMMAND ----------

// MAGIC %md
// MAGIC Syntax Difference between repartition and partition by

// COMMAND ----------

//Repratition based on a partition number
df.repartition(3).rdd.getNumPartitions

// COMMAND ----------

//Repartition based on a column
df.repartition(col("Customer_No")).rdd.getNumPartitions

// COMMAND ----------

//Repartition based on number and column
import org.apache.spark.sql.functions.spark_partition_id
df.repartition(3,col("Customer_No")).withColumn("PartitionID",spark_partition_id()).groupBy("PartitionID").count.show()


// COMMAND ----------

df.write.partitionBy("Customer_No")

// COMMAND ----------

// MAGIC %md
// MAGIC Functional Difference

// COMMAND ----------

//In case of repartition, the partition is made in memory but while saving the disk it has not created partitions on the column
df
  .repartition(col("Customer_No"))
  .write.format("csv")
  .option("header",true)
  .mode("overwrite")
  .save("/FieStore/practice/output/customer_repart")

// COMMAND ----------

// MAGIC %fs ls "/FieStore/practice/output/customer_repart"

// COMMAND ----------

//Using partition by while creating, creating partitions on the disk or storage 
df
  .write
  .partitionBy("Customer_No")
  .format("csv")
  .option("header",true)
  .mode("overwrite")
  .save("/FieStore/practice/output/customer_part")

// COMMAND ----------

// MAGIC %fs ls "/FieStore/practice/output/customer_part"
