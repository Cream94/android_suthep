package com.example.hp.suthep;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by HP on 7/29/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context ctx;
    JSONArray arr;

    SimpleDateFormat input, output;

    public HistoryAdapter(Context ctx, JSONArray arr) {
        this.ctx = ctx;
        this.arr = arr;

        input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        output = new SimpleDateFormat("dd MMMM yyyy");
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_history, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        try {
            final JSONObject item = arr.getJSONObject(position);
            holder.soid.setText(item.getString("so_id"));
            holder.datetime.setText(output.format(input.parse(item.getString("date_time"))));
            holder.count.setText(item.getString("count"));
            double vat = Double.parseDouble(item.getString("total")) + (Double.parseDouble(item.getString("total")) * 7 / 100);
            String textTotal = NumberFormat.getNumberInstance().format(vat);
            holder.total.setText(textTotal);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ctx, SoDetailActivity.class);
                    try {
                        in.putExtra("soid", item.getString("so_id"));
                        ctx.startActivity(in);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soid, datetime, count, total;
        public ViewHolder(View itemView) {
            super(itemView);
            soid = (TextView) itemView.findViewById(R.id.soid);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            count = (TextView) itemView.findViewById(R.id.product);
            total = (TextView) itemView.findViewById(R.id.total);
        }
    }
}
