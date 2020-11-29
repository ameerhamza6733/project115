package com.app.project115.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.project115.Fragments.WebViewFragment;
import com.app.project115.R;

public class WebActivity extends AppCompatActivity {
    private int doubleBackToExitPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        attachFragment(R.id.fragment_container, WebViewFragment.newInstance(),getApplicationContext(),"WebViewFragment");
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
                  finish();
                }
            }else {
              super.onBackPressed();

            }

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;

            }
        }, 1500);
    }
}