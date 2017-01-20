import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;
public class SpeedReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	@Override
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		double sum = 0, count = 0; 
		for (DoubleWritable value : values) {
			count++;
	     	sum += value.get();
	    }
	    double res = sum / count;
	    if (res <= 10.0) {
	    	context.write(key, new DoubleWritable(sum / count)); 	
	    }	
	}
}
