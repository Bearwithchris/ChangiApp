package com.example.chris.changiappv3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class dayplanner extends AppCompatActivity {

    public LocationDbHelper locationDbHelper;
    public SQLiteDatabase locationDb;
    TextView testview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayplanner);

        locationDbHelper=new LocationDbHelper(this);
        locationDb=locationDbHelper.getWritableDatabase();
        testview=findViewById(R.id.test);


    }

    public void onClickGetEntireDb(View view){

        //TO DO 3.7 Call the query or rawQuery method of the spendingDb object and store the result in a Cursor object
        final String SQL_QUERY_TABLE="SELECT * FROM "+LocationsContract.LocationEntry.TABLE_NAME;

        //TO DO 3.8 Extract the data from the Cursor object and display it on the textView widget
        Cursor cursor = locationDb.rawQuery(SQL_QUERY_TABLE, null);

        String outstring="";
        int indexRemarks=cursor.getColumnIndex(LocationsContract.LocationEntry.COL_LOCATIONNAME);
        int indexAmount=cursor.getColumnIndex(LocationsContract.LocationEntry.COL_AMOUNT);
        while(cursor.moveToNext()){
            String myAmount = cursor.getString(indexRemarks);
            String location = cursor.getString(indexAmount);

            outstring=outstring+location+" "+myAmount+"\n";
        }

        testview.setText(outstring);
    }

    public void removeEntireDb(View view){
        locationDb.delete(LocationsContract.LocationEntry.TABLE_NAME,null,null);
    }
}
