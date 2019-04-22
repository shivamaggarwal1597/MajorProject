package com.example.shivam.majorproject;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shivam.majorproject.Fragments.EmployeeListFragment;
import com.example.shivam.majorproject.model.EmployeeListModel;
import com.example.shivam.majorproject.utils.StringUsed;
import com.example.shivam.majorproject.utils.TinyDB;

public class ManageEmployeeActivity extends AppCompatActivity
        implements EmployeeListFragment.OnListFragmentInteractionListener{
    TinyDB tinyDB;
    Button add_employee_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);
        tinyDB = new TinyDB(ManageEmployeeActivity.this);
        tinyDB.putString(StringUsed.key_type,StringUsed.type_employee_list_manage);
        EmployeeListFragment employeeListFragment =  new EmployeeListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_manage_employee,employeeListFragment);
        fragmentTransaction.commit();
        add_employee_bt = (Button)findViewById(R.id.add_employee_bt);
        add_employee_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageEmployeeActivity.this,AddEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(EmployeeListModel item) {
        //Show details of this employee on next page.

    }
}
