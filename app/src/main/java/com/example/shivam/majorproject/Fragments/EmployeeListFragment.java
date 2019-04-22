package com.example.shivam.majorproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.majorproject.R;

import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.model.EmployeeListModel;
import com.example.shivam.majorproject.utils.StringUsed;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmployeeListFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TinyDB tinyDB;
    View view;
    List<EmployeeListModel> employeeListModels;
    Context context;
    private OnListFragmentInteractionListener mListener;
    String type ;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("major");
        employeeListModels = new ArrayList<>();
        type  = tinyDB.getString(StringUsed.key_type);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.employee_list_fragment_item_list, container, false);
        databaseReference.child("employer").child(tinyDB.getObject("config", Configuration.class).getLogin_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> employee_id_list = new ArrayList<>();
                for (DataSnapshot ds :  dataSnapshot.child("employees").getChildren()){

                    if (type.equals(StringUsed.type_employee_list_transaction)){
                        if (ds.getValue(String.class).equals("available")){
                            employee_id_list.add(ds.getKey());
                        }
                    }else if(type.equals(StringUsed.type_employee_list_manage)){
                        employee_id_list.add(ds.getKey());
                    }
                }
                for (String s : employee_id_list){
                    databaseReference.child("employee").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            EmployeeListModel employeeListModel = new EmployeeListModel();

                            employeeListModel.setContact_number(dataSnapshot.child("contact_number").getValue(String.class));
                            employeeListModel.setEmployee_id(dataSnapshot.child("login_id").getValue(String.class));
                            employeeListModel.setName_of_person(dataSnapshot.child("name_of_person").getValue(String.class));

                            employeeListModels.add(employeeListModel);

                            if (view instanceof RecyclerView) {
                                context = view.getContext();
                                RecyclerView recyclerView = (RecyclerView) view;
                                if (mColumnCount <= 1) {
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                } else {
                                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                }
                                recyclerView.setAdapter(new EmployeeListFragmentAdapter(employeeListModels, mListener));

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (view instanceof RecyclerView) {
                    context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new EmployeeListFragmentAdapter(employeeListModels, mListener));

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        tinyDB = new TinyDB(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(EmployeeListModel item);
    }
}
