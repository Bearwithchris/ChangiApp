package com.example.chris.changiappv3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class dayplanner extends AppCompatActivity {

    public LocationDbHelper locationDbHelper;
    public SQLiteDatabase locationDb;
    TextView testview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayplanner);

       Cursor cursor= locationDbHelper.getInformation(locationDb);
        String outstring="";
        int indexRemarks=cursor.getColumnIndex(LocationsContract.LocationEntry.COL_LOCATIONNAME);
        int indexAmount=cursor.getColumnIndex(LocationsContract.LocationEntry.COL_AMOUNT);
        while(cursor.moveToNext()){
            String myAmount = cursor.getString(indexRemarks);
            String myRemarks = cursor.getString(indexAmount);

            outstring=outstring+myRemarks+" "+myAmount+"\n";
        }

        testview.setText(outstring);
    }


}
