package com.example.hp.suthep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {

    JSONObject product;
    ImageView imageView;
    TextView tvSoID, tvPrice, tvWeight, tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                product = new JSONObject(bundle.getString("product"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        tvSoID = (TextView) findViewById(R.id.tvSoID);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvDetail = (TextView) findViewById(R.id.tvDetail);

        try {
            tvSoID.setText(product.getString("prod_id"));
            tvPrice.setText(product.getString("price"));
            tvWeight.setText(product.getString("weight"));
            tvDetail.setText(product.getString("prod_detail"));
            String imageUrl = getString(R.string.ip_address) + "/suthep/image/" + product.getString("prod_id") + ".jpg";
            Glide.with(this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.no_product)
                    .into(imageView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
