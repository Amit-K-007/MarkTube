package com.example.marktube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> bookmark_id,bookmark_label,bookmark_timestamp,bookmark_link;
    static CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//
        //TURN ON ACCESSIBILITY SERVICE
        boolean enabled = isAccessibilityServiceEnabled(this, YTAccessibilityService.class);
        if(!enabled) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }
        //
        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(MainActivity.this);
        bookmark_id = new ArrayList<>();
        bookmark_label = new ArrayList<>();
        bookmark_timestamp = new ArrayList<>();
        bookmark_link = new ArrayList<>();
        storeDataInArray();

        customAdapter = new CustomAdapter(MainActivity.this,bookmark_id,bookmark_label,bookmark_timestamp,bookmark_link);

        recyclerView.setAdapter(customAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    public static boolean isAccessibilityServiceEnabled(Context context, Class<?> accessibilityService) {
        ComponentName expectedComponentName = new ComponentName(context, accessibilityService);

        String enabledServicesSetting = Settings.Secure.getString(context.getContentResolver(),  Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (enabledServicesSetting == null)
            return false;

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        colonSplitter.setString(enabledServicesSetting);

        while (colonSplitter.hasNext()) {
            String componentNameString = colonSplitter.next();
            ComponentName enabledService = ComponentName.unflattenFromString(componentNameString);

            if (enabledService != null && enabledService.equals(expectedComponentName))
                return true;
        }

        return false;
    }
    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                bookmark_id.add(cursor.getString(0));
                bookmark_label.add(cursor.getString(1));
                bookmark_timestamp.add(cursor.getString(2));
                bookmark_link.add(cursor.getString(3));

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            MyDatabaseHelper myDB = new MyDatabaseHelper(this);
            myDB.deleteAllData();
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }


}