package com.app.project115.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.project115.Model.AppConfig;
import com.app.project115.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DownloadRemoteConfig extends AndroidViewModel {
    private MutableLiveData<AppConfig> appConfigLiveDate;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DownloadRemoteConfig downloadRemoteConfig=null;
    private ValueEventListener valueEventListener;
    private DatabaseReference appConfigRef;
    private String TAG="DownloadRemoteConfig";

    public DownloadRemoteConfig(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        appConfigLiveDate=null;
    }

    public LiveData<AppConfig> getAppConfig(){
        if (valueEventListener==null){
            valueEventListener= new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG,"onDateaChange");
                    appConfigLiveDate.setValue(snapshot.getValue(AppConfig.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    error.toException().printStackTrace();
                    appConfigLiveDate.setValue(null);
                }
            };
        }
        if (appConfigLiveDate==null){
            appConfigLiveDate=new MutableLiveData<>();
           appConfigRef= database.getReference(Constant.APP_CONFIG);
           appConfigRef.addValueEventListener(valueEventListener);

        }
        return appConfigLiveDate;
    }


    public void  cleanUp(){
        if (valueEventListener!=null && appConfigRef!=null){
            appConfigRef.removeEventListener(valueEventListener);
            valueEventListener=null;
            appConfigRef=null;
        }
    }

     public static DownloadRemoteConfig newInstance(Application application) {

        if (downloadRemoteConfig==null){
            downloadRemoteConfig=new DownloadRemoteConfig(application);
        }

        return downloadRemoteConfig;
    }
}
