package com.example.realtimecovid19tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realtimecovid19tracker.api.countrydata;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// creating adapter class
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.Countryviewholder> {
    private Context context;
    private List<countrydata> list;

    public CountryAdapter(Context context, List<countrydata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Countryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item_layout, parent, false);

        return new Countryviewholder(view);
    }

    public void filterList(List<countrydata> filterlist){
        list=filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Countryviewholder holder, int position) {
   countrydata data = list.get(position);
   holder.countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
   holder.countryname.setText(data.getCountry());
   holder.sno.setText(String.valueOf(position+1));

        Map<String,String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra( "country",data.getCountry());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class Countryviewholder extends RecyclerView.ViewHolder {
        private TextView sno, countryname, countrycases;
        private ImageView imageView;

        public Countryviewholder(@NonNull View itemView) {

            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            countrycases = itemView.findViewById(R.id.countrycases);
            countryname = itemView.findViewById(R.id.countryname);
            imageView = itemView.findViewById(R.id.countryimage);

        }
    }
}
