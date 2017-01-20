import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class BikeTripMapper extends Mapper<LongWritable, Text, Text, IntWritable> {     
    
	//private IntWritable mapValue = new IntWritable(1);  
    	
	@Override public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] values = line.split(",");
		
		if(!(values[3].contains("start station id") && values[7].contains("end station id")&&
			values[1].contains("starttime")&&values[2].contains("stoptime"))){
			String startStationID = values[3];
			String endStationID = values[7];
			
			String startTime = values[1];
			String stopTime = values[2];
			String startTimeHour = startTime.split(" ")[1].split(":")[0];
			String stopTimeHour = stopTime.split(" ")[1].split(":")[0];
			//get the hour
			int startHour = Integer.parseInt(startTimeHour);
			int stopHour = Integer.parseInt(stopTimeHour);
			
			if(getWeek(startTime)=="SUN"|| getWeek(startTime) == "SAT" || getWeek(stopTime)=="SUN"|| getWeek(stopTime) == "SAT"){
				if (13 <= startHour&&startHour <= 14 || 13 <= stopHour&& stopHour<= 14 ) { 
					context.write(new Text(startStationID+" "+getWeek(startTime) + " Noon "), new IntWritable(-1));
					context.write(new Text(endStationID+" "+getWeek(stopTime) + " Noon "), new IntWritable(1));			
				}
			} else {
				if (8 <= startHour&&startHour <= 9 ||8 <= stopHour&& stopHour <= 9 ) { 
					context.write(new Text(startStationID+" "+getWeek(startTime) + " Morning "), new IntWritable(-1));
					context.write(new Text(endStationID+" "+getWeek(stopTime) + " Morning "), new IntWritable(1));			
				} 
			
				if(17 <= stopHour&&stopHour <= 18 || 17 <= startHour&&startHour <= 18) {
					context.write(new Text(startStationID+" "+getWeek(startTime) + " Night "), new IntWritable(-1));
					context.write(new Text(endStationID+" "+getWeek(stopTime) + " Night "), new IntWritable(1));
				}  
			}
		}   	
	}

	public static String getWeek(String str_date) {
		
	    	try {
				int mydate = 0;
				String week = null;
		    	DateFormat formatter;
		    	formatter = new SimpleDateFormat("MM/dd/yy HH:mm");
		    	Date date = (Date) formatter.parse(str_date);
		    	//java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

		    	Calendar c = Calendar.getInstance();
				c.setTime(date);
				mydate = c.get(Calendar.DAY_OF_WEEK);
				
				if (mydate == 1) {
					week = "Sun";
				} else if (mydate == 2) {
					week = "Mon";
				} else if (mydate == 3) {
					week = "Tue";
				} else if (mydate == 4) {
					week = "Wed";
				} else if (mydate == 5) {
					week = "Thu";
				} else if (mydate == 6) {
					week = "Fri";
				} else if (mydate == 7) {
					week = "Sat";
				}
				return week;
		   } catch (ParseException e) {
		    	System.out.println("Exception :" + e);
		    	return null;
		   }
		
	}
	
}