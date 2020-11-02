package com.app.project115;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.WorkManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.project115.Fragments.DateListFragment;
import com.app.project115.Fragments.GenrateLuckyNumberFragment;
import com.app.project115.Fragments.PopFragment;
import com.app.project115.Fragments.PopFragment2;
import com.app.project115.Fragments.WebViewFragment;
import com.app.project115.Model.AppConfig;
import com.app.project115.viewModel.DownloadRemoteConfig;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    boolean luckNumberState=false;
    boolean checkResultState=false;
    private AppConfig appConfig;
    private ImageView imageViewFixedBanner;
    private DownloadRemoteConfig downloadRemoteConfig;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            showPop();
        }
    };
    private Button btLuckyNumber;
    private Button btCheckResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("show-pop"));
         btLuckyNumber= findViewById(R.id.btLuckyNumber);
        btCheckResult= findViewById(R.id.btCheckResult);
        ImageView imageView = findViewById(R.id.imageBackGound);
         imageViewFixedBanner= findViewById(R.id.imageViewFixedBanner);
        Log.d("MainActivty",""+Util.getUserCountry(getApplicationContext()));
        Glide.with(this).load(R.drawable.splash_bg).into(imageView);
        downloadRemoteConfig = DownloadRemoteConfig.newInstance(getApplication());
        downloadRemoteConfig.getAppConfig().observe(this, new Observer<AppConfig>() {
            @Override
            public void onChanged(AppConfig appConfig) {
                MainActivity.this.appConfig=appConfig;
                if (appConfig.getAppCodeNumber()==1){
                    btLuckyNumber.setVisibility(View.GONE);
                    btCheckResult.setVisibility(View.GONE);
                    attachFragment(R.id.fragment_container,WebViewFragment.newInstance(appConfig.getWebViewUrl()),getApplicationContext(),"WebViewFragment");

                }else  if (appConfig.getAppCodeNumber()==2){
                    checkResultState=true;
                    btCheckResult.callOnClick();
                }
                showBanner();
                showPop();
            }
        });



        btLuckyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attachFragment(R.id.fragment_container,new GenrateLuckyNumberFragment(),getApplicationContext(),"GenrateLuckyNumberFragment");

                if (luckNumberState){
                    luckNumberState=false;
                    checkResultState=true;
                    btCheckResult.setBackground(getDrawable(R.drawable.button_seletor_back_gound_transparent));
                    btLuckyNumber.setBackground(getDrawable(R.drawable.button_seletor_back_gound));

                }else {
                    checkResultState=false;
                    luckNumberState=true;
                    btCheckResult.setBackground(getDrawable(R.drawable.button_seletor_back_gound));
                    btLuckyNumber.setBackground(getDrawable(R.drawable.button_seletor_back_gound_transparent));

                }
            }
        });
        btCheckResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attachFragment(R.id.fragment_container,DateListFragment.newInstance(),getApplicationContext(),"DateListFragment");

                if (checkResultState){
                    checkResultState=false;
                    luckNumberState=true;
                    btCheckResult.setBackground(getDrawable(R.drawable.button_seletor_back_gound));
                    btLuckyNumber.setBackground(getDrawable(R.drawable.button_seletor_back_gound_transparent));

                }else {
                    luckNumberState=false;
                    checkResultState=true;
                    btLuckyNumber.setBackground(getDrawable(R.drawable.button_seletor_back_gound));
                    btCheckResult.setBackground(getDrawable(R.drawable.button_seletor_back_gound_transparent));

                }
            }
        });

        imageViewFixedBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig!=null){
                    if (appConfig.isBannerOpenNewBrowser()){
                        Util.openBrowser(appConfig.getFixBannerUrl(),getApplicationContext());
                    }else {
                        attachFragment(R.id.fragment_container,WebViewFragment.newInstance(appConfig.getFixBannerUrl()),getApplicationContext(),"WebViewFragment");

                    }
                }
            }
        });
    }
    public  void attachFragment (int fragmentHolderLayoutId, Fragment fragment, Context context, String tag ) {
        Log.d("attachFragment","attachFragment");
        FragmentManager manager = getSupportFragmentManager ();
        FragmentTransaction ft = manager.beginTransaction ();

        if (manager.findFragmentByTag ( tag ) == null) {
            ft.replace ( fragmentHolderLayoutId, fragment, tag );
            ft.addToBackStack ( tag );
            ft.commit ();
        }
        else {
           ft.replace(fragmentHolderLayoutId, manager.findFragmentByTag ( tag )).commit();
        }
    }

    private void showPop(){
        boolean isFirstTime= SharedPref.read(SharedPref.IS_FIREST_TIME,true);
        int popY=SharedPref.read(SharedPref.KEY_COUNT_POP_Y,0);

        if (appConfig.isEnablePop31() && Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th")){
            if (isFirstTime){
                if (popY>=appConfig.getPopY()){
                    SharedPref.write(SharedPref.KEY_COUNT_POP_Y,0);
                    PopFragment2  popFragment= new PopFragment2();
                    popFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.DialogTheme);
                    popFragment.showNow(getSupportFragmentManager(),"popFragment");
                }else {
                    PopFragment  popFragment= new PopFragment();
                    popFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.DialogTheme);
                    popFragment.showNow(getSupportFragmentManager(),"popFragment");
                }
            }

        }

    }

    public ImageView getImageViewFixedBanner() {
        return imageViewFixedBanner;
    }

    public void hindeButton(){
      btCheckResult.setVisibility(View.GONE);
      btLuckyNumber.setVisibility(View.GONE);
    }
    public void showButton(){
      btCheckResult.setVisibility(View.VISIBLE);
      btLuckyNumber.setVisibility(View.VISIBLE);
    }



    private void  showBanner(){

        if (appConfig.isShowFixBanner() && Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk")){
            imageViewFixedBanner.setVisibility(View.VISIBLE);
            Glide.with(this).load(appConfig.getFixBannerImageUrl()).into(imageViewFixedBanner);
        }else {
            imageViewFixedBanner.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        WorkManager.getInstance(this).cancelAllWorkByTag("ShowPopWorker");
        downloadRemoteConfig.cleanUp();
        super.onDestroy();
    }
}