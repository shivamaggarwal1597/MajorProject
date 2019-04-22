package com.example.shivam.majorproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.majorproject.model.Configuration;
import com.example.shivam.majorproject.model.Transaction;
import com.example.shivam.majorproject.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class
EmployeeAllTransactionFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<Transaction> transactions;
    Context context;
    TinyDB tinyDB ;
    View view;
    List<String> transactions_string_list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private OnListFragmentInteractionListener mListener;


    public EmployeeAllTransactionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactions = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        transactions_string_list = new ArrayList<>();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employee_all_transaction_list, container, false);
        Configuration configuration  = tinyDB.getObject("config",Configuration.class);
        if (tinyDB.getString("status_transaction_type").equals("all")){
           transactions.clear();
            databaseReference
                    .child("major")
                    .child("employee")
                    .child(configuration.getLogin_id())
                    .child("transactions")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("Block :","1#");
                    for (DataSnapshot ps :  dataSnapshot.getChildren()){
                        //Transaction ts = ps.getValue(Transaction.class);
                        //transactions.add(new Transaction(ts.getTransaction_id(),ts.getCommodity(),ts.getQuantity(),ts.getStart_date(),ts.getEnd_date(),ts.getDue_date(),ts.getCreated_by()));
                        transactions_string_list.add(ps.getKey());
                        for(String s : transactions_string_list){
                            databaseReference.child("major").child("transaction").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Transaction t  = dataSnapshot.getValue(Transaction.class);
                                    transactions.add(t);
                                    if (view instanceof RecyclerView) {
                                        Log.e("Block :","1##");
                                        context = view.getContext();
                                        RecyclerView recyclerView = (RecyclerView) view;
                                        if (mColumnCount <= 1) {
                                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                            Log.e("Block: ","2");
                                        } else {
                                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                        }
                                        Log.e("Block","3");
                                        recyclerView.setAdapter(new EmployeeAllTransactionAdapter(transactions, mListener));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        if (view instanceof RecyclerView) {
                            Log.e("Block :","1##");
                            context = view.getContext();
                            RecyclerView recyclerView = (RecyclerView) view;
                            if (mColumnCount <= 1) {
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                Log.e("Block: ","2");
                            } else {
                                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                            }
                            Log.e("Block","3");
                            recyclerView.setAdapter(new EmployeeAllTransactionAdapter(transactions, mListener));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else if (tinyDB.getString("status_transaction_type").equals("transaction")){
            transactions.clear();
            transactions_string_list.clear();
            databaseReference
                    .child("major")
                    .child("employer")
                    .child(configuration.getLogin_id())
                    .child("transactions")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("Block :","1#");

                            for (DataSnapshot ps :  dataSnapshot.getChildren()){
                                String ts = ps.getKey();
                                transactions_string_list.add(ts);
                                for (String s : transactions_string_list){
                                    databaseReference
                                            .child("major")
                                            .child("transaction")
                                            .child(s)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Transaction t = dataSnapshot.getValue(Transaction.class);
                                                    transactions.add(t);
                                                    if (view instanceof RecyclerView) {
                                                        Log.e("Block :","1##");
                                                        context = view.getContext();
                                                        RecyclerView recyclerView = (RecyclerView) view;
                                                        if (mColumnCount <= 1) {
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                                            Log.e("Block: ","2");
                                                        } else {
                                                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                                        }
                                                        Log.e("Block","3");
                                                        recyclerView.setAdapter(new EmployeeAllTransactionAdapter(transactions, mListener));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                }
                                if (view instanceof RecyclerView) {
                                    Log.e("Block :","1##");
                                    context = view.getContext();
                                    RecyclerView recyclerView = (RecyclerView) view;
                                    if (mColumnCount <= 1) {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        Log.e("Block: ","2");
                                    } else {
                                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                                    }
                                    Log.e("Block","3");
                                    recyclerView.setAdapter(new EmployeeAllTransactionAdapter(transactions, mListener));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tinyDB = new TinyDB(context);
        this.context =  context;
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
        void onListFragmentInteraction(Transaction item);
    }
}
