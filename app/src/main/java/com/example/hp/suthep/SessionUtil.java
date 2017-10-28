package com.example.hp.suthep;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HP on 7/29/2017.
 */

public class SessionUtil {

    Context ctx;
    SharedPreferences spf;
    private String KEY_PREF = "session_util";
    private String CUST_ID = "cust_id";
    private String CUST_USERNAME = "cust_username";
    private String CUST_PASS = "cust_pass";
    private String CUST_NAME = "cust_name";
    private String CUST_ADDRESS = "cust_address";
    private String CUST_TEL = "cust_tel";

    public SessionUtil(Context c) {
        ctx = c;
        spf = ctx.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    public void saveCustomer(int custID, String custName, String custAddress, String custTel, String username, String pass) {
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt(CUST_ID, custID);
        editor.putString(CUST_USERNAME, username);
        editor.putString(CUST_PASS, pass);
        editor.putString(CUST_NAME, custName);
        editor.putString(CUST_ADDRESS, custAddress);
        editor.putString(CUST_TEL, custTel);
        editor.commit();
    }

    public int getCustomerID() {
        return spf.getInt(CUST_ID, 0);
    }

    public String getCustomerUsername() {
        return spf.getString(CUST_USERNAME, null);
    }

    public String getCustomerPassword() {
        return spf.getString(CUST_PASS, null);
    }

    public String getCustomerName() {
        return spf.getString(CUST_NAME, "");
    }

    public String getCustomerAddress() {
        return spf.getString(CUST_ADDRESS, "");
    }

    public String getCustomerTel() {
        return spf.getString(CUST_TEL, "");
    }

    public void clearSession() {
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.clear();
            edit.commit();
        }
    }

}
