package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//This is my Login Start Screen where the user will choose the type of login
    Button employee, employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employee = (Button)findViewById(R.id.btn_employee);
        employer = (Button)findViewById(R.id.btn_employer);
        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("code","employer");
                startActivity(intent);
                finish();

            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("code","employee");
                startActivity(intent);
                finish();
            }
        });


    }
}
