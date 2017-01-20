import java.io.IOException;
import org.apache.hadoop.io.IntWritable; import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BikeTripReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {		

		int sum = 0;
		for (IntWritable count : values) {
			sum += count.get();
		}
		
		if(key.toString().contains("Thu") || key.toString().contains("Fri")) {
			if(sum/(2*5) < 0) {
				context.write(key, new IntWritable(sum/(2*5)));
			}
		} else {
			if(sum/(2*4) < 0) {
				context.write(key, new IntWritable(sum/(2*4)));
			}
		}
	}

}