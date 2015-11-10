package com.sourcebrain.islfans.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.sourcebrain.islfans.R;

import java.util.Calendar;

public class PointsFragment extends Fragment {

    public PointsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_points, container, false);

        /*ImageView mImgPointsTable = (ImageView) rootView.findViewById(R.id.imgPointsTable);
        Glide.with(PointsFragment.this)

                .load("http://neelamana.net/isl/result/image.jpg")
                .signature(new StringSignature(Calendar.getInstance().getTimeInMillis() + ""))
               *//* .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)*//*
                .into(mImgPointsTable);*/

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
