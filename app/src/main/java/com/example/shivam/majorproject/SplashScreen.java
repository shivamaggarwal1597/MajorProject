package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.utils.TinyDB;

public class SplashScreen extends AppCompatActivity {
//This is my splash screen
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tinyDB = new TinyDB(SplashScreen.this);
        try{
            Configuration c = tinyDB.getObject("config", Configuration.class);
            if (c!=null) {
                if (c.isLogged_in()) {
                    if (c.type_of_person.equals("employee")) {
                        Intent intent = new Intent(SplashScreen.this, DashboardEmployeeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                            Intent intent = new Intent(SplashScreen.this, DashboardEmployerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
