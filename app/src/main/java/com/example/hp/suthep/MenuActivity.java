package com.example.hp.suthep;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View head = navigationView.getHeaderView(0);

        TextView tvCompanyName = (TextView) head.findViewById(R.id.tvCompanyName);
        TextView tvCompanyAddress = (TextView) head.findViewById(R.id.tvCompanyAddress);

        tvCompanyName.setText(new SessionUtil(this).getCustomerName());
        tvCompanyAddress.setText(new SessionUtil(this).getCustomerAddress());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProductFragment fragment = new ProductFragment();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.product) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ProductFragment fragment = new ProductFragment();
            transaction.replace(R.id.main_frame, fragment);
            transaction.commit();
        } else if (id == R.id.customer) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CustomerFragment fragment = new CustomerFragment();
            transaction.replace(R.id.main_frame, fragment);
            transaction.commit();

        } else if (id == R.id.order) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            OrderFragment fragment = new OrderFragment();
            transaction.replace(R.id.main_frame, fragment);
            transaction.commit();
        } else if (id == R.id.history) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HistoryFragment fragment = new HistoryFragment();
            transaction.replace(R.id.main_frame, fragment);
            transaction.commit();
        }else if (id == R.id.logout){
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            new SessionUtil(MenuActivity.this).clearSession();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
