package com.example.realtimecovid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realtimecovid19tracker.api.Apiutilities;
import com.example.realtimecovid19tracker.api.countrydata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<countrydata> list ;
    private ProgressDialog dialog;
    private EditText Searchbar;
    private CountryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.countries);
        list = new ArrayList<>();
        Searchbar = findViewById(R.id.search_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

         adapter = new CountryAdapter(this,list);
        recyclerView.setAdapter(adapter);

    dialog = new ProgressDialog(this);
    dialog.setMessage("Loading.....");
    dialog.setCancelable(false);
    dialog.show();

        Apiutilities.getApiInterface().getcountrydata().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                Toast.makeText(CoountryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        List<countrydata> filterlist = new ArrayList<>();
        for (countrydata items : list){
            if(items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(items);
            }
        }

        adapter.filterList(filterlist);
    }
}