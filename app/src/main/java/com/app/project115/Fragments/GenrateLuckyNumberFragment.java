package com.app.project115.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.project115.R;
import com.app.project115.Util;
import com.bumptech.glide.Glide;

public class GenrateLuckyNumberFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.luck_number_genrate_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btGenrateTwoDilgit = view.findViewById(R.id.btGenrateTwoDigitNumber);
        ImageView btGenrateThreeDigit= view.findViewById(R.id.btGenrateThreeDigitNumber);
        Glide.with(this).load(R.drawable.click_here).into(btGenrateThreeDigit);
        Glide.with(this).load(R.drawable.click_here).into(btGenrateTwoDilgit);
        final TextView tvTwoDigit=view.findViewById(R.id.tvTwoDigit);
        final TextView tvThreeDigit=view.findViewById(R.id.tvThreeDigit);
        btGenrateThreeDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvThreeDigit.setText(String.valueOf(Util.getRand(99,999)));
            }
        });
        btGenrateTwoDilgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTwoDigit.setText(String.valueOf(Util.getRand(10,99)));
            }
        });
    }
}
