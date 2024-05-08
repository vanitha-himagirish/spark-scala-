Following are the optimization techniques:

1. API Selection: Compared to RDDs , Dataframes and Spark SQL APIs offers higher levels of abstractions and increases the performance.
2. Data Serializtion: Choosing Kryo serialization over java serialization
3. Caching and persistance :
4. Partitioning and Bucketing
5. Shuffle Optimization: Spark shuffle operations involve data movement across the network. Techniques like broadcasting variables
   (sending small datasets to all worker nodes) or using accumulators (efficiently summing values across partitions) can minimize shuffling overhead.
6. Tungsten Engine and Catalyst Optimizer: Spark utilizes built-in optimizers like Tungsten for memory management and Catalyst for query optimization.
   These optimizers improve code generation, memory usage, and overall execution plans.
7. Monitoring and Profiling: Tools like Spark UI and profiler libraries help identify bottlenecks and areas for improvement in your Spark applications.
