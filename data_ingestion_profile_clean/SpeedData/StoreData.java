import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Created by Wei Shi on 11/5/16.
 */
public class StoreData {
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost/trafficSpeed";
//    static final String USER = "root";
//    static final String PASS = "shiwei";
    public static void main(String[] args) {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            System.out.println("Database connected!");
//        } catch (SQLException e) {
//            throw new IllegalStateException("Cannot connect the database!", e);
//        }


        try {
            StringBuilder str = new StringBuilder();
            BufferedWriter bw = new BufferedWriter(new FileWriter("newData.txt", true));
            URL text = new URL("http://dotsignals.org/nyc-links-cams/TrafficSpeed.php");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(text.openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("TrafficSpeedData");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Statement query = connection.createStatement();
                    Element eElement = (Element) nNode;
                    int linkId = Integer.valueOf(eElement.getAttribute("linkId"));
                    String linkName = eElement.getAttribute("linkName");
                    String linkBorough = eElement.getAttribute("linkBorough");
                    double linkSpeed = Double.valueOf(eElement.getAttribute("linkSpeed"));
                    int linkTravelTime = Integer.valueOf(eElement.getAttribute("linkTravelTime"));
                    String linkTimeStamp = processDateTime(eElement.getAttribute("linkTimeStamp"));
                    String linkPoints = decodePoly(eElement.getAttribute("linkPolyline"));
//                    if (linkPoints.length() > 300) continue;
//                    String sql = "INSERT INTO info VALUES (" + linkId + ",'" + linkName + "','" +
//                            linkBorough + "'," + linkSpeed + "," + linkTravelTime + ",'" +
//                            linkTimeStamp + "','" + linkPoints + "');";
//                    try {
//                        query.executeUpdate(sql);
//                    } catch (SQLException e) {
//                        continue;
//                    }
                    String tmp = new String(linkId + "|" + linkBorough + "|" + linkName + "|" +
                            linkTimeStamp +  "|" + linkSpeed + "|" + linkTravelTime + "|" + linkPoints + "\n");
//                    str.append(linkId + "|" + linkName + "|" + linkBorough +
//                            "|" + linkSpeed + "|" + linkTravelTime + "|" + linkPoints + "\n");
                    bw.write(tmp);
                }
            }
            bw.close();
        } catch (MalformedURLException mfe) {
            mfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException saxe) {
            saxe.printStackTrace();
        }

    }

    private static String decodePoly(String encoded) {
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        StringBuilder sb = new StringBuilder();
        while (index < len) {
            try {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                sb.append(lat / 1E5 + "," + lng / 1E5 + " ");
            } catch (StringIndexOutOfBoundsException e) {
                continue;
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String processDateTime(String str) {
        String res = null;
        java.util.Date date;
        try {
            DateFormat df = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
            date = df.parse(str);
            res = new SimpleDateFormat("EE yyyy-MM-dd HH:mm:ss").format(date);
        }
        catch (DateTimeParseException | ParseException exc) {
            System.out.printf("%s is not parsable!%n", str);
        }
        return res;
    }
}
