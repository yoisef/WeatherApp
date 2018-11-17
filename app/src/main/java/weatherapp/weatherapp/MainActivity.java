package weatherapp.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weatherapp.weatherapp.models.CurWeather;
import weatherapp.weatherapp.models.ForcWeather;
import weatherapp.weatherapp.models.Forecastday;
import weatherapp.weatherapp.models.Searchlist;

public class MainActivity extends AppCompatActivity {

// initialize UI Components

    TextView cityy, countryy, dataa, conditiontext, temp, tgrba;
    ImageView imgcondition;
    RecyclerView forrecycle;
    RecyclerView.LayoutManager mLayoutManager;
    List<Forecastday> Data;
    List<Searchlist> mysearchlist;
    ArrayList<String> mytrings;
    Spinner myspinner;
    Call<CurWeather> call;
    Call<ForcWeather> callf;
    List<Forecastday> mylistfor;
    mydatabase mydatabasee;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydatabasee=new mydatabase(this);

        mylistfor=new ArrayList<>();

        Data = new ArrayList<>();
        mysearchlist = new ArrayList<>();
        mytrings = new ArrayList<>();
        tgrba = findViewById(R.id.tgrbatext);
        myspinner = findViewById(R.id.citiesspinner);
        cityy = findViewById(R.id.city);
        countryy = findViewById(R.id.country);
        dataa = findViewById(R.id.data);
        conditiontext = findViewById(R.id.textcondition);
        imgcondition = findViewById(R.id.imgcondition);


        temp = findViewById(R.id.temperaturee);

        forrecycle = findViewById(R.id.forcastrec);
        forrecycle.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        forrecycle.setLayoutManager(mLayoutManager);

        // intialize spinner for the 5 cities
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);








        // Change the Weather data of the City was selected

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,  int position, long id) {

                 //call weather data from the api by Retrofit library
                OkHttpClient.Builder builderr = new OkHttpClient.Builder();

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                builderr.addInterceptor(loggingInterceptor);


                Retrofit retrofitt = new Retrofit.Builder()
                        .baseUrl("http://api.apixu.com/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(builderr.build())
                        .build();

                Endpoints myendpoints = retrofitt.create(Endpoints.class);

                call = myendpoints.getcurentweather(myspinner.getSelectedItem().toString());
                call.enqueue(new Callback<CurWeather>() {
                    @Override
                    public void onResponse(Call<CurWeather> call, Response<CurWeather> response) {

                      int i=  myspinner.getSelectedItemPosition();


                        if (response.isSuccessful()) {
                            String coun,citye,data,cond,temper;
                            coun=response.body().getLocation().getCountry();
                            citye=response.body().getLocation().getName();
                            data=response.body().getLocation().getLocaltime();
                            cond=response.body().getCurrent().getCondition().getText();
                            temper=Float.toString(response.body().getCurrent().getTempC()) + "°";
                         switch (i)
                         {
                             case 0:{
                                 mydatabasee.insertdataalex(coun,citye,data,cond,temper);
                                 break;
                             }
                             case 1:{
                                 mydatabasee.insertdatacairo(coun,citye,data,cond,temper);
                                 break;
                             }
                             case 2:{
                                 mydatabasee.insertdatamimia(coun,citye,data,cond,temper);
                                 break;
                             }
                             case 3:{
                                 mydatabasee.insertdataparis(coun,citye,data,cond,temper);
                                 break;
                             }
                             case 4:{
                                 mydatabasee.insertdatachicgo(coun,citye,data,cond,temper);
                                 break;
                             }
                         }






                            cityy.setText(response.body().getLocation().getName());
                            countryy.setText(response.body().getLocation().getCountry());
                            dataa.setText(response.body().getLocation().getLocaltime());
                            conditiontext.setText(response.body().getCurrent().getCondition().getText());
                            temp.setText(Float.toString(response.body().getCurrent().getTempC()) + "°");



                            String imgurl = response.body().getCurrent().getCondition().getIcon();
                            Glide.with(MainActivity.this)
                                    .load("http:" + imgurl)
                                    .into(imgcondition);


                        } else {
                            Toast.makeText(MainActivity.this, "Write correct city name", Toast.LENGTH_LONG).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<CurWeather> call, Throwable t) {

                        int x=  myspinner.getSelectedItemPosition();

                        List<currentobject> mylist;

                      switch (x)
                      {
                          case 0:{
                              mylist=mydatabasee.getdatafromcurrenttalex();
                              cityy.setText(mylist.get(mylist.size()-1).getMycity());
                              countryy.setText(mylist.get(mylist.size()-1).getMycoun());
                              dataa.setText(mylist.get(mylist.size()-1).getMydata());
                              conditiontext.setText(mylist.get(mylist.size()-1).getMycondi());
                              temp.setText(mylist.get(mylist.size()-1).getMytemp());
                              Toast.makeText(MainActivity.this,String.valueOf(mylist.size()),Toast.LENGTH_LONG).show();
                              break;
                          }
                          case 1:{
                              mylist=mydatabasee.getdatafromcurrenttCairo();
                              cityy.setText(mylist.get(mylist.size()-1).getMycity());
                              countryy.setText(mylist.get(mylist.size()-1).getMycoun());
                              dataa.setText(mylist.get(mylist.size()-1).getMydata());
                              conditiontext.setText(mylist.get(mylist.size()-1).getMycondi());
                              temp.setText(mylist.get(mylist.size()-1).getMytemp());
                              Toast.makeText(MainActivity.this,String.valueOf(mylist.size()),Toast.LENGTH_LONG).show();
                              break;
                          }
                          case 2:{
                              mylist=mydatabasee.getdatafromcurrenttMiami();
                              cityy.setText(mylist.get(mylist.size()-1).getMycity());
                              countryy.setText(mylist.get(mylist.size()-1).getMycoun());
                              dataa.setText(mylist.get(mylist.size()-1).getMydata());
                              conditiontext.setText(mylist.get(mylist.size()-1).getMycondi());
                              temp.setText(mylist.get(mylist.size()-1).getMytemp());
                              Toast.makeText(MainActivity.this,String.valueOf(mylist.size()),Toast.LENGTH_LONG).show();
                              break;
                          }
                          case 3:{
                              mylist=mydatabasee.getdatafromcurrenttparis();
                              cityy.setText(mylist.get(mylist.size()-1).getMycity());
                              countryy.setText(mylist.get(mylist.size()-1).getMycoun());
                              dataa.setText(mylist.get(mylist.size()-1).getMydata());
                              conditiontext.setText(mylist.get(mylist.size()-1).getMycondi());
                              temp.setText(mylist.get(mylist.size()-1).getMytemp());
                              Toast.makeText(MainActivity.this,String.valueOf(mylist.size()),Toast.LENGTH_LONG).show();
                              break;
                          }
                          case 4:{
                              mylist=mydatabasee.getdatafromcurrenttchicago();
                              cityy.setText(mylist.get(mylist.size()-1).getMycity());
                              countryy.setText(mylist.get(mylist.size()-1).getMycoun());
                              dataa.setText(mylist.get(mylist.size()-1).getMydata());
                              conditiontext.setText(mylist.get(mylist.size()-1).getMycondi());
                              temp.setText(mylist.get(mylist.size()-1).getMytemp());
                              Toast.makeText(MainActivity.this,String.valueOf(mylist.size()),Toast.LENGTH_LONG).show();
                              break;
                          }
                      }






                    }
                });



                //Call Forecast Weather Data for 5 cities
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                HttpLoggingInterceptor loggingInterceptorr = new HttpLoggingInterceptor();
                loggingInterceptorr.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://api.apixu.com/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(builderr.build())
                        .build();

                Endpoints myendpointss = retrofit.create(Endpoints.class);
                callf=myendpointss.getforecast(myspinner.getSelectedItem().toString());
                callf.enqueue(new Callback<ForcWeather>() {
                    @Override
                    public void onResponse(Call<ForcWeather> call, Response<ForcWeather> response) {

                        if (response.isSuccessful())
                        {
                            mylistfor = response.body().getForecast().getForecastday();
                            foreadapter myadapter = new foreadapter(MainActivity.this,mylistfor);
                            myadapter.notifyDataSetChanged();

                            forrecycle.setAdapter(myadapter);


                        }
                        else{
                            Toast.makeText(MainActivity.this,"errorman",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ForcWeather> call, Throwable t) {

                        mylistfor=new ArrayList<>();
                        foreadapter myadapter = new foreadapter(MainActivity.this,mylistfor);
                        myadapter.notifyDataSetChanged();

                        forrecycle.setAdapter(myadapter);


                        Log.e("error2",t.getMessage());


                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }
}
