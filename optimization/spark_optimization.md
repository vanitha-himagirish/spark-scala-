Following are the optimization techniques:

1. API Selection: Compared to RDDs , Dataframes and Spark SQL APIs offers higher levels of abstractions and increases the performance.
2. Data Serializtion: Choosing Kryo serialization over java serialization
3. Caching and persistance :Use cache and persist whenever required
   -  Using cache() and persist() methods, Spark provides an optimization mechanism to store the intermediate computation of a
     Spark DataFrame so they can be reused insubsequent actions.
   -  When you persist a dataset, each node stores it’s partitioned data in memory and reuses them in other actions on that dataset.
      And Spark’s persisted data on nodes are fault-tolerant meaning if any partition of a Dataset is lost, it will
      automatically be recomputed using the original transformations that created it.
      df.where(col("State") === "PR").cache()
		spark.conf.set("spark.sql.inMemoryColumnarStorage.compressed", true)
		spark.conf.set("spark.sql.inMemoryColumnarStorage.batchSize",10000)
   -   Using coaleasce instead of repartition
4. Partitioning and Bucketing
6. Shuffle Optimization: Spark shuffle operations involve data movement across the network. Techniques like broadcasting variables
   (sending small datasets to all worker nodes) or using accumulators (efficiently summing values across partitions) can minimize shuffling overhead.
   -  Tuning your spark configuration to a right shuffle partition count is very important,
	-  Let's say I have a very small dataset and I decide to do a groupBy with the default shuffle partition count 200. In this case, I might overkill my spark resources with       too many partitions. In another case, I have a very huge dataset, and performing a groupBy with the default shuffle partition count.
      In this case, I might under    utilize my spark resources.
8. Tungsten Engine and Catalyst Optimizer: Spark utilizes built-in optimizers like Tungsten for memory management and Catalyst for query optimization.
   These optimizers improve code generation, memory usage, and overall execution plans.
9. Monitoring and Profiling: Tools like Spark UI and profiler libraries help identify bottlenecks and areas for improvement in your Spark applications.
10. Predicate pushdown
    -   Use .filter to filter out the data at the dataset level only before joining
11.Broadcast joins- Using broadcast hints - Broadcast joins are used whenever we need to join a larger dataset with a smaller dataset.
When we use broadcast join spark broadcasts the smaller dataset to all nodes in the cluster since the data to be joined is
available in every cluster nodes, spark can do a join without any shuffling. Using this broadcast join you can avoid sending huge
loads of data over the network and shuffling. Using the explain method we can validate whether the data frame is broadcasted or not. The below example illustrated how        broadcast join is done.
		df1 = spark.read.parquet("file1")
		df2 = spark.read.parquet("file2")
		broadcast_join = df1.join(F.broadcast(df2),"id")
12. Use serialized dataformats while storing the data
		a. Avro - schema attached to the data
		b. Parquet - columnar file format
		c. Orc  - columnar file format
		d. Kryo serialization
13. Spark jobs run as a pipeline where one Spark job writes data into a File and another Spark jobs read the data, process it, and writes to another file for another Spark job to pick up. When you have such use case, prefer writing an intermediate file in Serialized and optimized formats like Avro, Kryo, Parquet e.t.c, any transformations on these formats performs better than text, CSV, and JSON.
14. Avoid UDFs

Spark configuration settings for optimization

1. Use columnar data while caching - When you perform Dataframe/SQL operations on columns, Spark retrieves only required columns which result in fewer data retrieval and less memory usage.
	a. spark.conf.set("spark.sql.inMemoryColumnarStorage.compressed", true)
2. Use optimal value for spark.sql.shuffle.partitions If you have huge data then you need to have higher number and if you have smaller dataset have it lower number.
3. Use broadcast joins when your data can fit inmemory
   -	This strategy can be used only when one of the joins tables small enough to fit in memory within the broadcast threshold.
	spark.conf.set("spark.sql.autoBroadcastJoinThreshold",10485760)//100 MB by default
	Spark 3.0 - Enable Adaptive Query Execution
4. Used kryoserializer for better optimization
5. Use DataFrame/Dataset over RDD
6.  Use coalesce() over repartition()
7.  Use mapPartitions() over map()
8.  Use Serialized data format’s
9.  Avoid UDF’s (User Defined Functions)
10.  Caching data in memory
11.  Reduce expensive Shuffle operations
12.  Disable DEBUG & INFO Logging
