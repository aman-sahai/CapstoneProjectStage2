package com.example.searchjob;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Scroll recyclerView;
    RequestQueue requestQueue;
    List<MyJobs> list;
    String MainUrl = "https://jobs.github.com/positions.json?page=1&search=code";

    private FirebaseAuth mAuth;

    List<Savejob> saveJobDataList;
    AppData db;
    JobViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        saveJobDataList = new ArrayList<>();
        db = Room.databaseBuilder(this, AppData.class, getString(R.string.msg_db))
                .allowMainThreadQueries().build();
        viewModel = ViewModelProviders.of(this).get(JobViewModel.class);

        setTitle(getResources().getString(R.string.ompanylists));
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rec);
        requestQueue = Volley.newRequestQueue(this);
        list = new ArrayList<>();
        GetJobsInfo();

    }

    private void GetJobsInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (CheckNetwork()) {

                    String id = null;
                    String type = null;
                    String url = null;
                    String created_at = null;
                    String company = null;
                    String companyurl = null;
                    String location = null;
                    String company_logo = null;
                    String how_to_apply = null;
                    String description = null;
                    String title = null;

                    try {

                        JSONArray root = new JSONArray(response);
                        for (int i = 0; i < root.length(); i++) {
                            JSONObject jsonObject = root.getJSONObject(i);
                            id = jsonObject.getString("id");
                            type = jsonObject.getString("type");
                            url = jsonObject.getString("url");
                            created_at = jsonObject.getString("created_at");
                            company = jsonObject.getString("company");
                            companyurl = jsonObject.getString("company_url");
                            location = jsonObject.getString("location");
                            company_logo = jsonObject.getString("company_logo");
                            how_to_apply = jsonObject.getString("how_to_apply");
                            description = jsonObject.getString("description");
                            title = jsonObject.getString("title");
                            MyJobs myjobs = new MyJobs(id, type, url, created_at, company, companyurl, location, company_logo, how_to_apply, description, title);
                            list.add(myjobs);

                        }
                        MyAdapter adapter = new MyAdapter(HomeActivity.this, list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, getResources().getString(R.string.internet_connction),
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m1: {
                SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.aman), Context.MODE_PRIVATE).edit();
                editor.putInt(getResources().getString(R.string.aman), 0);
                editor.apply();
                callsignout();

            }
            case R.id.m2: {
                getdatafromdatabase();
                break;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean CheckNetwork() {
        boolean connected = false;
        try {
            ConnectivityManager connetion = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo Info = connetion.getActiveNetworkInfo();
            connected = Info != null && Info.isAvailable() && Info.isConnected();
            return connected;

        } catch (Exception e) {
        }
        return connected;
    }


    private void callsignout() {
        mAuth.signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getdatafromdatabase() {
        viewModel.getListLiveData().observe(this, new Observer<List<Savejob>>() {
            @Override
            public void onChanged(@Nullable List<Savejob> saveJobData) {
                saveJobDataList = saveJobData;


                if (saveJobDataList.isEmpty()) {
                    Toast.makeText(HomeActivity.this, R.string.jobsToast,
                            Toast.LENGTH_SHORT).show();
                }
                JobsAdapter adapter = new JobsAdapter(HomeActivity.this, saveJobDataList);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                recyclerView.setAdapter(adapter);


            }
        });


    }
}