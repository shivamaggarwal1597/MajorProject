package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
Intent intent ;
    String code;
    Button submit_button;
    String login_id,password;
    TinyDB tinyDB;
    EditText username_et,password_et;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = getIntent();
        tinyDB = new TinyDB(LoginActivity.this);
        username_et = (EditText)findViewById(R.id.login_screen_user_name_edit_text);
        password_et  = (EditText)findViewById(R.id.login_screen_password_edit_text);
        submit_button = (Button) findViewById(R.id.login_screen_submit_button);
        code  = intent.getStringExtra("code");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //databaseReference.child(code).child()
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (username_et.getText().toString().length() != 0 && password_et.getText().toString().length() !=0){
                 login_id = username_et.getText().toString();
                 password = password_et.getText().toString();
                databaseReference.child("major").child(code).child(login_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                     if (dataSnapshot.exists()){
                         //Data exists
                         if (password.equals(dataSnapshot.child("password").getValue(String.class)) && dataSnapshot.child("is_active").getValue(Boolean.class)){
                             //User Verified, Store in Shared Preferences.
                             Configuration configuration = new Configuration();
                             configuration.setLogin_id(login_id);
                             configuration.setLogged_in(true);
                             configuration.setLogin_password(password);
                             configuration.setType_of_person(code);
                             configuration.setContact_number(dataSnapshot.child("contact_number").getValue(String.class));
                             configuration.setName_of_person(dataSnapshot.child("name_of_person").getValue(String.class));
                             tinyDB.putObject("config",configuration);
                             switch (code){
                                 case "employee":
                                     Intent intent = new Intent(LoginActivity.this, DashboardEmployeeActivity.class);
                                     startActivity(intent);
                                     finish();
                                         break;
                                 case "employer":
                                     Intent intent1 = new Intent(LoginActivity.this, DashboardEmployerActivity.class);
                                     startActivity(intent1);
                                     finish();
                                     break;
                                 default:
                                     Toast.makeText(LoginActivity.this, "Error ",Toast.LENGTH_LONG).show();
                             }

                         }


                     }  else {
                         Toast.makeText(LoginActivity.this, " This Username Doesn't Exist", Toast.LENGTH_LONG).show();
                     }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }   else {

            }
            }
        });



    }
}
