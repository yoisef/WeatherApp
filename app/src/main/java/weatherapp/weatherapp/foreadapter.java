package weatherapp.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import weatherapp.weatherapp.models.ForcWeather;
import weatherapp.weatherapp.models.Forecastday;

public class foreadapter extends RecyclerView.Adapter<foreadapter.viewholder> {

    Context context;
    Call<ForcWeather> callf;

    ArrayList<Forecastday> mylistfor;
    ArrayList<String> nm=new ArrayList<>();
    MainActivity mymain=new MainActivity();

    public foreadapter( Activity context,List<Forecastday> mylist)
    {
        this.context=context;
        mylistfor= (ArrayList<Forecastday>) mylist;



    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.rowrecycle,parent,false);
        viewholder vholder=new viewholder(view);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

         holder.daydataa.setText(mylistfor.get(position).getDate());
        holder.daytextcobdition.setText(mylistfor.get(position).getDay().getCondition().getText());

        Glide.with(context)
                .load("http:"+mylistfor.get(position).getDay().getCondition().getIcon())
                .into(holder.imageView);


        holder.tempmax.setText(String.valueOf(mylistfor.get(position).getDay().getMaxtempC()));
        holder.tempmin.setText(String.valueOf(mylistfor.get(position).getDay().getMintempC()));
        holder.tempavg.setText(String.valueOf(mylistfor.get(position).getDay().getAvgtempC()));

        //holder.daydataa.setText(nm.get(position));
    }

    @Override
    public int getItemCount() {
        return mylistfor.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView daydataa,daytextcobdition,tempmax,tempavg,tempmin;
        ImageView imageView;

        public viewholder(View itemView) {
            super(itemView);
            daydataa=itemView.findViewById(R.id.dataday);
            daytextcobdition=itemView.findViewById(R.id.daycondition);
            tempavg=itemView.findViewById(R.id.avgtemp);
            tempmax=itemView.findViewById(R.id.maxtemp);
            tempmin=itemView.findViewById(R.id.mintemp);
            imageView=itemView.findViewById(R.id.dayimgcondition);
        }
    }
}
