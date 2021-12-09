package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
{
    private TextView tvCityName1, tvCityName2, tvCityName3;
    private TextView tvCityTemp1, tvCityTemp2, tvCityTemp3;
    private TextView tvMainWeather1, tvMainWeather2, tvMainWeather3;
    private EditText etLat, etLong;
    private Button btnGetWeather, btnRefresh;
    private ImageView iv1, iv2, iv3;
    private TextView tvDate1, tvDate2, tvDate3;
    private TextView tvTime1, tvTime2, tvTime3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tvCityName1 = findViewById(R.id.tvName1);
        tvCityName2 = findViewById(R.id.tvName2);
        tvCityName3 = findViewById(R.id.tvName3);
        tvCityTemp1 = findViewById(R.id.tvTemp1);
        tvCityTemp2 = findViewById(R.id.tvTemp2);
        tvCityTemp3 = findViewById(R.id.tvTemp3);
        tvMainWeather1 = findViewById(R.id.tvWeather1);
        tvMainWeather2 = findViewById(R.id.tvWeather2);
        tvMainWeather3 = findViewById(R.id.tvWeather3);
        etLat = findViewById(R.id.etLat);
        etLong = findViewById(R.id.etLong);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        btnRefresh = findViewById(R.id.btnRefresh);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        tvDate1 = findViewById(R.id.tvDate);
        tvDate2 = findViewById(R.id.tvDate2);
        tvDate3 = findViewById(R.id.tvDate3);
        tvTime1 = findViewById(R.id.tvTime1);
        tvTime2 = findViewById(R.id.tvTime2);
        tvTime3 = findViewById(R.id.tvTime3);

        btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent reload = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(reload);
            }
        });

        btnGetWeather.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String lat = etLat.getText().toString();
                String lon = etLong.getText().toString();

                double latDouble = Double.parseDouble(lat);
                double lonDouble = Double.parseDouble(lon);

                WeatherInfo weatherInfo = new WeatherInfo(MainActivity.this);

                // execute
                weatherInfo.execute(latDouble, lonDouble);
            }
        });
    }

    public static void setInformation(TextView name, TextView temp, TextView weather, CityInfo city, ImageView iv, TextView date, TextView time)
    {
        name.setText(city.getCityName());
        temp.setText(city.getTemperature() + "°F");
        weather.setText(city.getWeatherDescription());
        date.setText(city.getDate().format(city.getDt()));
        time.setText(city.getTime().format(city.getDt()) + " EST");

        if(city.getWeatherDescription().contains("cloud"))
        {
            iv.setImageResource(R.drawable.cloud);
        }
        else if(city.getWeatherDescription().contains("clear"))
        {
            iv.setImageResource(R.drawable.clear);
        }
        else if(city.getWeatherDescription().contains("sunny"))
        {
            iv.setImageResource(R.drawable.sunny);
        }
        else if(city.getWeatherDescription().contains("fog"))
        {
            iv.setImageResource(R.drawable.fog);
        }
        else if(city.getWeatherDescription().contains("snow"))
        {
            iv.setImageResource(R.drawable.snow);
        }
        else if(city.getWeatherDescription().contains("mist"))
        {
            iv.setImageResource(R.drawable.mist);
        }
        else if(city.getWeatherDescription().contains("rain"))
        {
            iv.setImageResource(R.drawable.rain);
        }
    }

    // First parameter is Double. I pass an array of Double values, which are the input latitude and longitude coordinates. The third parameter, ArrayList,
    // is what the doInBackground method returns to the onPostExecute method.
    class WeatherInfo extends AsyncTask<Double, Void, ArrayList<CityInfo>>
    {
        private ProgressDialog dialog;
        private static final String API_KEY = "8d33d9bdf87f01dbb88b843fb0b82bd0";

        public WeatherInfo(MainActivity mainActivity)
        {
            dialog = new ProgressDialog(mainActivity);
        }

        @Override
        protected ArrayList<CityInfo> doInBackground(Double... coordinates)
        {
            HttpURLConnection httpURLConnection = null;
            InputStream contentStream = null;
            Scanner scanner = null;
            ArrayList<CityInfo> cities = new ArrayList<CityInfo>();
            try
            {
                String apiURL = "http://api.openweathermap.org/data/2.5/find?lat=" +coordinates[0]+"&lon=" +coordinates[1]+"&cnt=3&appid="+API_KEY+"&units=imperial";

                //create new URL object from apiURL String
                URL url = new URL(apiURL);

                //open url connection; form connection to "server"
                httpURLConnection = (HttpURLConnection) url.openConnection();

                //read the output the API returns as an InputStream
                contentStream = (InputStream) httpURLConnection.getContent();

                //read the InputStream into a Scanner
                scanner = new Scanner(contentStream);

                //allows Scanner to read multiple lines in one go by specifying delimiter as new line (\A)
                scanner.useDelimiter("\\A");

                //read data from Scanner as a string
                String weatherInfoJSONStr = "";
                if(scanner.hasNext())
                {
                    weatherInfoJSONStr = scanner.next();
                }
                else
                {
                    scanner.next();
                }

                Log.d("TAG_MESSAGE", weatherInfoJSONStr);
                JSONObject weatherInfoJSONObject = new JSONObject(weatherInfoJSONStr);
                JSONArray citiesInfoJSONArray = weatherInfoJSONObject.getJSONArray("list");

                //parsing using org.JSON
                for(int i = 0; i < citiesInfoJSONArray.length(); i++)
                {
                    JSONObject cityInfoJSONObject = citiesInfoJSONArray.getJSONObject(i);
                    String cityName = cityInfoJSONObject.getString("name");

                    JSONObject cityCoordJSONObject = cityInfoJSONObject.getJSONObject("coord");
                    double latitude = cityCoordJSONObject.getDouble("lat");
                    double longitude = cityCoordJSONObject.getDouble("lon");

                    JSONObject cityMainJSONObject = cityInfoJSONObject.getJSONObject("main");
                    double temperature = cityMainJSONObject.getDouble("temp");

                    long cityDT = cityInfoJSONObject.getLong("dt");
                    Date dt = new Date(cityDT * 1000);
                    SimpleDateFormat date = new SimpleDateFormat("MM/dd/yy");
                    SimpleDateFormat tnbnjjime = new SimpleDateFormat("hh:mm aa");

                    JSONArray cityWeatherJSONArray = cityInfoJSONObject.getJSONArray("weather");
                    JSONObject cityWeatherJSONObject = cityWeatherJSONArray.getJSONObject(0);
                    String mainWeather = cityWeatherJSONObject.getString("main");
                    String weatherDescription = cityWeatherJSONObject.getString("description");

                    CityInfo cityInfo = new CityInfo(latitude, longitude, cityName, temperature, mainWeather, weatherDescription, dt, date, time);
                    cities.add(cityInfo);
                }
            }
            catch (JSONException | IOException e)
            {
                Log.d("LOG", e.toString());
            }
            finally
            {
                try
                {
                    if(scanner != null)
                    {
                        scanner.close();
                    }
                    if(contentStream != null)
                    {
                        contentStream.close();
                    }
                }
                catch (IOException e)
                {

                }

                if(httpURLConnection != null)
                {
                    httpURLConnection.disconnect();
                }
            }
            return cities;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setMessage("Fetching weather, please wait...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<CityInfo> cities)
        {
            super.onPostExecute(cities);
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e)
            {

            }
            dialog.dismiss();

            CityInfo city1 = cities.get(0);
            setInformation(tvCityName1, tvCityTemp1, tvMainWeather1, city1, iv1, tvDate1, tvTime1);

            CityInfo city2 = cities.get(1);
            setInformation(tvCityName2, tvCityTemp2, tvMainWeather2, city2, iv2, tvDate2, tvTime2);

            CityInfo city3 = cities.get(2);
            setInformation(tvCityName3, tvCityTemp3, tvMainWeather3, city3, iv3, tvDate3, tvTime3);
        }
    }
}