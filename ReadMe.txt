Any file which is significantly smaller than Hadoop block size can be named as a small file. We can define that any file less than 75% of Hadoop block size can be categorized as a small file. Default Hadoop block size is 64MB but its trending towards larger block size and can define the block size to be 128MB, 256MB etc.
Small Files can cause a huge bottleneck to the NameNode address space that supports the DataNodes and also consume more system resources and time to perform a Map-Reduce computation. The project work aims at implementing a solution to overcome the Small Files problem. Also, the work provides a comparative evaluation of the NameNode memory space utilized before and after the Small Files issue is addressed. Also, the performance of the Map-Reduce computations are analyzed before and after the Small Files issue is addressed.
The solution approach consists of merging all the small files in a folder to create a larger Sequence file to resolve the Small Files problem in HDFS. The implemented solution is further tested and evaluated using datasets from Project Gutenberg. Various parameters that affect the performance of HDFS file system under the presence of large number of Small Files are discussed in the next section on Experimental Setup and Evaluation.


To run the program, follow the below steps:

Initially set the terminal path to hadoop folder.

1. First copy a number of small files, into HDFS file system using the below command: (we used large number of ebooks in our case, a few of them are attached in the code as a zip file).
	bin/hadoop dfs -copyFromLocal /Users/hduser/Downloads/SF1/ /Users/hduser/hadoop/SF1
	
2. Next, run the jar file against the small files. The jar included in the code is a runnable jar file. You will need to provide 2 parameters after with the Jar File, the input path to hdfs small files location and the output path where the sequence file can be created.
	bin/hadoop jar SmallFiles.jar /Users/hduser/hadoop/SF1 /Users/hduser/hadoop/out/seq1

Once the command is run, the map-reduce operation starts and finally produce a Sequence File at the output location "/Users/hduser/hadoop/out/seq1".

