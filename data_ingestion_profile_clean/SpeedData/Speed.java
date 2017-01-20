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
public class Speed {
	public static void main(String[] args) throws Exception { 
		if (args.length != 2) {
      		System.err.println("Usage: Word Count <input path> <output path>");
      		System.exit(-1);
    	}
		Job job = new Job(); 
		job.setJarByClass(Speed.class); 
		job.setNumReduceTasks(1);
		job.setJobName("Speed");
		FileInputFormat.addInputPath(job, new Path(args[0])); 
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    job.setMapperClass(SpeedMapper.class);
		job.setReducerClass(SpeedReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

