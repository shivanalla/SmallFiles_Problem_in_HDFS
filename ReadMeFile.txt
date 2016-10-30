Initially set the terminal path to hadoop folder.

To run the program, follow the below steps:

1. First copy a number of small files, into HDFS file system using the below command: (we used large number of ebooks in our case, a few of them are attached in the code as a zip file).
	bin/hadoop dfs -copyFromLocal /Users/hduser/Downloads/SF1/ /Users/hduser/hadoop/SF1
	
2. Next, run the jar file against the small files. The jar included in the code is a runnable jar file. You will need to provide 2 parameters after with the Jar File, the input path to hdfs small files location and the output path where the sequence file can be created.
	bin/hadoop jar SmallFiles.jar /Users/hduser/hadoop/SF1 /Users/hduser/hadoop/out/seq1

Once the command is run, the map-reduce operation starts and finally produce a Sequence File at the output location "/Users/hduser/hadoop/out/seq1".

