package cs.iastate.edu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 
 * @author hduser
 * @desc Main class that extends the Tool and defines the run configuration
 */
public class ConvertSmallFileToSequenceFile extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		
		//Run the tool with its configuration and input, output files
		ToolRunner.run(new ConvertSmallFileToSequenceFile(), args);
	}

	@Override
	public int run(String[] args) throws Exception {
		
		Configuration seqConf = new Configuration();
		
		// create a new Job instance, set the job name and class
		Job job = Job.getInstance(seqConf);
		job.setJobName("SmallFilesToSequenceFile");
		job.setJarByClass(ConvertSmallFileToSequenceFile.class);
		
		// Set the mapper class and number of reducers
		job.setMapperClass(SequenceMapper.class);
		job.setNumReduceTasks(1);
		
		// Set the input format class, output format class, key and value classes for reducer output
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);
		
		// Initiate the paths of input and output files. args[0] is input file location and args[1] is output file location
		FileInputFormat.setInputPaths(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    //Submit the job to cluster and wait for the job to finish
		if(job.waitForCompletion(true))	return 0;
		else	return 1;
	}

}
