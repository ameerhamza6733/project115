package com.app.project115.Adupters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.project115.Model.GoldenStock;
import com.app.project115.Model.PinkStock;
import com.app.project115.Model.Stock;
import com.app.project115.R;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Stock> stockList;

    public StockAdapter(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1){
            return  new GoldenStock(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_golden_stock, parent, false));
        }else if (viewType==2){
            return  new GovtStock(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_govt_lot, parent, false));
        }else  if (viewType==3 || viewType==4){
            return  new PinkStockAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_pink_stock, parent, false));
        }else  if (viewType==5){
            return  new CountryStock(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_thai_stock, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==1){
            GoldenStock goldenStockViewModel= (GoldenStock) holder;
            com.app.project115.Model.GoldenStock goldenStock = (com.app.project115.Model.GoldenStock) stockList.get(position);
            goldenStockViewModel.tvGoldenStockDate.setText(goldenStock.getDate());
            goldenStockViewModel.tv2digits.setText(Integer.toString(goldenStock.getTwoDigit()));
            goldenStockViewModel.tv3digits.setText(Integer.toString(goldenStock.getThreeDigit()));
            goldenStockViewModel.tvGoldenStock.setText(goldenStock.getName());
        }else  if (holder.getItemViewType()==2){

        }else  if (holder.getItemViewType()==3 || holder.getItemViewType()==4){
            PinkStockAdapter pinkStockAdapter = (PinkStockAdapter) holder;
            PinkStock pinkStock = (PinkStock) stockList.get(position);
            pinkStockAdapter.tvNamePinkLot.setText(pinkStock.getName());
            pinkStockAdapter.tvPinkDate.setText(pinkStock.getDate());
            pinkStockAdapter.tvThree.setText(Integer.toString(pinkStock.getThreeDigits()));
            pinkStockAdapter.tvTwo.setText(Integer.toString(pinkStock.getTwoDigtis()));
            pinkStockAdapter.tvPinkPrizeNumber.setText(pinkStock.getWiningNumber());
            pinkStockAdapter.backGround.setBackgroundColor(Color.parseColor(pinkStock.getColorCode()));
        }else if (holder.getItemViewType()==5){
            CountryStock countryStockHolder= (CountryStock) holder;
            com.app.project115.Model.GoldenStock goldenStock = (com.app.project115.Model.GoldenStock) stockList.get(position);
            countryStockHolder.tvDate.setText(goldenStock.getDate());
            countryStockHolder.tvTwo.setText(Integer.toString(goldenStock.getTwoDigit()));
            countryStockHolder.tvThree.setText(Integer.toString(goldenStock.getThreeDigit()));
            countryStockHolder.tvName.setText(goldenStock.getName());
            countryStockHolder.tvCountryName.setText(goldenStock.getName());

        }
    }

    @Override
    public int getItemViewType(int position) {

        return stockList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class CountryStock extends RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvDate;
        public TextView tvTwo;
        public TextView tvThree;
        public TextView tvCountryName;

        public CountryStock(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvThaiName);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvCountryName=itemView.findViewById(R.id.tvCountryName);
            tvTwo=itemView.findViewById(R.id.tvTwoDigit);
            tvThree=itemView.findViewById(R.id.tvThaiStockThreeDigit);
        }
    }

    public static class GoldenStock extends RecyclerView.ViewHolder {
        public TextView tvGoldenStock;
        public TextView tvGoldenStockRound;
        public TextView tvGoldenStockDate;
        public TextView tv3digitsTitle;
        public TextView tv3digits;
        public TextView tv2digitsTitle;
        public TextView tv2digits;
        public GoldenStock(View itemView) {
            super(itemView);
            tvGoldenStock=itemView.findViewById(R.id.tvGoldenStock);
            tvGoldenStockRound=itemView.findViewById(R.id.tvGoldenStockRound);
            tvGoldenStockDate=itemView.findViewById(R.id.tvGoldenStockDate);
            tv3digitsTitle=itemView.findViewById(R.id.tv3digitsTitle);
            tv3digits=itemView.findViewById(R.id.tv3digits);
            tv2digitsTitle=itemView.findViewById(R.id.tv2digitsTitle);
            tv2digits=itemView.findViewById(R.id.tv2digits);


        }
    }

    public static class GovtStock extends RecyclerView.ViewHolder {
        public ImageView imageCountryLotFlag;
        public  TextView tvNameGovtLot;
        public TextView tvGovtDate;
        public TextView tvGovtPrizeNumber;
        public  TextView runningNumberFrontThreeTitle;
        public  TextView runningNumberFrontThree;
        public TextView runningNumberBackThreeTitle;
        public TextView runningNumberBackThree;
        public TextView runningNumberBackTwoTitle;
        public TextView runningNumberBack;

        public GovtStock(View itemView) {
            super(itemView);
            imageCountryLotFlag=itemView.findViewById(R.id.imageCountryLotFlag);
            tvNameGovtLot=itemView.findViewById(R.id.tvNameGovtLot);
            tvGovtDate=itemView.findViewById(R.id.tvGovtDate);
            tvGovtPrizeNumber=itemView.findViewById(R.id.tvGovtPrizeNumber);
            runningNumberFrontThreeTitle=itemView.findViewById(R.id.runningNumberFrontThreeTitle);
            runningNumberFrontThree=itemView.findViewById(R.id.runningNumberFrontThree);
            runningNumberBackThreeTitle=itemView.findViewById(R.id.runningNumberBackThreeTitle);
            runningNumberBackThree=itemView.findViewById(R.id.runningNumberBackThree);
            runningNumberBackTwoTitle=itemView.findViewById(R.id.runningNumberBackTwoTitle);
            runningNumberBack=itemView.findViewById(R.id.runningNumberBack);
        }
    }

    public static class PinkStockAdapter extends RecyclerView.ViewHolder{
        public TextView tvNamePinkLot;
        public TextView tvPinkDate;
        public TextView tvPinkPrizeNumber;
        public TextView tvPinkPrizeName;
        public TextView tvThree;
        public TextView tvTwo;
        public View backGround;
        public PinkStockAdapter(@NonNull View itemView) {
            super(itemView);
            tvNamePinkLot=itemView.findViewById(R.id.tvNameGovtLot);
            tvPinkDate=itemView.findViewById(R.id.tvGovtDate);
            tvPinkPrizeNumber=itemView.findViewById(R.id.tvGovtPrizeNumber);
            tvPinkPrizeName=itemView.findViewById(R.id.tvGovtPrizeName);
            tvThree=itemView.findViewById(R.id.tvThreeDigit);
            tvTwo=itemView.findViewById(R.id.tvTwoDigit);
            backGround=itemView.findViewById(R.id.color);

        }
    }
}
