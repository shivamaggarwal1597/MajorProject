package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;

public class ShowInformationActivity extends AppCompatActivity {
    TextView trans_id_tv, emp_name_tv, start_date_tv, end_date_tv, charges_tv;
    Button track_transaction;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        tinyDB = new TinyDB(ShowInformationActivity.this);

        trans_id_tv = (TextView)findViewById(R.id.trans_id_tv);

        emp_name_tv =  (TextView)findViewById(R.id.trans_emp_name_tv);

        start_date_tv = (TextView)findViewById(R.id.trans_start_date_tv);

        end_date_tv = (TextView)findViewById(R.id.trans_end_date_tv);

        charges_tv = (TextView)findViewById(R.id.trans_charges_tv);

        track_transaction = (Button)findViewById(R.id.track_trans_tv);
        Transaction t =  tinyDB.getObject("transaction_key_db",Transaction.class);
        trans_id_tv.setText("Transaction ID: "+ t.getTransaction_id());
        emp_name_tv.setText("Employee ID: "+ t.getEmployee_id());
        start_date_tv.setText("Check In Date :"+ t.getCheck_in_date());
        end_date_tv.setText("Check Out Date : "+ t.getCheck_out_date());
        charges_tv.setText("Charges : "+ t.getCharges());



        track_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowInformationActivity.this, MapsActivity.class);

                startActivity(intent);

            }
        });
    }
}
