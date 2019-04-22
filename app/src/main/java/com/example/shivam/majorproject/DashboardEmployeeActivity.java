package com.example.shivam.majorproject;


import android.*;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.LocationMonitoringService;
import com.example.shivam.majorproject.utils.StringUsed;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class DashboardEmployeeActivity extends AppCompatActivity implements EmployeeAllTransactionFragment.OnListFragmentInteractionListener {
    Button logout;
    private TextView mTextMessage;
    TinyDB tinyDB;
    FrameLayout frame;
    Transaction t;
    SimpleDateFormat simpleDateFormat;
    String date;
    boolean mAlreadyStartedService = false;
    LinearLayout linearLayout;
    Configuration configuration;
    String current_lat,current_lon;
    MaterialDialog materialDialog;
    TextView comp_name_tv, comp_addr_tv, commodity_tv, charges_tv, quantity_tv, transId_tv;
    Button check_in_bt, check_out_bt;
    FirebaseDatabase firebaseDatabase;
    FusedLocationProviderClient fusedLocationClient;
    DatabaseReference databaseReference;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    //all transactions
                    tinyDB.putString("status_transaction_type", "all");
                    linearLayout.setVisibility(View.GONE);
                    frame.setVisibility(View.VISIBLE);
                    EmployeeAllTransactionFragment employeeAllTransactionFragment = new EmployeeAllTransactionFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame, employeeAllTransactionFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    // active/current transactions
                    //tinyDB.putString("status_transaction_type","active");
                    linearLayout.setVisibility(View.VISIBLE);
                    frame.setVisibility(View.GONE);

                    materialDialog = new MaterialDialog.Builder(DashboardEmployeeActivity.this)
                            .title("Updating Data")
                            .content("Please Don't Press Back..")
                            .canceledOnTouchOutside(false)
                            .progress(true, 0)
                            .progressIndeterminateStyle(true).build();

                    databaseReference.child("employee").child(configuration.getLogin_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.child("availability").getValue(Boolean.class)) {
                                String current_trans_id = dataSnapshot.child("current_transaction_id").getValue(String.class);

                                databaseReference
                                        .child("transaction")
                                        .child(current_trans_id)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                t = dataSnapshot.getValue(Transaction.class);
                                                //SET UI DATA

                                                commodity_tv.setText("Commodity : "+t.getCommodity());
                                                comp_addr_tv.setText("Company State: "+t.getRecipient_remarks());
                                                comp_name_tv.setText("Company Name : "+t.getRecipient_company_name());
                                                charges_tv.setText("Charges: "+t.getCharges());
                                                quantity_tv.setText("Quantity: "+t.getQuantity());
                                                transId_tv.setText("Transaction ID : " +t.getTransaction_id());

                                                if (t.isStarted()) {
                                                    check_in_bt.setAlpha(0.5f);
                                                    check_in_bt.setClickable(false);
                                                    databaseReference
                                                            .child("transaction")
                                                            .child(t.getTransaction_id())
                                                            .child("current_employee_location")
                                                            .setValue(current_lat+"_"+current_lon);
                                                    LocalBroadcastManager.getInstance(DashboardEmployeeActivity.this).registerReceiver(new BroadcastReceiver() {
                                                        @Override
                                                        public void onReceive(Context context, Intent intent) {
                                                            final String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                                                            final String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
                                                            if (latitude != null && longitude != null) {
                                                                databaseReference
                                                                        .child("transaction")
                                                                        .child(t.getTransaction_id())
                                                                        .child("current_employee_location")
                                                                        .setValue(latitude + "_" + longitude, new DatabaseReference.CompletionListener() {
                                                                            @Override
                                                                            public void onComplete(DatabaseError databaseError, DatabaseReference dbr) {
                                                                                databaseReference.child("transaction").child(t.getTransaction_id())
                                                                                        .child("current_employee_location_set")
                                                                                        .setValue(true, new DatabaseReference.CompletionListener() {
                                                                                            @Override
                                                                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                                                Toast.makeText(DashboardEmployeeActivity.this, "Location Updated", Toast.LENGTH_LONG).show();
                                                                                                materialDialog.dismiss();
                                                                                            }
                                                                                        });
                                                                            }
                                                                        });


                                                            } else {
                                                                Toast.makeText(DashboardEmployeeActivity.this, "not working", Toast.LENGTH_LONG).show();
                                                                materialDialog.dismiss();
                                                            }


                                                        }
                                                    }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST));
                                                   // Intent intent = new Intent(DashboardEmployeeActivity.this, LocationMonitoringService.class);
                                                   // startService(intent);

                                                    mAlreadyStartedService = true;

                                                }else {
                                                    materialDialog.dismiss();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            } else {
                                Toast.makeText(DashboardEmployeeActivity.this, "No Transaction Currently Assigned", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    /*EmployeeAllTransactionFragment employeeAllTransactionFragment1 = new EmployeeAllTransactionFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction().replace(R.id.frame,employeeAllTransactionFragment1);
                    fragmentTransaction1.commit();
                    */
                    mTextMessage.setText(R.string.title_dashboard);

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onResume(){
        super.onResume();
        if (!mAlreadyStartedService){
          //  Intent intent = new Intent(DashboardEmployeeActivity.this, LocationMonitoringService.class);
           // startService(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employee);
        mTextMessage = (TextView) findViewById(R.id.message);
        tinyDB = new TinyDB(DashboardEmployeeActivity.this);
        frame = (FrameLayout) findViewById(R.id.frame);
        configuration = tinyDB.getObject(StringUsed.config_string_value, Configuration.class);
        frame.setVisibility(View.GONE);


        if (ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashboardEmployeeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(DashboardEmployeeActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            current_lat = String.valueOf(location.getLatitude());
                            current_lon = String.valueOf(location.getLongitude());

                        }
                    }
                });
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("major");
        linearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        linearLayout.setVisibility(View.VISIBLE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        transId_tv = (TextView)findViewById(R.id.transact_id_tv);
        commodity_tv = (TextView)findViewById(R.id.commodity_tv);
        quantity_tv = (TextView)findViewById(R.id.quantity_tv);
        charges_tv  = (TextView)findViewById(R.id.charges_tv);
        comp_name_tv = (TextView)findViewById(R.id.com_name_tv);
        comp_addr_tv = (TextView)findViewById(R.id.comp_addr_tv);
        check_in_bt = (Button)findViewById(R.id.check_in_bt);
        check_out_bt = (Button)findViewById(R.id.check_out_bt);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date =  simpleDateFormat.format(new Date()).trim();




        check_in_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!t.isStarted()){

                    materialDialog = new MaterialDialog.Builder(DashboardEmployeeActivity.this)
                            .title("Updating Data")
                            .content("Please Don't Press Back..")
                            .canceledOnTouchOutside(false)
                            .progress(true, 0)
                            .progressIndeterminateStyle(true).build();
                    materialDialog.show();
                    if (t.isStarting_location_set()){
                        String val_loc_str = t.getStarting_location();
                        String arr[]  = val_loc_str.split("_");
                        if (meterDistanceBetweenPoints(Float.valueOf(arr[0]),Float.valueOf(arr[1]),Float.valueOf(current_lat),Float.valueOf(current_lon))<100000){
                            databaseReference.child("transaction").child(t.getTransaction_id()).child("started").setValue(true, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference dbr) {
                                    databaseReference
                                            .child("transaction")
                                            .child(t.getTransaction_id())
                                            .child("check_in_date")
                                            .setValue(myDateFormatter(date), new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference dbr) {
                                                    check_in_bt.setClickable(false);
                                                    check_in_bt.setAlpha(0.5f);

                                                    databaseReference
                                                            .child("transaction")
                                                            .child(t.getTransaction_id())
                                                            .child("current_employee_location_set")
                                                            .setValue(true, new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(DatabaseError databaseError, DatabaseReference dbref) {
                                                                    materialDialog.dismiss();
                                                                }
                                                            });
                                                }
                                            });

                                }
                            });
                        }else {
                            Toast.makeText(DashboardEmployeeActivity.this,"You are not in Check In Range",Toast.LENGTH_LONG).show();
                        }
                    }
                    //Allowed to set Check In (set check in date for transaction)


                }else{
                    Toast.makeText(DashboardEmployeeActivity.this,"Transaction Already Checked in",Toast.LENGTH_LONG).show();
                }

            }
        });





        check_out_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (!t.isStarted()){
                Toast.makeText(DashboardEmployeeActivity.this,"You Need to Check In First.", Toast.LENGTH_LONG).show();

            }else if (t.isStarted()){
                //Now the transaction is being checked out.

                if (t.isEnding_location_set()){
                    String val_str[] = t.getEnding_location().split("_");
                    if (meterDistanceBetweenPoints(Float.valueOf(current_lat)
                            ,Float.valueOf(current_lon)
                            ,Float.valueOf(val_str[0])
                            ,Float.valueOf(val_str[1]))<100000){
                        databaseReference.child("transaction")
                                .child(t.getTransaction_id())
                                .child("check_out_date")
                                .setValue(myDateFormatter(date), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference dbr) {
                                        databaseReference.child("transaction")
                                                .child(t.getTransaction_id())
                                                .child("completed")
                                                .setValue(true, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference db) {
                                                        databaseReference.child("employer")
                                                                .child(t.getEmployer_id())
                                                                .child("transactions")
                                                                .child(t.getTransaction_id())
                                                                .setValue(true);
                                                        databaseReference.child("employer")
                                                                .child(t.getEmployer_id())
                                                                .child("employees")
                                                                .child(t.getEmployee_id())
                                                                .setValue("available");
                                                        databaseReference.child("employee")
                                                                .child(t.getEmployee_id())
                                                                .child("availability")
                                                                .setValue(true);
                                                        databaseReference.child("employee")
                                                                .child(t.getEmployee_id())
                                                                .child("transactions")
                                                                .child(t.getTransaction_id())
                                                        .setValue(false, new DatabaseReference.CompletionListener() {
                                                            @Override
                                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                                check_out_bt.setClickable(false);
                                                                check_out_bt.setAlpha(0.5f);

                                                            }
                                                        });

                                                    }
                                                });
                                    }
                                });

                    }

                }

            }
            }
        });




        //Present on current transactions page
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.clear();
                Intent intent = new Intent(DashboardEmployeeActivity.this,SplashScreen.class);
                startActivity(intent);
            }
        });

    }


    public String myDateFormatter(String date){


        String a = date.replace("-","_");
        return a;
    }



    @Override
    public void onListFragmentInteraction(Transaction item) {



    }

    public void onDestroy() {


        //Stop location sharing service to app server.........

   //     stopService(new Intent(this, LocationMonitoringService.class));

        //Ends................................................

     //   mAlreadyStartedService = false;
        super.onDestroy();
    }
    private double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.f/Math.PI);

        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }
}
