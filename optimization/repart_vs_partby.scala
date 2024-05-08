// Databricks notebook source
//Refer to this link . Its published here ..-
// https://databricks-prod-cloudfront.cloud.databricks.com/public/4027ec902e239c93eaaa8714f173bcfc/6282898221377548/1373301107822423/3842820456753764/latest.html

// Difference Between Repartition and Partition By
// 
//  Repartition : 
//  1. Used for In-Memory partitioning
//  2. Allows to control over the number of partitions
//  3. Useful for shuffling of the data across partitions that is useful for operations like joins and aggregations
//  4. Since it involves shuffling of data, it might be expensive
// 
//  Partition By:
//  1. Used for Partitioning the data on disk while writing files like parquet or ORC files that support partitioning
//  2. Creates subdirectories within the output directory, with each subdirectory containing data for a specific partition value.\
//  3. Improves query performance
//  4. Does not shuffle data and does not affect in memory processing
// 

 ----------

// Import 
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.Window

 ----------

//Reading data
val df=spark.read
  .option("Header",true)
  .option("inferSchema", "true")
  .csv("/FileStore/scala/data/personal_transactions.csv")
df.printSchema
df.show()

 ----------

df.rdd.getNumPartitions

 ----------

df.count

 ----------

//  %md
//  Syntax Difference between repartition and partition by

 ----------

//Repratition based on a partition number
df.repartition(3).rdd.getNumPartitions

 ----------

//Repartition based on a column
df.repartition(col("Customer_No")).rdd.getNumPartitions

 ----------

//Repartition based on number and column
import org.apache.spark.sql.functions.spark_partition_id
df.repartition(3,col("Customer_No")).withColumn("PartitionID",spark_partition_id()).groupBy("PartitionID").count.show()


 ----------

df.write.partitionBy("Customer_No")

 ----------

//  %md
//  Functional Difference

 ----------

//In case of repartition, the partition is made in memory but while saving the disk it has not created partitions on the column
df
  .repartition(col("Customer_No"))
  .write.format("csv")
  .option("header",true)
  .mode("overwrite")
  .save("/FieStore/practice/output/customer_repart")

 ----------

//  %fs ls "/FieStore/practice/output/customer_repart"

 ----------

//Using partition by while creating, creating partitions on the disk or storage 
df
  .write
  .partitionBy("Customer_No")
  .format("csv")
  .option("header",true)
  .mode("overwrite")
  .save("/FieStore/practice/output/customer_part")

 ----------

//  %fs ls "/FieStore/practice/output/customer_part"
