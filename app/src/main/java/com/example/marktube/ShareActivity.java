package com.example.marktube;

import static com.example.marktube.YTAccessibilityService.timestamp;
import static com.example.marktube.YTAccessibilityService.timestamp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {
    private TextView link_data_id,timestamp_data_id;
    private Button save_id,cancel_id;
    private EditText label_data_id;
    private String videoLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // DECLARING VIEWS
        link_data_id = findViewById(R.id.link_data_id);
        timestamp_data_id = findViewById(R.id.timestamp_data_id);
        save_id = findViewById(R.id.save_id);
        cancel_id = findViewById(R.id.cancel_id);
        label_data_id = findViewById(R.id.label_data_id);

        //SETTING LINK AND TIMESTAMP
        Intent intent = getIntent();
        String getIntentAction = intent.getAction();
        String getIntentType = intent.getType();
        if(getIntentAction.equals(Intent.ACTION_SEND) && getIntentAction!=null && "text/plain".equals(getIntentType)){
            videoLink = intent.getStringExtra(Intent.EXTRA_TEXT);
            if(videoLink!=null && videoLink.contains("https://youtu.be/")){
                link_data_id.setText(videoLink);
                //timestamp_data_id.setText(timestamp);
            }
        }

        //Timestamp calculation
        String time1 = timestamp.toString();
        String time2 = timestamp2.toString();
        String timeString1[], timeString2[],timeString3="";
        int seconds1 = 0, seconds2 = 0;
        if (time1.charAt(0) != '-') {
            timeString1 = time1.split(":");
            if (time1.length() == 4 || time1.length() == 5) {
                seconds1 = (Integer.parseInt(timeString1[0])) * 60 + (Integer.parseInt(timeString1[1]));
            } else if (time1.length() == 7 || time1.length() == 8 || time1.length() == 9) {
                seconds1 = (Integer.parseInt(timeString1[0])) * 3600 + (Integer.parseInt(timeString1[1])) * 60 + (Integer.parseInt(timeString1[2]));
            }
            timestamp_data_id.setText(time1);
            Log.e("mytag", "onCreate: "+seconds1 );
        } else {
            time1 = time1.substring(1, time1.length());
            timeString1 = time1.split(":");
            if (time1.length() == 4 || time1.length() == 5) {
                seconds1 = (Integer.parseInt(timeString1[0])) * 60 + (Integer.parseInt(timeString1[1]));

            } else if (time1.length() == 7 || time1.length() == 8 || time1.length() == 9) {
                seconds1 = (Integer.parseInt(timeString1[0])) * 3600 + (Integer.parseInt(timeString1[1])) * 60 + (Integer.parseInt(timeString1[2]));
            }

            time2 = time2.substring(3, time2.length());
            timeString2 = time2.split(":");
            if (time2.length() == 4 || time2.length() == 5) {
                seconds2 = (Integer.parseInt(timeString2[0])) * 60 + (Integer.parseInt(timeString2[1]));
                if(Integer.valueOf(timeString2[1])-Integer.valueOf(timeString1[1])>9) {
                    timeString3 = String.valueOf(Integer.valueOf(timeString2[0]) - Integer.valueOf(timeString1[0])) + ":" + String.valueOf(Integer.valueOf(timeString2[1]) - Integer.valueOf(timeString1[1]));
                }
                else{
                    timeString3 = String.valueOf(Integer.valueOf(timeString2[0]) - Integer.valueOf(timeString1[0])) + ":0" + String.valueOf(Integer.valueOf(timeString2[1]) - Integer.valueOf(timeString1[1]));
                }
            } else if (time2.length() == 7 || time2.length() == 8 || time2.length() == 9) {
                seconds2 = (Integer.parseInt(timeString2[0])) * 3600 + (Integer.parseInt(timeString2[1])) * 60 + (Integer.parseInt(timeString2[2]));
                timeString3 = String.valueOf(Integer.valueOf(timeString2[0])-Integer.valueOf(timeString1[0]))+":"+String.valueOf(Integer.valueOf(timeString2[1])-Integer.valueOf(timeString1[1])+":"+String.valueOf(Integer.valueOf(timeString2[2])-Integer.valueOf(timeString1[2])));
            }
            timestamp_data_id.setText(timeString3);
            seconds1 = seconds2-seconds1;
            Log.e("mytag", "onCreate: "+(seconds1));
        }

        //SAVE BUTTON
        int finalSeconds = seconds1;
        save_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("mytag", "label: "+label_data_id.getText().toString().trim());
//                Log.e("mytag", "timestamp: "+timestamp_data_id.getText().toString().trim());
//                Log.e("mytag", "link: "+((link_data_id.getText().toString())+"?t="+String.valueOf(finalSeconds)).trim() );
                MyDatabaseHelper mydb = new MyDatabaseHelper(ShareActivity.this);
                mydb.addBookmark(label_data_id.getText().toString().trim(),timestamp_data_id.getText().toString().trim(),((link_data_id.getText().toString())+"?t="+String.valueOf(finalSeconds)).trim());
                finish();
            }
        });

        //CANCEL BUTTON
        cancel_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}