package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shivam.majorproject.utils.TinyDB;

public class DashboardEmployerActivity extends AppCompatActivity {
    Button create_transactions,manage_employee_bt, logout_bt,manage_transactions_bt;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);
        create_transactions = (Button)findViewById(R.id.create_transaction_button);
        manage_employee_bt  = (Button)findViewById(R.id.manage_employees_bt);
        manage_transactions_bt = (Button)findViewById(R.id.manage_transactions_bt);

        tinyDB = new TinyDB(DashboardEmployerActivity.this);
        logout_bt = (Button)findViewById(R.id.logout_bt);
        logout_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.clear();
                Intent intent = new Intent(DashboardEmployerActivity.this, SplashScreen.class);
                startActivity(intent);
            }
        });
        manage_employee_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardEmployerActivity.this,ManageEmployeeActivity.class);
                startActivity(intent);
            }
        });
        create_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardEmployerActivity.this, CreateTransactionActivity.class);
                startActivity(intent);
            }
        });

        manage_transactions_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardEmployerActivity.this, ManageTransactionsActivity.class);
                startActivity(intent);
            }
        });
    }
}
