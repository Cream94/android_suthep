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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HP on 7/29/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context ctx;
    JSONArray arr;

    SimpleDateFormat input, output;

    public OrderAdapter(Context ctx, JSONArray arr) {
        this.ctx = ctx;
        this.arr = arr;
        input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        output = new SimpleDateFormat("dd MMMM yyyy");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            final JSONObject item = arr.getJSONObject(position);
            holder.soid.setText(item.getString("so_id"));
            holder.datetime.setText(output.format(input.parse(item.getString("date_time"))));
            //holder.date.setText(item.getString("count"));
            //holder.send.setText(item.getString("total"));
            int total = item.getInt("total");
            int workday = (int) (Math.ceil((total / 20.0)) + 2);
            holder.date.setText(String.valueOf(workday));

            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(input.parse(item.getString("date_time")));
                cal.add(Calendar.DATE, workday);
                holder.send.setText(output.format(cal.getTime()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            holder.status.setText(item.getString("status_name"));

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
        TextView soid, datetime, date, send, status;

        public ViewHolder(View itemView) {
            super(itemView);
            soid = (TextView) itemView.findViewById(R.id.order_soid);
            datetime = (TextView) itemView.findViewById(R.id.order_datetime);
            date = (TextView) itemView.findViewById(R.id.order_date);
            send = (TextView) itemView.findViewById(R.id.order_send);
            status = (TextView) itemView.findViewById(R.id.order_status);
        }
    }
}
