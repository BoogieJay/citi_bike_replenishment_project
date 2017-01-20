import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by BoogieJay on 1/29/16.
 */

public class Run {

    public static void main(String[] args) throws Exception {

        String lastExcutionTime = "";

        //Calculate what the day today
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();
        String dowStr = dow.toString();
        String day = dowStr.substring(0, 1).toUpperCase() + dowStr.substring(1,3).toLowerCase();

        //Calculate the Time(Morning or Night or Noon)
        String time = "";
        if (day.equals("Sat") || day.equals("Sun")){
            time = "Noon";
        } else {
            Calendar calendar = GregorianCalendar.getInstance();
            int currTime = calendar.get(Calendar.HOUR_OF_DAY);
            time = currTime > 12 ? "Night" : "Morning";
        }

        while(true){
            JSONObject page = DataIngestion.readJsonFromUrl("https://feeds.citibikenyc.com/stations/stations.json");
            String currTime = page.getString("executionTime");
            if (currTime.equals(lastExcutionTime)){
                Thread.currentThread().sleep(1000 * 60 * 5);
                continue;
            }
            lastExcutionTime = currTime;

            JSONArray stationInfo = (JSONArray) page.get("stationBeanList");
            FileWriter fw = new FileWriter("stationData.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);

            System.out.println("Begin a new page and num of station is " + stationInfo.length());
            for (int i = 0; i < stationInfo.length(); i++){
                JSONObject currObj = stationInfo.getJSONObject(i);
                currObj.accumulate("day", day);
                currObj.accumulate("time", time);
                writer.write(currObj.toString());
                writer.newLine();
            }
            writer.close();
        }
    }

}