package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetailedWeather extends AppCompatActivity {
    List<ForecastData> data;
    ProgressBar progressBar;
TextView date,time1,d1,t1,time2,d2,t2,time3,d3,t3,time4,d4,t4,time5,d5,t5,time6,d6,t6,time7,d7,t7,time8,d8,t8,OpenUrl;
ImageView image1,image2,image3,image4,image5,image6,image7,image8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);
        date = findViewById(R.id.date);
        progressBar=findViewById(R.id.progressBar2);
        LocalDate Tdate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Tdate = LocalDate.now();
        }
        OpenUrl = findViewById(R.id.OpenUrl);
        OpenUrl.setText("https://www.accuweather.com");
        OpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DetailedWeather.this,forecast.class);
                intent1.putExtra("openUrl","https://www.accuweather.com/en/in/india-weather");
                startActivity(intent1);
            }
        });
        date.setText("Today (" + Tdate.toString() + ")");
        time1 = findViewById(R.id.time1);
        image1 = findViewById(R.id.image1);
        d1 = findViewById(R.id.d1);
        t1 = findViewById(R.id.t1);
        time2 = findViewById(R.id.time2);
        image2 = findViewById(R.id.image2);
        d2 = findViewById(R.id.d2);
        t2 = findViewById(R.id.t2);
        time3 = findViewById(R.id.time3);
        image3 = findViewById(R.id.image3);
        d3 = findViewById(R.id.d3);
        t3 = findViewById(R.id.t3);
        time4 = findViewById(R.id.time4);
        image4 = findViewById(R.id.image4);
        d4 = findViewById(R.id.d4);
        t4 = findViewById(R.id.t4);
        time5 = findViewById(R.id.time5);
        image5 = findViewById(R.id.image5);
        d5 = findViewById(R.id.d5);
        t5 = findViewById(R.id.t5);
        time6 = findViewById(R.id.time6);
        image6 = findViewById(R.id.image6);
        d6 = findViewById(R.id.d6);
        t6 = findViewById(R.id.t6);
        time8 = findViewById(R.id.time8);
        image8 = findViewById(R.id.image8);
        d8 = findViewById(R.id.d8);
        t8 = findViewById(R.id.t8);
        time7 = findViewById(R.id.time7);
        image7 = findViewById(R.id.image7);
        d7 = findViewById(R.id.d7);
        t7 = findViewById(R.id.t7);
        getData();

    }

    public void getData() {
       data=new ArrayList<>();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Intent intent=getIntent();
        String lat= intent.getStringExtra("lat");
        String lon=intent.getStringExtra("lon");
        String Api_key="3d149737b2d4986d9aace68e2efe5a17";
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&appid="+Api_key;
        //String url="https://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=3d149737b2d4986d9aace68e2efe5a17";
        StringRequest jsonString=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressBar.setVisibility(View.INVISIBLE);
                //Toast.makeText(DetailedWeather.this, response, Toast.LENGTH_SHORT).show();
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
               // Toast.makeText(DetailedWeather.this, json.toString(), Toast.LENGTH_SHORT).show();
                JSONArray list = null;
                try {
                    list = json.getJSONArray("list");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
               // Toast.makeText(DetailedWeather.this, list.toString(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 8; i++) {
                    JSONObject index = null;
                    try {
                        index = list.getJSONObject(i);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    //Toast.makeText(DetailedWeather.this,i+index.toString(), Toast.LENGTH_SHORT).show();
                    JSONObject main = null;
                    try {
                        main = index.getJSONObject("main");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                   // Toast.makeText(DetailedWeather.this, main+main.toString(), Toast.LENGTH_SHORT).show();
//                    JSONObject rain=null;
//                    try {
//                       rain= index.getJSONObject("rain");
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
                   // Toast.makeText(DetailedWeather.this, rain+rain.toString(), Toast.LENGTH_SHORT).show();
//                    JSONObject sys = null;
//                    try {
//                        sys = index.getJSONObject("sys");
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
                    //Toast.makeText(DetailedWeather.this, sys.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray weather = null;
                    try {
                        weather = index.getJSONArray("weather");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    String temp_max = null;
                    try {
                        temp_max = main.getString("temp_max");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String temp_min = null;
                    try {
                        temp_min = main.getString("temp_min");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JSONObject rainCond = null;
                    try {
                        rainCond = weather.getJSONObject(0);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    String description = null;
                    try {
                        description = rainCond.getString("description");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String code = null;
                    try {
                        code = rainCond.getString("icon");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                   // Toast.makeText(DetailedWeather.this, code.toString(), Toast.LENGTH_SHORT).show();
                    String time = null;
                    try {
                        time = index.getString("dt_txt");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                   // Toast.makeText(DetailedWeather.this, time.toString(), Toast.LENGTH_SHORT).show();
                  ForecastData newData = new ForecastData(time, code, description, temp_max, temp_min);
                   data.add(newData);
                   // Toast.makeText(DetailedWeather.this, data.toString(), Toast.LENGTH_SHORT).show();

                }
setData( time1, d1, t1,image1, data,0);
                time1.setText("09:00 A.M");
                setData( time2, d2, t2,image2, data,1);
                time2.setText("12:00 P.M");
                setData( time3, d3, t3,image3, data,2);
                time3.setText("03:00 P.M");
                setData( time4, d4, t4,image4, data,3);
                time4.setText("06:00 P.M");
                setData( time5, d5, t5,image5, data,4);
                time5.setText("09:00 P.M");
                setData( time6, d6, t6,image6, data,5);
                time6.setText("12:00 A.M");
                setData( time7, d7, t7,image7, data,6);
                time7.setText("03:00 A.M");
                setData( time8, d8, t8,image8, data,7);
                time8.setText("06:00 A.M");


            }

            },new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occur

                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        });
               requestQueue.add(jsonString);

    }
    public void setData(TextView time, TextView d, TextView t,ImageView image ,List<ForecastData> myData,int i){
        //time.setText(myData.get(i).getTime());
        d.setText((myData.get(i).getDescription()));
        double t_m=Double.parseDouble(myData.get(i).getTemp_max());
        int maxi=(int)t_m;
        maxi-=273;
        String t_max=Integer.toString(maxi);
        double t_mi=Double.parseDouble(myData.get(i).getTemp_min());
        int mini=(int)t_mi;
        mini-=273;
        String t_min=Integer.toString(mini);
        t.setText(t_max+"°C \\ "+t_min+"°C");
        String iconcode=myData.get(i).getCode();
        String url="https://openweathermap.org/img/wn/"+iconcode+"@2x.png";
        Glide.with(DetailedWeather.this).load(url).into(image);
    }
}