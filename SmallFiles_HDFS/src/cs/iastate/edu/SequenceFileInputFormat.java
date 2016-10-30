/**
 * 
 */
package cs.iastate.edu;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * @author hduser
 * @desc Custom class that overrides FileInputFormat, processes file content
 */
public class SequenceFileInputFormat extends FileInputFormat<NullWritable, BytesWritable>{
	
	@Override
	public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskContext) throws IOException, InterruptedException {
		
		// create an instance of SequenceFileRecordReader and initialize it with current file content to be processed by reader
		SequenceFileRecordReader sfrr = new SequenceFileRecordReader();
		sfrr.initialize(inputSplit, taskContext);
		return sfrr;
	}
	
	@Override
	public boolean isSplitable(JobContext jobContext, Path filePath) {
		// we are not using file splitting, hence set it to return false
		return false; 
	}
	
}
