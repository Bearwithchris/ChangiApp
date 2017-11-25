package com.example.chris.changiappv3;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRVLocations; //mRVFishPrice
    private AdapterLocation mAdapter; //mAdapter

//    Button planner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       LocationDbHelper locationDbHelper=new LocationDbHelper(this);
//        planner=planner.findViewById(R.id.dayplanner);

//        planner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent=new Intent(MainActivity.this , dayplanner.class);
////                startActivity(intent);
//
//            }
//        });
        //Make call to AsyncTask
        new AsyncFetch().execute();
    }


    protected void dp(View view){
        Intent intent=new Intent(this,dayplanner.class);
        startActivity(intent);
    }


    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            InputStream input;
            StringBuilder result =null;
            try {
                input = getAssets().open("data.json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method

            } catch (IOException e) {
                e.printStackTrace();
            }

            return (result.toString());
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataLocation> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataLocation Data = new DataLocation();
                    Data.loationImage= json_data.getString("img");
                    Data.loationName= json_data.getString("location_name");
                    Data.catName= json_data.getString("cat_name");
                    Data.ratings= json_data.getString("rating");
                    Data.price= json_data.getInt("price");
                    data.add(Data);
                }

                // Setup and Handover data to recyclerview
                mRVLocations = (RecyclerView)findViewById(R.id.locationlist);
                mAdapter = new AdapterLocation(MainActivity.this, data);
                mRVLocations.setAdapter(mAdapter);
                mRVLocations.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
}
