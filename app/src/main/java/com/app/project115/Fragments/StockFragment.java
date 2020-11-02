package com.app.project115.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.app.project115.Adupters.StockAdapter;
import com.app.project115.MainActivity;
import com.app.project115.Model.AppConfig;
import com.app.project115.Model.Stock;
import com.app.project115.R;
import com.app.project115.viewModel.DownloadRemoteConfig;
import com.app.project115.viewModel.StockViewModel;

import java.util.List;

public class StockFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private StockAdapter stockAdapter;
    private static StockFragment stockFragment;
    private ImageView imageViewBanner;
    private ImageView imageBackGound;
    private String TAG="StockFragment";
    private AppConfig appConfig;
    private TextView tvDate;

    public static StockFragment getInstance(String date){
        if (stockFragment==null){
            stockFragment=new StockFragment();
        }
        Bundle bundle= new Bundle();
        bundle.putString("date",date);
        stockFragment.setArguments(bundle);
        return stockFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          View view= inflater.inflate(R.layout.stock_fragment, container, false);
        recyclerView=view.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar=view.findViewById(R.id.progressBar);
        imageBackGound=view.findViewById(R.id.backGoundImage);
        tvDate=view.findViewById(R.id.tvDate);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).hindeButton();
        String date = getArguments().getString("date");
        tvDate.setText(date);
        DownloadRemoteConfig downloadRemoteConfig = new DownloadRemoteConfig(this.getActivity().getApplication());
        downloadRemoteConfig.getAppConfig().observe(this, new Observer<AppConfig>() {
            @Override
            public void onChanged(AppConfig appConfig) {
                StockFragment.this.appConfig=appConfig;

            }
        });
        final StockViewModel stockViewModel= new ViewModelProvider(this).get(StockViewModel.class);
        stockViewModel.getGoldenStock(Volley.newRequestQueue(getActivity().getApplicationContext()),date).observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stock) {
                progressBar.setVisibility(View.INVISIBLE);

                   if (stock!=null){
                       stockAdapter=new StockAdapter(stock);
                       recyclerView.setAdapter(stockAdapter);
                   }else {
                       Toast.makeText(getContext(),"Error: Please try again ",Toast.LENGTH_LONG).show();
                       Log.d(TAG,"error");
                   }



            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            ((MainActivity)getActivity()).showButton();
        }catch (Exception e){

        }
    }
}
