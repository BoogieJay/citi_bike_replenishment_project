import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class StationDriver {

  public static void main(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.printf("Usage: Driver <input dir> <output dir>\n");
      System.exit(-1);
    }

    Job job = new Job();
	job.setJarByClass(StationDriver.class);
	job.setJobName("Station Driver");

	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));

	job.setMapperClass(StationMapper.class);
	job.setReducerClass(StationReducer.class);

	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(DoubleWritable.class);

    boolean success = job.waitForCompletion(true);
    System.exit(success ? 0 : 1);
  }
}

