package com.app.project115.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.app.project115.Adupters.PopButtonListAdupter;
import com.app.project115.MainActivity;
import com.app.project115.Model.AppConfig;
import com.app.project115.R;
import com.app.project115.SharedPref;
import com.app.project115.viewModel.DownloadRemoteConfig;
import com.app.project115.worker.ShowPopWorker;
import com.bumptech.glide.Glide;
import com.facebook.share.Share;

import java.util.concurrent.TimeUnit;

public class PopFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private AppConfig appConfig;
    private PopButtonListAdupter  popButtonListAdupter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pop_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recylerviewButtonList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ImageView imageView = view.findViewById(R.id.imageBackGoundPop);
        final ImageButton btClosePop=view.findViewById(R.id.btClosePop);
        SharedPref.write(SharedPref.IS_FIREST_TIME,false);
        int countPopY=SharedPref.read(SharedPref.KEY_COUNT_POP_Y,0);
        SharedPref.write(SharedPref.KEY_COUNT_POP_Y,countPopY+1);
        DownloadRemoteConfig model = DownloadRemoteConfig.newInstance(getActivity().getApplication());
        model.getAppConfig().observe(this, new Observer<AppConfig>() {
            @Override
            public void onChanged(AppConfig appConfig) {
                PopFragment.this.appConfig=appConfig;
                Glide.with(PopFragment.this).load(appConfig.getPopBackGroundUrl()).into(imageView);
                popButtonListAdupter = new PopButtonListAdupter(appConfig.getPopButtonList());
                recyclerView.setAdapter(popButtonListAdupter);

            }
        });
        btClosePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dismiss();
              getActivity().finish();


            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity.isPop31Showing=true;
    }

    @Override
    public void onDetach() {
        if (appConfig!=null){
            MainActivity.isPop31Showing=false;
            Log.d("popFragment","show pop after "+appConfig.getPopX());
            Data  data = new Data.Builder().putInt("seconds", appConfig.getPopX())
                    .build();
            ;

            OneTimeWorkRequest saveRequest =
                    new OneTimeWorkRequest.Builder(ShowPopWorker.class)
                            .setInitialDelay(appConfig.getPopX(),TimeUnit.SECONDS)
                            .setInputData(data)
                            .addTag("ShowPopWorker")
                            .build();
            WorkManager.getInstance(getActivity().getApplicationContext()).enqueueUniqueWork("ShowPopWorker", ExistingWorkPolicy.KEEP,saveRequest);

        }
        super.onDetach();
    }


}
