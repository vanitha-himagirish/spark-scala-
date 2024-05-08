Difference Between Repartition and Partition By

Repartition :
1. Used for In-Memory partitioning
2. Allows to control over the number of partitions
3. Useful for shuffling of the data across partitions that is useful for operations like joins and aggregations
4. Since it involves shuffling of data, it might be expensive

Partition By:
1. Used for Partitioning the data on disk while writing files like parquet or ORC files that support partitioning
2. Creates subdirectories within the output directory, with each subdirectory containing data for a specific partition value.\
3. Improves query performance
4. Does not shuffle data and does not affect in memory processing

