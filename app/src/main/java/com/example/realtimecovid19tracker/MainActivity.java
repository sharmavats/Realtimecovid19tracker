package com.example.realtimecovid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realtimecovid19tracker.api.Apiutilities;
import com.example.realtimecovid19tracker.api.countrydata;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView Totalconfirm,Totalactive,Totalrecover,Totaldeath,Totaltest;
    private  TextView Todayconfirm,Todaydeath,Todayrecover,dateTV;
    private PieChart piechart;

    private List<countrydata> list;
    String country = "India";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        if(getIntent().getStringExtra("country") != null )
            country = getIntent().getStringExtra("country");

       init();

       TextView cname = findViewById(R.id.cname);
       cname.setText(country);

       cname.setOnClickListener(v ->
               startActivity(new Intent(MainActivity.this,CoountryActivity.class)));


        Apiutilities.getApiInterface().getcountrydata()
                .enqueue(new Callback<List<countrydata>>() {
                    @Override
                    public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                        list.addAll(response.body());
                        for (int i=0; i<list.size(); i++){
                            if(list.get(i).getCountry().equals(country)){
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());


                                Totalactive.setText(NumberFormat.getInstance().format(active));
                                Totalconfirm.setText(NumberFormat.getInstance().format(confirm));
                                Totalrecover.setText(NumberFormat.getInstance().format(recovered));
                                Totaldeath.setText(NumberFormat.getInstance().format(death));


                                Todaydeath.setText(("+ ")+(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths()))));
                                Todayconfirm.setText(("+ ")+(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases()))));
                                Todayrecover.setText(("+ ")+(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered()))));
                                Totaltest.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));


                                setText(list.get(i).getUpdated());
                                piechart.addPieSlice(new PieModel("Confirm",confirm, getResources().getColor(R.color.YELLOW)));
                                piechart.addPieSlice(new PieModel("Active",active, getResources().getColor(R.color.BLUE_PIE)));
                                piechart.addPieSlice(new PieModel("Recovered",recovered, getResources().getColor(R.color.green)));
                                piechart.addPieSlice(new PieModel("Death",death, getResources().getColor(R.color.red_pie)));

                                piechart.startAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<countrydata>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("dd MMM , yyyy");
        long milliseconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        dateTV.setText("Updated at "+format.format(calendar.getTime()));
    }

    private void init(){
        Totalconfirm = findViewById(R.id.totalconfirm);
        Totalactive = findViewById(R.id.totalactive);
        Totalrecover = findViewById(R.id.totalrecover);
        Totaldeath = findViewById(R.id.totaldeath);
        Totaltest = findViewById(R.id.totaltest);
        Todayconfirm = findViewById(R.id.todayconfirm);
        Todaydeath = findViewById(R.id.todaydeath);
        Todayrecover = findViewById(R.id.todayrecover);
        piechart = findViewById(R.id.piechart);
        dateTV = findViewById(R.id.date);


    }
}