package com.app.project115.worker;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.app.project115.SharedPref;

import java.util.concurrent.TimeUnit;

public class ShowPopWorker extends Worker {

    public ShowPopWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Intent intent = new Intent("show-pop");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        int popX= getInputData().getInt("seconds",0);
       boolean isPopShowing= SharedPref.read(SharedPref.KEY_IS_POP_SHOW,false);
        if (popX!=0 && !isPopShowing){
            Data data = new Data.Builder().putInt("seconds", popX)
                    .build();
            ;

            OneTimeWorkRequest saveRequest =
                    new OneTimeWorkRequest.Builder(ShowPopWorker.class)
                            .setInitialDelay(popX, TimeUnit.SECONDS)
                            .setInputData(data)
                            .addTag("ShowPopWorker")
                            .build();
            WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork("ShowPopWorker", ExistingWorkPolicy.KEEP,saveRequest);
        }
        return Result.success();
    }
}
