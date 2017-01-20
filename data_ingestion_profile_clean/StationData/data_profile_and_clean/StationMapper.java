import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.*;

public class StationMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  try {
		JSONObject json = new JSONObject(value.toString());
		
		int statusKey = (int)json.get("statusKey");
		if (statusKey == 1){
			StringBuilder inputKey = new StringBuilder();
			
			inputKey.append(json.get("day"));
			inputKey.append(" ");
			
			inputKey.append(json.get("time"));
			inputKey.append(" ");
			
			inputKey.append(json.get("id"));
			inputKey.append(" ");
			
			double latitude = (double) json.get("latitude");
			latitude = Math.floor(latitude * 100000) / 100000;
			inputKey.append(latitude);
			inputKey.append(" ");
			
			double longitude = (double) json.get("longitude");
			longitude = Math.floor(longitude * 100000) / 100000;
			inputKey.append(longitude);
			inputKey.append(" ");
			
			int totalDocks = (int) json.get("totalDocks");
			inputKey.append(String.valueOf(totalDocks));
			
			int availableBikes = (int) json.get("availableBikes");
			double percent = (double)availableBikes / totalDocks;
			
			if (percent < 0.2){
				context.write(new Text(inputKey.toString()), new DoubleWritable(percent));
			}
		}
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	  
  }
}
