package com.example.hp.suthep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HP on 7/28/2017.
 */

public class ProductAdapter extends BaseAdapter {

    Context ctx;
    JSONArray arr;

    public ProductAdapter(Context c, JSONArray a) {
        ctx = c;
        arr = a;
    }

    @Override
    public int getCount() {
        return arr.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_product, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView prodName = (TextView) view.findViewById(R.id.prod_name);
        TextView prodDetail = (TextView) view.findViewById(R.id.prod_detail);
        try {
            JSONObject obj = arr.getJSONObject(position);
            prodName.setText(obj.getString("prod_id"));
            prodDetail.setText(obj.getString("prod_detail"));
            String imageUrl = ctx.getString(R.string.ip_address) + "/suthep/image/" + obj.getString("prod_id") + ".jpg";
            Glide.with(ctx)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.no_product)
                    .into(imageView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }
}
