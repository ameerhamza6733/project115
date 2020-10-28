package com.app.project115.viewModel;

import android.content.Context;
import android.telephony.TelephonyManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public  class DateListViewModel extends ViewModel {
    private MutableLiveData<List<Date>> listDateMutableLiveData;
    private List<Date> dateList=new ArrayList<>();
    public LiveData<List<Date>> genrateLast10Days(){
        listDateMutableLiveData=new MutableLiveData<>();
        for (int i=10; i>0; i--){

            dateList.add(new Date(System.currentTimeMillis()- TimeUnit.DAYS.toMillis(i)));
        }
        listDateMutableLiveData.setValue(dateList);
       return listDateMutableLiveData;
    }


}
