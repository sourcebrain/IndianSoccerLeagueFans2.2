package com.sourcebrain.islfans.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourcebrain.islfans.R;
import com.sourcebrain.islfans.model.Fixture;

import java.util.ArrayList;


public class FixturesAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Fixture> mArrayFixtures;

    public FixturesAdapter(Context context, ArrayList<Fixture> fixtures) {
        mContext = context;
        mArrayFixtures = fixtures;
    }

    @Override
    public int getCount() {
        return mArrayFixtures.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayFixtures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FixtureViewHolder holder;

        if(view == null){
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.row_fixture, parent, false);

            holder = new FixtureViewHolder();
            holder.mTxtMatchId = (TextView) view.findViewById(R.id.txtMatchId);
            holder.mImgTeam1 = (ImageView) view.findViewById(R.id.imgTeam1);
            holder.mTxtTeam1 = (TextView) view.findViewById(R.id.txtTeam1);
            holder.mImgTeam2 = (ImageView) view.findViewById(R.id.imgTeam2);
            holder.mTxtTeam2 = (TextView) view.findViewById(R.id.txtTeam2);
            holder.mTxtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
            holder.mTxtVenue = (TextView) view.findViewById(R.id.txtVenue);


            view.setTag(holder);
        } else {
            holder = (FixtureViewHolder) view.getTag();
        }
        holder.mTxtMatchId.setText("Match " + mArrayFixtures.get(position).getMatchID());
        holder.mTxtTeam1.setText(mArrayFixtures.get(position).getTeam1());
        holder.mTxtTeam2.setText(mArrayFixtures.get(position).getTeam2());

        switch (mArrayFixtures.get(position).getTeam1()) {
            case "ATLETICO DE KOLKATA":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "CHENNAIYIN FC":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "DELHI DYNAMOS FC":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "FC GOA":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "KERALA BLASTERS FC":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "MUMBAI CITY FC":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "NORTHEAST UNITED FC":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "FC PUNE CITY":
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
            default:
                holder.mImgTeam1.setImageResource(R.drawable.app_icon);
                break;
        }
        switch (mArrayFixtures.get(position).getTeam2()) {
            case "ATLÉTICO DE KOLKATA":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "CHENNAIYIN FC":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "DELHI DYNAMOS FC":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "FC GOA":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "KERALA BLASTERS FC":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "MUMBAI CITY FC":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "NORTHEAST UNITED FC":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "FC PUNE CITY":
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
            default:
                holder.mImgTeam2.setImageResource(R.drawable.app_icon);
                break;
        }


        holder.mTxtDateTime.setText(mArrayFixtures.get(position).getDate() + ", "
                                        + mArrayFixtures.get(position).getTime());
        holder.mTxtVenue.setText(mArrayFixtures.get(position).getVenue());

        return view;
    }

    static class FixtureViewHolder{
        TextView mTxtMatchId;
        ImageView mImgTeam1;
        TextView mTxtTeam1;
        ImageView mImgTeam2;
        TextView mTxtTeam2;
        TextView mTxtDateTime;
        TextView mTxtVenue;
    }
}
