package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;

public class ManageTransactionsActivity extends AppCompatActivity implements
        EmployeeAllTransactionFragment.OnListFragmentInteractionListener{
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transactions);
        tinyDB = new TinyDB(ManageTransactionsActivity.this);
        tinyDB.putString("status_transaction_type","transaction");
        EmployeeAllTransactionFragment employeeAllTransactionFragment = new EmployeeAllTransactionFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_manage_transactions,employeeAllTransactionFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onListFragmentInteraction(Transaction item) {
        Intent intent = new Intent(ManageTransactionsActivity.this,ShowInformationActivity.class);
        tinyDB.putObject("transaction_key_db",item);
        startActivity(intent);
    }
}
