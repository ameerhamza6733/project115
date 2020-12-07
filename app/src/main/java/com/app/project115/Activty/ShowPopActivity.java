package com.app.project115.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import com.app.project115.Fragments.PopFragment;
import com.app.project115.Fragments.PopFragment2;
import com.app.project115.Model.AppConfig;
import com.app.project115.R;
import com.app.project115.SharedPref;
import com.app.project115.Util;
import com.google.gson.Gson;

import static com.app.project115.MainActivity.isPop31Showing;

public class ShowPopActivity extends AppCompatActivity {

    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig=new Gson().fromJson(SharedPref.read(SharedPref.KEY_APP_CONFIG,""),AppConfig.class);
        String action = getIntent().getAction();
        if (action.equals("showPop")){
            showPop();
        }else  if (action.equals("showPop2")){
            showPop2();
        }else {
            finish();
        }
    }

    private void showPop(){
        // boolean isFirstTime= SharedPref.read(SharedPref.IS_FIREST_TIME,true);

        int popY= SharedPref.read(SharedPref.KEY_COUNT_POP_Y,0);

        if ( appConfig.isEnablePop31() && (Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th")||Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk"))){

            if (popY>=appConfig.getPopY() && appConfig.isEnablePop32()){
                SharedPref.write(SharedPref.KEY_COUNT_POP_Y,0);
                PopFragment2 popFragment= new PopFragment2();
                popFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
                popFragment.showNow(getSupportFragmentManager(),"pop2Fragment");
            }else {
                PopFragment popFragment= new PopFragment();
                popFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.DialogTheme);
                popFragment.showNow(getSupportFragmentManager(),"popFragment");
            }


        }else {

        }

    }
    private void showPop2(){



        if (!isPop31Showing){
            if (appConfig.isEnablePop32() && (Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("th") || Util.getUserCountry(getApplicationContext()).equalsIgnoreCase("pk"))){


                SharedPref.write(SharedPref.KEY_COUNT_POP_Y,0);
                PopFragment2  popFragment= new PopFragment2();
                popFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.DialogTheme);
                popFragment.showNow(getSupportFragmentManager(),"pop2Fragment");



            }
        }

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}