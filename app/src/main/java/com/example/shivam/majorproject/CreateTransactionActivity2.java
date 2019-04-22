package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;

public class CreateTransactionActivity2 extends AppCompatActivity {
    EditText quantity_et,commodity_et,charges_et;
    Button next_bt;
    TinyDB tinyDB;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction2);
        next_bt = (Button)findViewById(R.id.next_bt);
        quantity_et = (EditText) findViewById(R.id.quantity_et);
        commodity_et = (EditText)findViewById(R.id.commodity_et);
        charges_et = (EditText)findViewById(R.id.charges_et);
        tinyDB = new TinyDB(CreateTransactionActivity2.this);
        transaction = tinyDB.getObject("interim_transaction",Transaction.class);
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_et.getText().toString().length()>0 &&
                        commodity_et.getText().toString().length()>0 &&
                        charges_et.getText().toString().length()>0){
                    transaction.setQuantity(quantity_et.getText().toString() + " kg");
                    transaction.setCharges(charges_et.getText().toString() + " rs");
                    transaction.setCommodity(commodity_et.getText().toString() );
                    tinyDB.putObject("interim_transaction",transaction);
                    Intent intent = new Intent(CreateTransactionActivity2.this,CreateTransactioActivity3.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(CreateTransactionActivity2.this,"Please Fill All the Fields.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
