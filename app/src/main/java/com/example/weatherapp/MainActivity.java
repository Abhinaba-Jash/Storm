package com.example.weatherapp;

import static android.content.ContentValues.TAG;

import static androidx.core.view.ViewCompat.setBackground;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private TextView latitude_text,longitude_text,day, date,country,description,temperature,max_temp,min_temp,pressure,humidity,wind,sun;
    ImageView imageView;
    private View view;
    List<ForecastData> data;
    Button forecastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int colorId=getResources().getColor(R.color.white);
        ViewGroup mContainer = (ViewGroup) findViewById(android.R.id.content).getRootView();
        latitude_text=findViewById(R.id.latitude_text);
        longitude_text=findViewById(R.id.longitude_text);
        day=findViewById(R.id.day);
        date=findViewById(R.id.date);
        country=findViewById(R.id.country);
        description=findViewById(R.id.description);
        temperature=findViewById(R.id.temperature);
        max_temp=findViewById(R.id.max_temp);
        min_temp=findViewById(R.id.min_temp);
        humidity=findViewById(R.id.humidity);

        pressure=findViewById(R.id.pressure);

        wind=findViewById(R.id.wind);

        sun=findViewById(R.id.sun);
        imageView=findViewById(R.id.image);
forecastButton=findViewById(R.id.forecastButton);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAppFontColor(mContainer,colorId);
        double[] store = getLocation(view);

        getData(store);



    }

    private void getData(double[] store) {

        double lat = store[0];
        double lon = store[1];
       String lat_st=Double.toString(lat);
        String lon_st=Double.toString(lon);
        String Api_key="3d149737b2d4986d9aace68e2efe5a17";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat_st+"&lon="+lon_st+"&appid="+Api_key;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                JSONObject sys= null;
                try {
                    sys = response.getJSONObject("sys");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject windJson= null;
                try {
                    windJson = response.getJSONObject("wind");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject main= null;
                try {
                    main = response.getJSONObject("main");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONArray weatherArray= null;
                try {
                    weatherArray = response.getJSONArray("weather");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JSONObject weather= null;
                try {
                    weather = weatherArray.getJSONObject(0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JSONObject coord= null;
                try {
                    coord = response.getJSONObject("coord");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String long_txt= null;
                try {
                    long_txt = coord.getString("lon");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                longitude_text.setText("Lon :"+long_txt);
                String lat_txt= null;
                try {
                    lat_txt = coord.getString("lat");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                latitude_text.setText("Lat :"+lat_txt);

                try {
                    country.setText(response.getString("name"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    description.setText(weather.getString("description"));


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String id=weather.getString("id");
                    LocalDateTime now = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        now = LocalDateTime.now();
                    }

                    // Format the date and time using a 24-hour format pattern
                    DateTimeFormatter formatter = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        formatter = DateTimeFormatter.ofPattern("HH");
                    }

                    // Get the current hour in 24-hour format as a string
                    String currentHour=null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        currentHour = now.format(formatter);
                    }
                    setBackground(id,currentHour);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double t=Double.parseDouble(main.getString("temp"));
                    int temp=(int)t;
                    temp=temp-273;
                    temperature.setText(temp +"°C");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double maxi=Double.parseDouble(main.getString("temp_max"));
                    int tempm=(int)maxi;
                    tempm=tempm-273;
                    max_temp.setText("Max "+tempm+"°C");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    double mini=Double.parseDouble(main.getString("temp_min"));
                    int tempmi=(int)mini;
                    tempmi=tempmi-273;
                    min_temp.setText("Min "+tempmi+"°C");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    humidity.setText("Humidity "+(main.getString("humidity"))+"%");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    pressure.setText("Pressure "+(main.getString("pressure"))+"P");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    wind.setText("Wind Speed "+(windJson.getString("speed"))+"m/s");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String icon=weather.getString("icon");
                    String url="https://openweathermap.org/img/wn/"+icon+"@2x.png";
                    Glide.with(MainActivity.this).load(url).into(imageView);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String sunr=toDate(Long.parseLong(sys.getString("sunrise")));
                    String suns=toDate(Long.parseLong(sys.getString("sunset")));
                    sun.setText("Sunrise :"+sunr+" Sunset :"+suns);
//                    Date dt();
//                    String s;
//                    s.format("%02d:%02d", dt.getHour(), dt.getMinute());
//                    sun.setText("Sunrise "+()+" Sunset "+());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                LocalDate now= null;
                DayOfWeek dayT=null;
                Month month=null;
                int dateT = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalDate.now();
                    dayT=now.getDayOfWeek();
                    month=now.getMonth();
                    dateT=now.getDayOfMonth();
                }
                String monthText=month.toString();
                String dayText=dayT.toString();
                day.setText(dayText);
                String dateText=Integer.toString(dateT);
                
                date.setText(monthText+" "+dateT);
forecastButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,DetailedWeather.class);
        intent.putExtra("lat",lat_st);
        intent.putExtra("lon",lon_st);
        startActivity(intent);
    }
});

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });

        requestQueue.add(json);

    }
    public static String toDate(long time) {
        SimpleDateFormat HMM = new SimpleDateFormat("hh : mm a");
        TimeZone id=TimeZone.getDefault();
        String timezone=id.getDisplayName();
        HMM.setTimeZone(TimeZone.getTimeZone("UTC"));
        final Date date = new Date(time*1000);
        return HMM.format(date);
    }
    private void setBackground(String text,String hour) {
       // Toast.makeText(MainActivity.this, hour, Toast.LENGTH_SHORT).show();
        int hourInt=Integer.parseInt(hour);
        int id=Integer.parseInt(text);
        View screenView;
        screenView=findViewById(R.id.constraintLayout);
        if(id>=200&&id<=232){
            if(isDay(hourInt)){
            screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.thunderstorm));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.thunderstormnight));

            }

        }
        if((id>=300&&id<=321)||(id>=500&&id<=521)){
            if(isDay(hourInt)){
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.showerrain));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.showerrainnight));

            }

        }
        if(id>=600&&id<=622){
            if(isDay(hourInt)){
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.snow));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.snownight));

            }

        }
        if(id>=701&&id<=781){
            if(isDay(hourInt)){
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mist));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.mistnight));

            }

        }
        if(id==800){
            if(isDay(hourInt)){
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clearsky));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clearskynight));

            }

        }
        if(id>=801&&id<=804){
            if(isDay(hourInt)){
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fewclouds));

            }
            else{
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fewclouds1));

            }

        }

    }
    private boolean isDay(int hour){
        if(((hour>=1&&hour<=5)||(hour>=19&&hour<=24))){
            return false;
        }
        return true;
    }
    private void setAppFontColor(ViewGroup mContainer, int color) {
        if (mContainer == null || color == 0)
            return;

        final int mCount = mContainer.getChildCount();

        for (int i = 0; i < mCount; ++i) {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                ((TextView) mChild).setTextColor(color);
            } else if (mChild instanceof Button) {
                ((Button) mChild).setTextColor(color);
            } else if (mChild instanceof ViewGroup) {
                setAppFontColor((ViewGroup) mChild, color);
            }
        }
    }
    public double[] getLocation(View view){
        gpsTracker = new GpsTracker(MainActivity.this);
        double[] store =new double[2];
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            store[0]=latitude;
            store[1]=longitude;

        }else{
            gpsTracker.showSettingsAlert();
        }
        return store;
    }
}
