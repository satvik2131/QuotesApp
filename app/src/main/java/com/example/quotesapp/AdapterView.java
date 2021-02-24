package com.example.quotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterView extends RecyclerView.Adapter<AdapterView.ViewHolder> {
    private Context context;
    private ArrayList<String> title;
    private ArrayList<String> description;

    public AdapterView(Context context , ArrayList<String> title , ArrayList<String> description){
        this.title = title;
        this.description = description;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.quotescardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterView.ViewHolder holder, int position) {
            holder.title.setText(title.get(position));
            holder.description.setText(description.get(position));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cardViewTitle);
            description = (TextView) itemView.findViewById(R.id.cardViewDescription);
        }
    }
}
