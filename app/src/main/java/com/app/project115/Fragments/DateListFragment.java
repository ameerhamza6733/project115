package com.app.project115.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.app.project115.Adupters.DateAdupter;
import com.app.project115.MainActivity;
import com.app.project115.Model.AppConfig;
import com.app.project115.R;
import com.app.project115.viewModel.DateListViewModel;

import java.util.Date;
import java.util.List;

public class DateListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DateAdupter mAdapter;
    private ImageView imageView;
    private ImageView imageViewBanner;
    private AppConfig appConfig;

    private String TAG="DateListFragment";

    public static DateListFragment newInstance() {

        DateListFragment fragment = new DateListFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerViewDate);


        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DateListViewModel dateListViewModel= new ViewModelProvider(this).get(DateListViewModel.class);
        dateListViewModel.genrateLast10Days().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dates) {
                mAdapter = new DateAdupter(dates);
                recyclerView.setAdapter(mAdapter);
            }
        });





    }


}