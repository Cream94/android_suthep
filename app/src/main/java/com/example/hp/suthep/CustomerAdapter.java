package com.example.hp.suthep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HP on 7/29/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    Context ctx;
    JSONArray arr;

    public CustomerAdapter (Context ctx, JSONArray arr) {
        this.ctx = ctx;
        this.arr = arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_customer, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject item = arr.getJSONObject(position);
            holder.custName.setText(item.getString("cust_name"));
            holder.custAddress.setText(item.getString("cust_address"));
            holder.custTel.setText(item.getString("cust_tel"));
            holder.custFax.setText(item.getString("cust_fax"));
            holder.email.setText(item.getString("email"));
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(ctx);
                    View view = LayoutInflater.from(ctx).inflate(R.layout.custom_repassword, null, false);
                    dialog.setContentView(view);

                    final EditText oldPassword = (EditText) view.findViewById(R.id.oldPassword);
                    final EditText password = (EditText) view.findViewById(R.id.password);
                    final EditText confirmpassword = (EditText) view.findViewById(R.id.confirmpassword);
                    Button btnchange = (Button) view.findViewById(R.id.btnchange);

                    btnchange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String oldpassword = oldPassword.getText().toString();
                            String newpassword = password.getText().toString();
                            String confirm = confirmpassword.getText().toString();
                            String url = ctx.getString(R.string.ip_address) + "/suthep/action/android_repassword.php";

                            if (oldpassword.equalsIgnoreCase("") || newpassword.equalsIgnoreCase("") || confirm.equalsIgnoreCase("")) {
                                Toast.makeText(ctx, "มีช่องว่าง กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                            } else if (!newpassword.equalsIgnoreCase(confirm)){
                                Toast.makeText(ctx, "รหัสผ่านใหม่ไม่ตรงกัน กรุณากรอกใหม่", Toast.LENGTH_LONG).show();
                            } else {
                                int userID = new SessionUtil(ctx).getCustomerID();

                                OkHttpClient client = new OkHttpClient();
                                RequestBody body = new FormBody.Builder()
                                        .add("userID", String.valueOf(userID))
                                        .add("oldpassword", oldpassword)
                                        .add("newpassword", newpassword)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(url)
                                        .post(body)
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final String fromATOM = response.body().string();
                                        ((Activity) ctx).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    JSONObject obj = new JSONObject(fromATOM);
                                                    if (obj.getBoolean("result")) {
                                                        Toast.makeText(ctx, "เปลี่ยนรหัสผ่านสำเร็จ กรุณาเข้าสู่ระบบใหม่", Toast.LENGTH_LONG).show();
                                                        ctx.startActivity(new Intent(ctx, MainActivity.class));
                                                        new SessionUtil(ctx).clearSession();
                                                        ((Activity) ctx).finish();
                                                    } else {
                                                        Toast.makeText(ctx, "ไม่สามารถเปลี่ยนรหััสผ่านได้ กรุณาลองใหม่", Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });

                    dialog.show();
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
        TextView custName, custAddress, custTel, custFax, email;
        Button btnEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            custName = (TextView) itemView.findViewById(R.id.cust_name);
            custAddress = (TextView) itemView.findViewById(R.id.cust_address);
            custTel = (TextView) itemView.findViewById(R.id.cust_tel);
            custFax = (TextView) itemView.findViewById(R.id.cust_fax);
            email = (TextView) itemView.findViewById(R.id.email);
            btnEdit = (Button) itemView.findViewById(R.id.btnedit);
        }
    }
}
