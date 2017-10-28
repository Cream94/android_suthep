package com.example.hp.suthep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText editusername, editpassword ;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editusername = (EditText) findViewById(R.id.editusername);
        editpassword = (EditText) findViewById(R.id.editpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        String oldUser = new SessionUtil(this).getCustomerUsername();
        String oldPassword = new SessionUtil(this).getCustomerPassword();
        if (oldUser != null && oldPassword != null) {
            login(oldUser, oldPassword);
        } else {
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = editusername.getText().toString();
                    final String password = editpassword.getText().toString();
                    login(username, password);
                }
            });
        }
    }

    public void login(final String username, final String password) {
        final String url = getString(R.string.ip_address) + "/suthep/action/login_by_app.php";
        final OkHttpClient client = new OkHttpClient();
        // OkHttp Client For POST
        RequestBody form = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(form)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String fromATOM = response.body().string();
                try {
                    JSONObject resp = new JSONObject(fromATOM);
                    if (resp.has("result")) {
                        if (resp.getBoolean("result")) {
                            int custID = resp.getInt("cust_id");
                            String custName = resp.getString("cust_name");
                            String custAddress = resp.getString("cust_address");
                            String custTel = resp.getString("cust_tel");
                            new SessionUtil(MainActivity.this).saveCustomer(custID, custName, custAddress, custTel, username, password);
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
