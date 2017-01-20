import java.io.IOException;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;
public class SpeedMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    @Override
    /*
    * input: newData: 4616337|Manhattan|11th ave n ganservoort - 12th ave @ 40th st|Mon 2016-11-07 10:59:35|9.94|526|40.74047,-74.00925 40.74137,-74.00893 40.74317,-74.00859 40.74623,-74.00797 40.74812,-74.00765 40.7487,-74.00769 40.74971,-74.00819 40.75048,-74.00832 40.75161,-74.00789 40.75375,-74.00704 40.75721,-74.00463 40.76003,-74.00263 40.76074,-74.00208|
    */
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split("\\|");
        String[] points = words[6].split(" ");
        String[] datetime = words[3].split(" ");
        String time = null;
        int hour = Integer.valueOf(datetime[2].split(":")[0]);
        if (datetime[0].equals("Sat") || datetime[0].equals("Sun")) { // this day is Weekend
        	if (hour == 12 || hour == 13) {
        		time = "Noon";
        	}
        } else { // this day is Weekday
	        if (hour == 8 || hour == 9 || hour == 10) {
	        	time = "Morning";
	        } else if (hour == 17 || hour == 18) {
	        	time = "Night";
	        }
        }
        if (time != null) {
	        for (String point : points) { // point format: 40.74047,-74.00925
	        	context.write(new Text(point + " " + datetime[0] + " " + time), new DoubleWritable(Double.valueOf(words[4])));
	        }        	
        }
    }
}
