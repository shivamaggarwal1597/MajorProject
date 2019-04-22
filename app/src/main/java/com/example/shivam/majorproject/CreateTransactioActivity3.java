package com.example.shivam.majorproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class CreateTransactioActivity3 extends AppCompatActivity {
    Button convert_pickup_bt,convert_dropoff_bt,commit_transaction_bt;
    EditText address_pickup_et,address_dropoff_et;
    Transaction transaction;
    TinyDB tinyDB;
    Geocoder geocoder;
    List<Address> addresses;
    TextView lat_lng_pickup_tv,lat_lng_dropoff_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transactio3);
        tinyDB = new TinyDB(CreateTransactioActivity3.this);

        //Buttons
        convert_pickup_bt  =  (Button)findViewById(R.id.pick_up_point_convert_bt);
        convert_dropoff_bt = (Button)findViewById(R.id.drop_off_point_convert_bt);
        commit_transaction_bt = (Button)findViewById(R.id.commit_transaction_bt);
        //Edit Texts
        address_pickup_et = (EditText)findViewById(R.id.pick_up_point_address_et);
        address_dropoff_et  = (EditText)findViewById(R.id.drop_off_point_address_et);
        //Text Views
        lat_lng_dropoff_tv = (TextView)findViewById(R.id.drop_off_point_address_tv);
        lat_lng_pickup_tv  = (TextView) findViewById(R.id.pick_up_point_address_tv);

        transaction = tinyDB.getObject("interim_transaction",Transaction.class);

        //Setting the listeners
        commit_transaction_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address_pickup_et.getText().toString().trim().length()>0 &&
                        address_dropoff_et.getText().toString().trim().length()>0){
                    tinyDB.putObject("interim_transaction",transaction);

                    Intent intent = new Intent(CreateTransactioActivity3.this,CreateTransactionActivity4.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(CreateTransactioActivity3.this,"please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });
        convert_pickup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address_pickup_et.getText().toString().trim().length()>0){
                    String val = getLocationFromAddress(address_pickup_et.getText().toString().trim(),1);
                    lat_lng_pickup_tv.setText("lat_lng : "+ val);
                }else {
                    Toast.makeText(CreateTransactioActivity3.this, "Please enter the details properly",Toast.LENGTH_LONG).show();
                }
            }
        });

        convert_dropoff_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address_dropoff_et.getText().toString().trim().length()>0){
                    String val = getLocationFromAddress(address_dropoff_et.getText().toString().trim(),2);
                    lat_lng_dropoff_tv.setText("lat_lng : "+ val);
                }else {
                    Toast.makeText(CreateTransactioActivity3.this, "Please enter the details properly",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public String getLocationFromAddress(String strAddress, int code){
        //Code -->  1. Start , 2. End
      String ret_string = "default";
      geocoder  = new Geocoder(CreateTransactioActivity3.this);
      LatLng p = null;

      try{
          addresses = geocoder.getFromLocationName(strAddress,3);
          if (addresses == null){
              Toast.makeText(CreateTransactioActivity3.this,"Unable to Fetch Coordinates for this address",Toast.LENGTH_LONG).show();
          }else {
              Address location = addresses.get(0);
              p =  new LatLng(location.getLatitude(),location.getLongitude());
              ret_string = p.latitude + "_" + p.longitude;
              switch (code){
                  case 1  :
                      transaction.setStarting_location_set(true);
                      transaction.setStarting_location(ret_string);
                      transaction.setStarting_address(strAddress);
                      break;
                  case 2 :
                      transaction.setEnding_location_set(true);
                      transaction.setEnding_location(ret_string);
                      transaction.setEnding_address(strAddress);
                      break;
                   default:
                       Log.e("Error Check: ","default case running");
              }
              return  ret_string;
          }

      }catch (IOException e){
          e.printStackTrace();
      }
      return "Unable to fetch coordinates, try writing again.";
    }
}
