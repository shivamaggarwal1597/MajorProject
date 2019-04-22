package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.model.CreateEmployeeModel;
import com.example.shivam.majorproject.utils.StringUsed;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText emp_name_et,emp_contact_et;
    Button create_bt;
    Configuration configuration;
    TinyDB tinyDB;
    CreateEmployeeModel createEmployeeModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        emp_contact_et = (EditText)findViewById(R.id.employee_contact_et);
        emp_name_et = (EditText)findViewById(R.id.employee_name_et);
        tinyDB = new TinyDB(AddEmployeeActivity.this);
        configuration = tinyDB.getObject(StringUsed.config_string_value, Configuration.class);
        create_bt = (Button)findViewById(R.id.create_employee_bt);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("major");
        createEmployeeModel = new CreateEmployeeModel();
        create_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emp_contact_et.getText().toString().trim().length()>0 && emp_name_et.getText().toString().trim().length()>0){
                    createEmployeeModel.setContact_number(emp_contact_et.getText().toString());
                    createEmployeeModel.setAvailability(true);
                    createEmployeeModel.setEmployer_id(configuration.getLogin_id());
                    String id = emp_name_et.getText().toString().substring(0,2) + emp_contact_et.getText().toString().substring(0,2);
                    createEmployeeModel.setLogin_id(id);
                    createEmployeeModel.setName_of_person(emp_name_et.getText().toString());
                    databaseReference.child("employee").child(id).setValue(createEmployeeModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            databaseReference
                                    .child("employer")
                                    .child(configuration.getLogin_id())
                                    .child("employees")
                                    .child(createEmployeeModel.getLogin_id())
                                    .setValue("available", new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference dbr) {
                                            Intent intent = new Intent(AddEmployeeActivity.this, DashboardEmployerActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                        }
                    });

                }
                else {
                    Toast.makeText(AddEmployeeActivity.this,"Please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
