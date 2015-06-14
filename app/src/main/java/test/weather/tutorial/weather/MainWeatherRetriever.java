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

    private static String[] geoLocation = {"Orlando,US", "Miami,US", "Tampa,US", "Chicago,US", "Houston,US", "Chennai,India", "Hyderabad,india"};

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

            return bufferedReader.toString();

        }catch(Throwable t){
            t.printStackTrace();
        }finally {
            try{inputStream.close();}catch(Throwable t){}
            try{
                assert httpURLConnection != null;}catch(Throwable t){}
        }

        return null;
    }

    private static String JSONWeatherParser(String data) throws JSONException {
        jsonWeatherObject = new JSONObject(data);

        return jsonWeatherObject.getString("name")+"#"+jsonWeatherObject.getString("temp");

    }


    public static List<EachWeatherItem> getAllWeatherData() throws JSONException {


        //jsonWeatherObject = jsonArray.getJSONObject(0);

        List<EachWeatherItem> weatherItemsList = new List<EachWeatherItem>() {
            @Override
            public void add(int location, EachWeatherItem object) {

            }

            @Override
            public boolean add(EachWeatherItem object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends EachWeatherItem> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends EachWeatherItem> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public EachWeatherItem get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Iterator<EachWeatherItem> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<EachWeatherItem> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<EachWeatherItem> listIterator(int location) {
                return null;
            }

            @Override
            public EachWeatherItem remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public EachWeatherItem set(int location, EachWeatherItem object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<EachWeatherItem> subList(int start, int end) {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };

        for(int i=0; i<geoLocation.length; i++){

            String weatherData = getWeatherData(geoLocation[i]);
            String[] dataWeather = JSONWeatherParser(weatherData).split("#", 2);
            EachWeatherItem items = new EachWeatherItem(dataWeather[0], dataWeather[1]);
            weatherItemsList.add(items);
        }

        return null;
    }

}
