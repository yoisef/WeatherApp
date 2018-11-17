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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        forrecycle = findViewById(R.id.forcastrecycle);
        forrecycle.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        forrecycle.setLayoutManager(mLayoutManager);
        // intialize spinner for the 5 cities
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


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


                        if (response.isSuccessful()) {


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

                        Log.e("myerror",t.toString());



                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }
}
