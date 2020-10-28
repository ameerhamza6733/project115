package com.app.project115.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.project115.Model.GoldenStock;
import com.app.project115.Model.PinkStock;
import com.app.project115.Model.Stock;
import com.app.project115.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends ViewModel {
    private String TAG="StockViewModel";
    private RequestQueue queue ;
    private MutableLiveData<List<Stock>> mutableLiveData;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest1;
    List<Stock> stockList= new ArrayList<>();
    public LiveData<List<Stock>> getGoldenStock(RequestQueue requestQueue,String date){
        String url="https://7wg.org/LottoParser/ruay.php?bet_type=YEEKEE&open_dt="+date;
        String urlYEEKEE="https://7wg.org/LottoParser/ruay.php?bet_type=STOCK&open_dt="+date;
        Log.d(TAG,"get golden stock "+url);
        Log.d(TAG,"counry stock "+url);
        this.requestQueue=requestQueue;
        if (mutableLiveData==null){
            mutableLiveData= new MutableLiveData<>();
            this.queue=requestQueue;
           jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, urlYEEKEE, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        JSONArray jsonArray= response.getJSONArray("data");

                        for (int i =0; i<jsonArray.length();i++){
                            String name= jsonArray.getJSONObject(i).getString("bet_name");
                            String date=jsonArray.getJSONObject(i).getString("open_dt");
                            int threeDigits=jsonArray.getJSONObject(i).getInt("result_bon_3");
                            int twoDigits=jsonArray.getJSONObject(i).getInt("result_lang_2");
                            GoldenStock countryStock = new GoldenStock(name,date,threeDigits,twoDigits);

                            countryStock.setType(5);
                            stockList.add(countryStock);
                        }


                        mutableLiveData.setValue(stockList);
                    }catch (Exception e){
                        e.printStackTrace();
                        mutableLiveData.setValue(null);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d(TAG,"error : "+error.getMessage());
                    mutableLiveData.setValue(null);
                }
            });
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        JSONArray jsonArray= response.getJSONArray("data");
                        String name= jsonArray.getJSONObject(jsonArray.length()-1).getString("bet_name");
                        String date=jsonArray.getJSONObject(jsonArray.length()-1).getString("open_dt");
                        int threeDigits=jsonArray.getJSONObject(jsonArray.length()-1).getInt("result_bon_3");
                        int twoDigits=jsonArray.getJSONObject(jsonArray.length()-1).getInt("result_lang_2");
                        GoldenStock goldenStock = new GoldenStock(name,date,threeDigits,twoDigits);
                        PinkStock pinkStock= new PinkStock(date,"วาดประหยัด", Util.getRand(100,999),Util.getRand(10,99),"หมายเลขที่ชนะ 6 หลัก","#FF1493");
                        PinkStock blueStock= new PinkStock(date,"TGS วาด", Util.getRand(100,999),Util.getRand(10,99),"หมายเลขที่ชนะ","#0000FF");
                        blueStock.setWiningNumber(String.valueOf(Util.getRand(100000,999999)));
                        pinkStock.setWiningNumber(String.valueOf(Util.getRand(100000,999999))+","+Util.getRand(100000,999999));

                        pinkStock.setType(3);
                        goldenStock.setType(1);
                        blueStock.setType(4);

                        stockList.add(goldenStock);
                        stockList.add(pinkStock);
                        stockList.add(blueStock);


                        queue.add(jsonObjectRequest1);
                    }catch (Exception e){
                        e.printStackTrace();
                        mutableLiveData.setValue(null);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d(TAG,"error : "+error.getMessage());
                    mutableLiveData.setValue(null);
                }
            });
            queue.add(jsonObjectRequest);




        }



        return mutableLiveData;
    }





}
