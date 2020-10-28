package com.app.project115.Activty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.project115.Model.AppConfig;
import com.app.project115.MainActivity;
import com.app.project115.R;

import com.app.project115.SharedPref;
import com.app.project115.Util;
import com.app.project115.viewModel.DownloadRemoteConfig;
import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    boolean singleBack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPref.init(getApplicationContext());
        ImageView imageViewSplash=findViewById(R.id.bg_splash_image);
         progressBar= findViewById(R.id.progressBar);
        Glide.with(this).load(R.drawable.splash_bg).into(imageViewSplash);
         getDate();
        if (!Util.isNetworkAvailable(getApplicationContext())){
            progressBar.setVisibility(View.INVISIBLE);
            noInternet();
        }

    }

    private void getDate(){

        DownloadRemoteConfig model = DownloadRemoteConfig.newInstance(getApplication());
        model.getAppConfig().observe(this, new Observer<AppConfig>() {
            @Override
            public void onChanged(AppConfig appConfig) {
                progressBar.setVisibility(View.INVISIBLE);
                if (appConfig==null){
                    noInternet();
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void noInternet(){
        new AlertDialog.Builder(this,R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Please connect internet")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
                if (Util.isNetworkAvailable(getApplicationContext())){
                    getDate();

                }else {
                    noInternet();
                }
            }
        }).create()
                .show();
    }


}