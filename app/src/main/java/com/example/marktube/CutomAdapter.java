package com.example.marktube;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList bookmark_id,bookmark_label,bookmark_timestamp,bookmark_link;

    CustomAdapter(Context context, ArrayList bookmark_id,ArrayList bookmark_label,ArrayList bookmark_timestamp,ArrayList bookmark_link){
        this.context = context;
        this.bookmark_id = bookmark_id;
        this.bookmark_label = bookmark_label;
        this.bookmark_timestamp = bookmark_timestamp;
        this.bookmark_link = bookmark_link;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookmark_id_txt.setText(String.valueOf(bookmark_id.get(position)));
        holder.bookmark_label_txt.setText(String.valueOf(bookmark_label.get(position)));
        holder.bookmark_timestamp_txt.setText(String.valueOf(bookmark_timestamp.get(position)));
        holder.bookmark_link_txt.setText(String.valueOf(bookmark_link.get(position)));
        Linkify.addLinks(holder.bookmark_link_txt,Linkify.WEB_URLS);
    }

    @Override
    public int getItemCount() {
        return bookmark_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookmark_id_txt,bookmark_label_txt,bookmark_timestamp_txt,bookmark_link_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmark_id_txt = itemView.findViewById(R.id.bookmark_id_txt);
            bookmark_label_txt = itemView.findViewById(R.id.bookmark_label_txt);
            bookmark_timestamp_txt = itemView.findViewById(R.id.bookmark_timestamp_txt);
            bookmark_link_txt = itemView.findViewById(R.id.bookmark_link_txt);
        }
    }
}
