package weatherapp.weatherapp;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weatherapp.weatherapp.models.CurWeather;
import weatherapp.weatherapp.models.ForcWeather;

public interface Endpoints {


    @GET("current.json?key=ae70fd6bf0074213bf054319181901")
    Call<CurWeather> getcurentweather(@Query("q") String citty);

    @GET("forecast.json?key=ae70fd6bf0074213bf054319181901&days=5")
    Call<ForcWeather> getforecast(@Query("q") String city);


}
