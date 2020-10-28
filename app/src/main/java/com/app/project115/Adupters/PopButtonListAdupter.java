package com.app.project115.Adupters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.project115.Model.PopButton;
import com.app.project115.R;
import com.app.project115.Util;

import java.util.List;

public class PopButtonListAdupter extends RecyclerView.Adapter<PopButtonListAdupter.PopViewHolder> {
    private List<PopButton> popButtonList;

    public PopButtonListAdupter(List<PopButton> popButtonList) {
        this.popButtonList = popButtonList;
    }

    @NonNull
    @Override
    public PopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_button, parent, false);
        return new PopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopViewHolder holder, final int position) {
       try{
           holder.button.setBackgroundColor(Color.parseColor(popButtonList.get(position).getBackGroundColorCode()));
           holder.button.setText(popButtonList.get(position).getTitle());
           holder.button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Util.openBrowser(popButtonList.get(position).getUrl(),holder.button.getContext().getApplicationContext());
               }
           });
       }catch (Exception e){

       }
    }

    @Override
    public int getItemCount() {
        return popButtonList.size();
    }

    public static final class PopViewHolder extends RecyclerView.ViewHolder{
        public Button button;
        public PopViewHolder(@NonNull View itemView) {
            super(itemView);
           button= itemView.findViewById(R.id.button);

        }
    }
}
