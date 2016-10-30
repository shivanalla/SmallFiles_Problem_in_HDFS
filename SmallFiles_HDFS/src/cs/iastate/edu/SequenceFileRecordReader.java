/**
 * 
 */
package cs.iastate.edu;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * @author hduser
 * @desc Custom RecordReader class that reads and processes the fileContent
 */
public class SequenceFileRecordReader extends RecordReader<NullWritable, BytesWritable> {
	
	private FileSplit fileSplit;	// fileSplit instance
	private Configuration seqConf;	// configuration instance
	private BytesWritable fileContent = new BytesWritable();	// used to maintain the current value of the files read
	private boolean isProcessed = false;	// used to maintain status
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub		
	}

	@Override
	public NullWritable getCurrentKey() throws IOException,
			InterruptedException {
		// Return an instance of NullWritable
		return NullWritable.get();
	}

	@Override
	public BytesWritable getCurrentValue() throws IOException,
			InterruptedException {
		// return current file content
		return fileContent;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// return the status of the task, 1 if completed, 0 if not
		if(isProcessed)
			return 1.0f;
		else 
			return 0.0f;
	}

	@Override
	public void initialize(InputSplit fileSplit, TaskAttemptContext taskContext) throws IOException, InterruptedException {
		// Initialize fileSplit and configuration details for the current reader instance 
		this.fileSplit = (FileSplit) fileSplit;
		this.seqConf = taskContext.getConfiguration();
		
	}
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		//read the file content only if it has not been read yet by any other task
		if(!isProcessed) {
			//Get the filePath
			Path filePath = fileSplit.getPath();
			FileSystem fileSystem = filePath.getFileSystem(seqConf);
			FSDataInputStream inStream;
			
			// Get the length of the current content
			int contentLength = (int) fileSplit.getLength();
			byte[] splitContent = new byte[contentLength];
			
			//Open a connection to the stream and read the content entirely
			inStream = fileSystem.open(filePath);
			IOUtils.readFully(inStream, splitContent, 0, contentLength);
			fileContent.set(splitContent, 0, contentLength);
			
			//Close the stream after reading
			IOUtils.closeStream(inStream);
			
			//Set isProcessed to true
			isProcessed = true;
			return true;
		}
		return false;
	}
	
}
