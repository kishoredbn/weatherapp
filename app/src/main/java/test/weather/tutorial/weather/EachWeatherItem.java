package test.weather.tutorial.weather;

/**
 * Created by Administrator on 6/14/2015.
 */
public class EachWeatherItem {
    private String placeName;
    private String temperatureData;

    EachWeatherItem(String placeName, String temperatureData){
        this.placeName = placeName;
        this.temperatureData = temperatureData;
    }

    public String getPlaceName(){
        return this.placeName;
    }

    public String getTemperatureData(){
        return this.temperatureData;
    }
}
