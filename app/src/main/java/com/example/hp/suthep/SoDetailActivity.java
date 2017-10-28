package com.example.hp.suthep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SoDetailActivity extends AppCompatActivity {

    String soid;
    JSONObject customer;
    SimpleDateFormat input, output;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            soid = bundle.getString("soid");
            getSupportActionBar().setTitle("เอกสารเลขที่ " + soid);
        }

        input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        output = new SimpleDateFormat("dd MMMM yyyy");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        String url = getString(R.string.ip_address) + "/suthep/action/android_showso.php?so_id=" + soid;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONArray json = new JSONArray(response.body().string());
                    if (json.length() > 0) {
                        customer = json.getJSONObject(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tvCustName = (TextView) findViewById(R.id.tvCustName);
                                TextView tvCustAddress = (TextView) findViewById(R.id.tvCustAddress);
                                TextView tvOrderDate = (TextView) findViewById(R.id.tvOrderDate);
                                TextView tvSendDate = (TextView) findViewById(R.id.tvSendDate);

                                mAdapter = new SoDetailAdapter(SoDetailActivity.this, json);
                                mRecyclerView.setAdapter(mAdapter);



                                try {
                                    tvCustName.setText(customer.getString("cust_name"));
                                    tvCustAddress.setText(customer.getString("cust_address"));
                                    tvOrderDate.setText("เริ่มผลิต "+output.format(input.parse(customer.getString("date_time"))));
                                    int total = customer.getInt("total");
                                    int workday = (int) (Math.ceil((total / 20.0)) + 2);

                                    double totalprice = 0.0;
                                    double totalnet = 0.0;
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject item = json.getJSONObject(i);
                                        int number = Integer.parseInt(item.getString("number"));
                                        double price = Double.parseDouble(item.getString("price"));
                                        double weight = Double.parseDouble(item.getString("weight"));

                                        totalprice = ((number*weight)*price);
                                        totalnet += (totalprice*7/100)+totalprice;
                                    }
                                    TextView net = (TextView) findViewById(R.id.net);
                                    net.setText(NumberFormat.getNumberInstance().format(totalnet) +"");



                                    Calendar cal = Calendar.getInstance();
                                    try {
                                        cal.setTime(input.parse(customer.getString("date_time")));
                                        cal.add(Calendar.DATE, workday);
                                        tvSendDate.setText("กำหนดส่ง "+output.format(cal.getTime()));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }



                            }
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();


                }
            }
        });

        }

    }

