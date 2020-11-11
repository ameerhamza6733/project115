package com.app.project115.Fragments;

import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.project115.MainActivity;
import com.app.project115.Model.AppConfig;
import com.app.project115.R;
import com.app.project115.SharedPref;

import static com.app.project115.Util.isNetworkAvailable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment  {

    private AppConfig appConfig;
    private WebView webView;
    private String TAG="webviewfrag";

    public WebViewFragment() {

    }


    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       try {
           ((MainActivity)getActivity()).hindeButton();
           ((MainActivity)getActivity()).getImageViewFixedBanner().setVisibility(View.GONE);
       }catch (Exception e){

       }
    }





    public WebView getWebView() {
        return webView;
    }

    @Override
    public void onPause() {
       try {
           ((MainActivity)getActivity()).showButton();
           ((MainActivity)getActivity()).getImageViewFixedBanner().setVisibility(View.VISIBLE);
       }catch (Exception e){
           e.printStackTrace();
       }
        super.onPause();
    }

    @Override
    public void onDetach() {

       try {
           ((MainActivity)getActivity()).showButton();
           ((MainActivity)getActivity()).getImageViewFixedBanner().setVisibility(View.VISIBLE);
       }catch (Exception e){

       }
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_web_view, container, false);
         webView=root.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);   // localStorage

// e.g., if your package is www.myapp.whatever;
        webSettings.setDatabasePath("/data/data/com.app.project115/databases/");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            });

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = SharedPref.read(SharedPref.KEY_URL_WEBVIEW,"");
        updateUi(url);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void updateUi(String url){
        Log.d(TAG,"url +"+url);
        webView.loadUrl(url);
    }
}