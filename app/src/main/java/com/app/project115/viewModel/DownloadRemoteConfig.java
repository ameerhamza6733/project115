package com.app.project115.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.project115.Model.AppConfig;
import com.app.project115.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DownloadRemoteConfig extends AndroidViewModel {
    private MutableLiveData<AppConfig> appConfigLiveDate;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DownloadRemoteConfig downloadRemoteConfig=null;

    public DownloadRemoteConfig(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        appConfigLiveDate=null;
    }

    public LiveData<AppConfig> getAppConfig(){
        if (appConfigLiveDate==null){
            appConfigLiveDate=new MutableLiveData<>();
            database.getReference(Constant.APP_CONFIG)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            appConfigLiveDate.setValue(snapshot.getValue(AppConfig.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            error.toException().printStackTrace();
                            appConfigLiveDate.setValue(null);
                        }
                    });
        }
        return appConfigLiveDate;
    }



     public static DownloadRemoteConfig newInstance(Application application) {

        if (downloadRemoteConfig==null){
            downloadRemoteConfig=new DownloadRemoteConfig(application);
        }

        return downloadRemoteConfig;
    }
}
