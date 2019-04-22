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

public class CreateTransactionActivity extends AppCompatActivity {
    EditText company_name_et,company_remarks_et, recipient_name_et;
    Button next_screen_bt;
    TinyDB tinyDB ;
    Transaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);
        tinyDB = new TinyDB(CreateTransactionActivity.this);
        //Started creating a transaction.
        company_name_et = (EditText)findViewById(R.id.recipient_company_name_edit_text);
        company_remarks_et = (EditText)findViewById(R.id.recipient_remarks_edit_text);
        recipient_name_et = (EditText)findViewById(R.id.recipient_name_edit_text);
        transaction = new Transaction();
        next_screen_bt = (Button)findViewById(R.id.submit_button);
        next_screen_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (company_remarks_et.getText().toString().length()>0 &&
                        company_name_et.getText().toString().length()>0 &&
                        recipient_name_et.getText().toString().length()>0){
                    transaction.setRecipient_remarks(company_remarks_et.getText().toString());
                    transaction.setRecipient_company_name(company_name_et.getText().toString());
                    transaction.setRecipient_name(recipient_name_et.getText().toString());
                    tinyDB.putObject("interim_transaction",transaction);
                    Intent intent = new Intent(CreateTransactionActivity.this, CreateTransactionActivity2.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(CreateTransactionActivity.this,"Please fill all these details", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
