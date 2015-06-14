package test.weather.tutorial.weather;

import android.location.Location;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 6/13/2015.
 */
public class MainWeatherRetriever {

    private static String[] geoLocation = {"Orlando", "Tampa"};

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static JSONObject jsonWeatherObject = null;
    private static JSONArray jsonArray = null;

    private static Location location = null;


    private static String getWeatherData(String location){

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{

            httpURLConnection = (HttpURLConnection) (new URL(BASE_URL+location)).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String eachLine = null;
            while((eachLine = bufferedReader.readLine())!=null){
                stringBuffer.append(eachLine+"\r\n");
            }

            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuffer.toString();

        }catch(Throwable t){
            t.printStackTrace();
        }finally {
            try{inputStream.close();}catch(Throwable t){}
            try{
                httpURLConnection.disconnect();}catch(Throwable t){}
        }

        return null;
    }

    private static String JSONWeatherParser(String data) throws JSONException {
        jsonWeatherObject = new JSONObject(data);

        return jsonWeatherObject.getString("name");//+"#"+jsonWeatherObject.getString("temp");

    }


    public static List<EachWeatherItem> getAllWeatherData() throws JSONException {


        //jsonWeatherObject = jsonArray.getJSONObject(0);

        List<EachWeatherItem> weatherItemsList = new ArrayList<EachWeatherItem>();

        for(int i=0; i<geoLocation.length; i++){

            String weatherData = getWeatherData(geoLocation[i]);
            //String[] dataWeather = JSONWeatherParser(weatherData).split("#", 2);
            EachWeatherItem items = new EachWeatherItem(JSONWeatherParser(weatherData), null);
            weatherItemsList.add(items);
        }

        return null;
    }

    public static List<EachWeatherItem> getTestDemo(){

         List<EachWeatherItem> weatherItemsList = new ArrayList<EachWeatherItem>();

         weatherItemsList.add((new EachWeatherItem("Rome, IL", "Temperature: 30C")));
         weatherItemsList.add((new EachWeatherItem("Milan, IL", "Temperature: 30C")));
         return weatherItemsList;
    }

}
