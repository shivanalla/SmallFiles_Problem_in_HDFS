/**
 * 
 */
package cs.iastate.edu;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * @author hduser
 * @desc Custom Mapper that extends Mapper class and provides definitions for mapper functions
 */
public class SequenceMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable>{
	private Text fileName;
	
	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		
		InputSplit split = context.getInputSplit();
		
		// Get the filePath and retrieve the fileName
		Path filePath = ((FileSplit) split).getPath();
		fileName = new Text(filePath.toString());
	}
	
	@Override
	public void map(NullWritable key, BytesWritable fileContent, Context context) throws IOException, InterruptedException {
		// key is the fileName and value is the fileContent
		context.write(fileName, fileContent);
	}
}
