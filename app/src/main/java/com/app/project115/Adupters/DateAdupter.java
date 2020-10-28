package com.app.project115.Adupters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.app.project115.Fragments.StockFragment;
import com.app.project115.Model.Stock;
import com.app.project115.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateAdupter extends RecyclerView.Adapter<DateAdupter.MyViewHolder> {
    private List<Date> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("attachFragment","onClick");
                    attachFragment(R.id.fragment_container, StockFragment.getInstance(textView.getText().toString()),view.getContext(),StockFragment.class.getSimpleName());

                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DateAdupter(List<Date> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DateAdupter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_date, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(mDataset.get(position)));


    }

    public  void attachFragment (int fragmentHolderLayoutId, Fragment fragment, Context context, String tag ) {
        Log.d("attachFragment","attachFragment");
        FragmentManager manager = ( (AppCompatActivity) context ).getSupportFragmentManager ();
        FragmentTransaction ft = manager.beginTransaction ();


            ft.replace ( fragmentHolderLayoutId, fragment, null );
            ft.addToBackStack ( null );
            ft.commit ();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
