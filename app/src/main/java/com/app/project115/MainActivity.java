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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.project115.Activty.ShowPopActivity;
import com.app.project115.Activty.WebActivity;
import com.app.project115.Fragments.DateListFragment;
import com.app.project115.Fragments.GenrateLuckyNumberFragment;
import com.app.project115.Fragments.PopFragment;
import com.app.project115.Fragments.PopFragment2;
import com.app.project115.Fragments.WebViewFragment;
import com.app.project115.Model.AppConfig;
import com.app.project115.viewModel.DownloadRemoteConfig;
import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.share.Share;
import com.google.gson.Gson;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    public static boolean isPop31Showing=false;
    public static boolean isIsPop32Showing=false;

    private int doubleBackToExitPressed;
    boolean luckNumberState=false;
    boolean checkResultState=false;
    private AppConfig appConfig;
    private ImageView imageViewFixedBanner;
    private ImageButton btHome;
    private ImageButton btRefresh;
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
    protected void onStart() {
        super.onStart();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(getApplicationContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("show-pop"));
         btLuckyNumber= findViewById(R.id.btLuckyNumber);
        btCheckResult= findViewById(R.id.btCheckResult);
        ImageView imageView = findViewById(R.id.imageBackGound);
         imageViewFixedBanner= findViewById(R.id.imageViewFixedBanner);
         btHome=findViewById(R.id.btHome);
         btRefresh=findViewById(R.id.imageButton);
        Log.d("MainActivty",""+Util.getUserCountry(getApplicationContext()));
        Glide.with(this).load(R.drawable.splash_bg).into(imageView);
        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showButton();
                btCheckResult.callOnClick();
            }
        });
        downloadRemoteConfig = DownloadRemoteConfig.newInstance(getApplication());
        downloadRemoteConfig.getAppConfig().observe(this, new Observer<AppConfig>() {
            @Override
            public void onChanged(AppConfig appConfig) {

                if (appConfig!=null){
                    MainActivity.this.appConfig=appConfig;
                    SharedPref.write(SharedPref.KEY_APP_CONFIG,new Gson().toJson(appConfig));
                    if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)==null){
                        if (appConfig.getAppCodeNumber()==1){

                            SharedPref.write(SharedPref.KEY_URL_WEBVIEW,appConfig.getWebViewUrl());
                            startActivity(new Intent(MainActivity.this, WebActivity.class));
                            finish();
                        }else  if (appConfig.getAppCodeNumber()==2){
                            checkResultState=true;
                            btCheckResult.callOnClick();
                        }
                        showBanner();
                        showPop();
                        showPop2();
                    }else {
                       // Toast.makeText(getApplicationContext(),"new settings from db",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Error: unable to get app config",Toast.LENGTH_LONG).show();
                }
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
                        SharedPref.write(SharedPref.KEY_URL_WEBVIEW,appConfig.getFixBannerUrl());
                        startActivity(new Intent(MainActivity.this, WebActivity.class));
                    }
                }
            }
        });
    }

    private void showPop2() {
        if (!isPop31Showing){
            if ((appConfig.isEnablePop31() && appConfig.isEnablePop32()) && (Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th") || Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk"))) {

                Intent intent = new Intent(this, ShowPopActivity.class);
                intent.setAction("showPop2");
                startActivity(intent);
            }
        }
    }

    private void showPop() {
        if ( appConfig.isEnablePop31() && (Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th")||Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk"))) {

            isPop31Showing = true;
            Intent intent = new Intent(this, ShowPopActivity.class);
            intent.setAction("showPop");
            startActivity(intent);
        }
    }

    public ImageButton getBtRefresh() {
        return btRefresh;
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

        if (appConfig.isShowFixBanner() && (Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th") || Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk"))){
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




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if(f instanceof WebViewFragment){
               WebViewFragment webViewFragment= ((WebViewFragment) f);
              if (  webViewFragment.getWebView().canGoBack()){
                  webViewFragment.getWebView().goBack();
              }else {
                  super.onBackPressed();
              }
            }else {
                if (getSupportFragmentManager().getBackStackEntryCount()>1)
                super.onBackPressed();
            }

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;

            }
        }, 1000);
    }
}