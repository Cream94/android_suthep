package com.example.hp.suthep;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by HP on 7/30/2017.
 */

public class SoDetailAdapter extends RecyclerView.Adapter<SoDetailAdapter.ViewHolder> {

    Context ctx;
    JSONArray arr;

    SimpleDateFormat input, output;

    public SoDetailAdapter(Context ctx, JSONArray arr){
        this.ctx = ctx;
        this.arr = arr;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_showso, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
            final JSONObject item = arr.getJSONObject(position);
            holder.count.setText(""+(position + 1));
            holder.detail.setText(item.getString("prod_detail"));
            holder.number.setText(item.getString("number"));
            holder.price.setText(item.getString("price"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView count, detail, number, price;
        public ViewHolder(View itemView) {
            super(itemView);
            count = (TextView) itemView.findViewById(R.id.tvCount);
            detail = (TextView) itemView.findViewById(R.id.tvTitle);
            number = (TextView) itemView.findViewById(R.id.tvNumber);
            price = (TextView) itemView.findViewById(R.id.pp);
        }
    }
}
