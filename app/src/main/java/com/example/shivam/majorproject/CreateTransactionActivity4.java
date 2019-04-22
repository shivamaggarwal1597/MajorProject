package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shivam.majorproject.Fragments.EmployeeListFragment;
import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.model.EmployeeListModel;
import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.StringUsed;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateTransactionActivity4 extends AppCompatActivity
        implements EmployeeListFragment.OnListFragmentInteractionListener {
    EmployeeListFragment employeeListFragment;
    TinyDB tinyDB;
    String date;
    Transaction transaction;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Boolean  two = false,three =false,four =false,five = false,six =false;
    SimpleDateFormat simpleDateFormat;
    //SELECT EMPLOYEE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction4);
        tinyDB = new TinyDB(CreateTransactionActivity4.this);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date =  simpleDateFormat.format(new Date()).trim();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("major");
        employeeListFragment =  new EmployeeListFragment();
        tinyDB.putString(StringUsed.key_type,StringUsed.type_employee_list_transaction);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,employeeListFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onListFragmentInteraction(EmployeeListModel item) {
        //Intent intent = new Intent(CreateTransactionActivity4.this, CreateTransactionActivity5.class);
        transaction = tinyDB.getObject("interim_transaction",Transaction.class);
        Configuration c = tinyDB.getObject("config",Configuration.class);
        transaction.setEmployee_id(item.getEmployee_id());
        transaction.setEmployer_id(c.getLogin_id());
        transaction.setStart_date(myDateFormatter(date));
        String key  = databaseReference.child("transactions").push().getKey();
        transaction.setTransaction_id(key);
        //TODO :  Show Material Dialog.
        databaseReference
                .child("transaction")
                .child(transaction.getTransaction_id())
                .setValue(transaction)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {




                databaseReference
                        .child("employee")
                        .child(transaction.getEmployee_id())
                        .child("current_transaction_id")
                        .setValue(transaction.getTransaction_id())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        two = true;
                        move_on();
                    }
                });

                databaseReference
                        .child("employee")
                        .child(transaction.getEmployee_id())
                        .child("transactions")
                        .child(transaction.getTransaction_id())
                        .setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        three  = true;
                        move_on();
                    }
                }); // True for Transactions Still Active and False for Done

                databaseReference
                        .child("employee")
                        .child(transaction.getEmployee_id())
                        .child("availability")
                        .setValue(false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                six  = true;
                                move_on();
                            }
                        });//Checks if any transaction is assigned to employee or not, if true --> no transaction assigned, while false means he is working.

                databaseReference
                        .child("employer")
                        .child(transaction.getEmployer_id())
                        .child("transactions")
                        .child(transaction.getTransaction_id())
                        .setValue(false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        four = true;
                        move_on();
                    }
                }); //True for Transactions Completed and False for the one's still Pending

                databaseReference
                        .child("employer")
                        .child(transaction.getEmployer_id())
                        .child("employees")
                        .child(transaction.getEmployee_id())
                        .setValue("unavailable"). //  available ,  unavailable ,  left
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        five = true;
                        move_on();
                    }
                }); //True for Transactions Completed and False for the one's still Pending


            }
        });


    }

    public void move_on(){
        //TODO : Dismiss Material Dialog
        if ( two && three && four && five && six){
            Intent intent = new Intent(CreateTransactionActivity4.this,DashboardEmployerActivity.class);
            startActivity(intent);

            finish();
        }
    }
    public String myDateFormatter(String date){


        String a = date.replace("-","_");
        return a;
    }
}
