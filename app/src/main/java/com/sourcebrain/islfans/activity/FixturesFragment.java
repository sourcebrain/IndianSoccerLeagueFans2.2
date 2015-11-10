package com.sourcebrain.islfans.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcebrain.islfans.R;
import com.sourcebrain.islfans.adapter.FixturesAdapter;
import com.sourcebrain.islfans.model.Fixture;
import com.sourcebrain.islfans.model.Result;
import com.sourcebrain.islfans.service.ServiceHandler;
import com.sourcebrain.islfans.service.ServiceRequest;
import com.sourcebrain.islfans.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FixturesFragment extends Fragment {

    public FixturesFragment() {
        // Required empty public constructor
    }

    LinearLayout mLinearTodaysMatch;
    ListView mListFixtures;
    Spinner mSpinnerTeams;

    TextView mTxtTodayMatchId;
    TextView mTxtTodayTeam1;
    TextView mTxtTodayTeam2;
    TextView mTxtTodayDateTime;
    TextView mTxtTodayVenue;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("dd-MMM");
    String mStrCurDate = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStrCurDate = format.format(calendar.getTimeInMillis());
            if(Util.hasConnection(getActivity())) {

                WebTask task = new WebTask();
                task.execute();
            }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fixtures, container, false);

        mLinearTodaysMatch = (LinearLayout) rootView.findViewById(R.id.linearTodaysMatch);
        mListFixtures = (ListView) rootView.findViewById(R.id.listFixtures);
        mSpinnerTeams = (Spinner) rootView.findViewById(R.id.spinnerTeams);

        mSpinnerTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mListFixtures.setAdapter(new FixturesAdapter(getActivity(), ISLHomeActivity.mArrayFixtures));
                } else {
                    filterFixtures(mSpinnerTeams.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTxtTodayMatchId = (TextView) rootView.findViewById(R.id.txtTodayMatchId);
        mTxtTodayTeam1 = (TextView) rootView.findViewById(R.id.txtTodayTeam1);
        mTxtTodayTeam2 = (TextView) rootView.findViewById(R.id.txtTodayTeam2);
        mTxtTodayDateTime = (TextView) rootView.findViewById(R.id.txtTodayDateTime);
        mTxtTodayVenue = (TextView) rootView.findViewById(R.id.txtTodayVenue);

        // Inflate the layout for this fragment
        return rootView;
    }

    void filterFixtures(String teamselected) {
        ArrayList<Fixture> filteredList = new ArrayList<Fixture>();
        filteredList.clear();
        for(int i = 0; i < ISLHomeActivity.mArrayFixtures.size(); i++) {
            if(ISLHomeActivity.mArrayFixtures.get(i).getTeam1().contains(teamselected)
                    || ISLHomeActivity.mArrayFixtures.get(i).getTeam2().contains(teamselected)) {
                filteredList.add(ISLHomeActivity.mArrayFixtures.get(i));
            }
        }
        mListFixtures.setAdapter(new FixturesAdapter(getActivity(), filteredList));
    }

    class WebTask extends AsyncTask<Void,Void,String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();

            String strJsonResponse = "";
            try {
                strJsonResponse = sh
                        .makeServiceCall(ServiceRequest.serverUrl,
                                ServiceHandler.GET);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return strJsonResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            try {
                JSONObject apiObject = new JSONObject(result);

                parseFixtures(apiObject.getString("fixture"));
                parseResults(apiObject.getString("result"));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.unknown_error),
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    void parseFixtures(String jsonFixtures) {
        ISLHomeActivity.mArrayFixtures.clear();
        try {
            JSONArray fixturesArray = new JSONArray(jsonFixtures);
            for (int i = 0; i < fixturesArray.length(); i++) {
                Fixture fixture = new Fixture();
                JSONObject object = fixturesArray.getJSONObject(i);
                fixture.setMatchID(object.getString("matchid"));
                fixture.setTeam1(object.getString("team1"));
                fixture.setTeam2(object.getString("team2"));
                fixture.setDate(object.getString("date"));
                fixture.setTime(object.getString("time"));
                fixture.setVenue(object.getString("venue"));
                ISLHomeActivity.mArrayFixtures.add(fixture);

                if(object.getString("date").equalsIgnoreCase(mStrCurDate)) {
                    setTodaysMatch(fixture);
                }

            }


            setFixturesAdapter();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setTodaysMatch(Fixture fixture) {
        mLinearTodaysMatch.setVisibility(View.VISIBLE);
        mTxtTodayMatchId.setText("Upcoming");
        mTxtTodayTeam1.setText(fixture.getTeam1());
        mTxtTodayTeam2.setText(fixture.getTeam2());
        /*switch (fixture.getTeam1()) {
            case "ATLETICO DE KOLKATA":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "CHENNAIYIN FC":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "DELHI DYNAMOS FC":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "FC GOA":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "KERALA BLASTERS FC":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "MUMBAI CITY FC":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "NORTHEAST UNITED FC":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            case "FC PUNE CITY":
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
            default:
                mImgTodayTeam1.setImageResource(R.drawable.app_icon);
                break;
        }
        switch (fixture.getTeam2()) {
            case "ATLÉTICO DE KOLKATA":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "CHENNAIYIN FC":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "DELHI DYNAMOS FC":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "FC GOA":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "KERALA BLASTERS FC":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "MUMBAI CITY FC":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "NORTHEAST UNITED FC":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            case "FC PUNE CITY":
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
            default:
                mImgTodayTeam2.setImageResource(R.drawable.app_icon);
                break;
        }
*/

        mTxtTodayDateTime.setText(fixture.getDate() + ", "
                + fixture.getTime());
        mTxtTodayVenue.setText(fixture.getVenue());
    }

    void parseResults(String jsonResults) {
        ISLHomeActivity.mArrayResults.clear();
        try {
            JSONArray resultsArray = new JSONArray(jsonResults);
            ISLHomeActivity.mArrayResults.clear();
            for (int i = 0; i < resultsArray.length(); i++) {
                Result result = new Result();
                JSONObject object = resultsArray.getJSONObject(i);
                result.setMatchID(object.getString("matchid"));
                result.setTeam1(object.getString("team1"));
                result.setGoal1(object.getString("goal1"));
                result.setTeam2(object.getString("team2"));
                result.setGoal2(object.getString("goal2"));
                result.setDate(object.getString("date"));
                result.setTime(object.getString("time"));
                result.setVenue(object.getString("venue"));
                result.setScorers(object.getString("scorers"));
                ISLHomeActivity.mArrayResults.add(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setFixturesAdapter() {
        mListFixtures.setAdapter(new FixturesAdapter(getActivity(), ISLHomeActivity.mArrayFixtures));
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
