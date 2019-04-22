package com.example.shivam.majorproject;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivam.majorproject.EmployeeAllTransactionFragment.OnListFragmentInteractionListener;

import com.example.shivam.majorproject.model.Transaction;

import java.util.List;

public class EmployeeAllTransactionAdapter extends RecyclerView.Adapter<EmployeeAllTransactionAdapter.ViewHolder> {

    private final List<Transaction> mValues;
    private final OnListFragmentInteractionListener mListener;

    public EmployeeAllTransactionAdapter(List<Transaction> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction_all, parent, false);
        Log.e("Block","4");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.trans_id.setText("Transaction ID : "+ mValues.get(position).getTransaction_id());
        holder.commodity.setText("Commodity: "+mValues.get(position).getCommodity());
        holder.quantity.setText("Quantity: "+ mValues.get(position).getQuantity());
        holder.start_date.setText("Start Date : "+ mValues.get(position).getStart_date());
        holder.end_date.setText("End Date : "+ mValues.get(position).getCheck_out_date());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView trans_id;
        public final TextView commodity;
        public final TextView quantity;
        public final TextView start_date;
        public final TextView end_date;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            trans_id = (TextView) view.findViewById(R.id.transaction_id);
            commodity = (TextView) view.findViewById(R.id.commodity);
            quantity = (TextView)view.findViewById(R.id.quantity);
            start_date =  (TextView)view.findViewById(R.id.start_date);
            end_date =  (TextView)view.findViewById(R.id.end_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + commodity.getText() + "'";
        }
    }
}
