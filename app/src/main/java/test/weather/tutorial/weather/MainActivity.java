package test.weather.tutorial.weather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;


public class MainActivity extends Activity {

    private ListView listViewParent = null;
    private List<EachWeatherItem> weatherDataList;
    private int index, subIndex;
    private JSONWeatherTask jsonWeatherTask;

    private class JSONWeatherTask extends AsyncTask<Void, Void, List<EachWeatherItem>> {

        @Override
        protected List<EachWeatherItem> doInBackground(Void... params) {

            try {

               // List<EachWeatherItem> weatherData = MainWeatherRetriever.getAllWeatherData();

                List<EachWeatherItem> weatherData = MainWeatherRetriever.getTestDemo();

                return weatherData;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EachWeatherItem> weatherResult){
            super.onPostExecute(weatherResult);

            weatherDataList = weatherResult;

            CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(MainActivity.this, R.layout.row_layout, weatherDataList);

            //adapter assigned to ListView Object
            listViewParent.setAdapter(customListViewAdapter);
        }
    }

    private class CustomListViewAdapter extends ArrayAdapter<EachWeatherItem>{

        Context context;
        public CustomListViewAdapter(Context context, int resourceID, List<EachWeatherItem> items){
            super(context, resourceID, items);
            this.context = context;
        }

        private class ViewHolder{
            public TextView item;
            public TextView subItem;
        }

        // inflate the layout for each row and assign the data to the individual views in the row
        public View getView(int position, View childView, ViewGroup parentView){

            ViewHolder viewHolder = null;
            EachWeatherItem eachWeatherItem = getItem(position);

            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if(childView == null){
                childView = mInflator.inflate(R.layout.row_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.item = (TextView) childView.findViewById(R.id.textView);
                viewHolder.subItem = (TextView) childView.findViewById(R.id.textView2);
                childView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) childView.getTag();
            }

            viewHolder.item.setText(eachWeatherItem.getPlaceName());
            viewHolder.subItem.setText(eachWeatherItem.getTemperatureData());

            return childView;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewParent =  (ListView)findViewById(R.id.listViewPrimary);

        jsonWeatherTask = new JSONWeatherTask();

    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonWeatherTask.execute();


        listViewParent.post(new Runnable() {
            @Override
            public void run() {

                if (weatherDataList != null) {


                    listViewParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Yo!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });


                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
