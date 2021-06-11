package com.example.rc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class MatchDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_desc);


     Intent intent = getIntent();
     boolean ss = intent.getBooleanExtra("ss",false);
     boolean squad = intent.getBooleanExtra("squad",false);
     String toss = intent.getStringExtra("toss");
     String wt = intent.getStringExtra("win_team");
     int uid = intent.getIntExtra("uid",0);
     String data_url  = "https://cricapi.com/api/fantasySummary?apikey=LDkgcBaZQGhywGDQMK3grJuBs652&unique_id="+uid;
//        Toast.makeText(getApplicationContext(),""+uid,Toast.LENGTH_SHORT).show();
        TextView tv_ss,tv_toss,tv_t1,tv_t2,tv_desc;
        tv_ss = findViewById(R.id.tv_ss);
        tv_toss = findViewById(R.id.tv_toss);
        tv_toss.setText("Toss : "+toss);
        tv_t1 = findViewById(R.id.t1Title_tv);
        tv_t2 = findViewById(R.id.t2Title_tv);
        tv_desc = findViewById(R.id.desc_tv);
//tv_desc.setText("Match Description");

//        if(ss && !(wt.equalsIgnoreCase("null"))){
////            Toast.makeText(getApplicationContext(),"ss toast",Toast.LENGTH_SHORT).show();
//            tv_ss.setText("Match won by : "+wt);
//        }

//        if(ss && wt.equalsIgnoreCase("null")){
        if(ss){
            tv_ss.setText("Match is in progress...");

//            Toast.makeText(getApplicationContext(),"Api call starts",Toast.LENGTH_SHORT).show();

            StringRequest data_req = new StringRequest(Request.Method.GET, data_url, new Response.Listener<String>() {
//                    String def_reponse = "{\"type\":\"empty\"}"; tried covering an edge case but it may not exist at all

                @Override
                public void onResponse(String response) {
                    try {
//                        Toast.makeText(getApplicationContext(),"Response",Toast.LENGTH_SHORT).show();
                        JSONObject total_data = new JSONObject(response);
                        JSONObject data = total_data.getJSONObject("data");

                        JSONArray batting_data = data.getJSONArray("batting");
                        JSONObject team1_bat_data = batting_data.getJSONObject(0);
                        JSONArray t1_bat_scores = team1_bat_data.getJSONArray("scores");
                        String t1BatList = "";
                        int t1_total = 0;
                        for (int i =0; i<t1_bat_scores.length();i++){
                            JSONObject player_data = t1_bat_scores.getJSONObject(i);
                            String name = player_data.getString("batsman");
                            String dismissal_type = player_data.getString("dismissal");
                            int runs = player_data.getInt("R");
                            t1BatList += name + " \t " + dismissal_type + " \t Runs Scored : " + runs + "\n\n";
                            t1_total += runs;
                        }

                        JSONObject team2_batting = batting_data.getJSONObject(1);
                        String ts1 = team1_bat_data .getString("title");
                        String team1_name = ts1.substring(0,ts1.length()-27);
                        int l1 = t1_bat_scores.length() - 2;
//                        String mType = total_data.getString("type");
                        //using team names from first api call


                        tv_t1.setText(team1_name+ " ("+t1_total+"/"+l1+")");
                        tv_ss.setText(t1BatList);

                        //setting team2 data just like team 1 data
                        JSONObject team2_bat_data = batting_data.getJSONObject(1);
                        JSONArray t2_bat_scores = team2_bat_data.getJSONArray("scores");
                        String t2BatList = "";
                        int t2_total = 0;
                        for (int i =0; i<t2_bat_scores.length();i++){
                            JSONObject player_data2 = t2_bat_scores.getJSONObject(i);
                            String name = player_data2.getString("batsman");
                            String dismissal_type = "caught";
                            if(player_data2.has("dismissal")) {
                                dismissal_type = player_data2.getString("dismissal");
                            }
                            else {
                                dismissal_type = player_data2.getString("dismissal-info");
                            }
                            int runs = player_data2.getInt("R");
                            t2BatList += name + " \t " + dismissal_type + " \t Runs Scored : " + runs + "\n\n";
                            t2_total += runs;
                        }
                        String ts2 = team2_bat_data .getString("title");
                        String team2_name = ts2.substring(0,ts2.length()-41);
                        int l2 = t2_bat_scores.length() - 2;
                        tv_t2.setText(team2_name + " ("+t2_total+"/"+l2+")");

                        tv_toss.setText(t2BatList);

                        Log.d(TAG, "onResponse: response success");
                    if (!(wt.equalsIgnoreCase("null"))){
                        tv_desc.setText(team1_name+" vs "+team2_name + " (Match won by " + wt + ")");
                    }
                    else{
                        tv_desc.setText(team1_name+" vs "+team2_name);
                    }
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),"LINE 123, match_desc",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"LINE 128, main",Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            rq.add(data_req);
//            Intent get_data = getIntent();
//            String api2_data = get_data.getStringExtra("api_response");

        }
        else{
            tv_ss.setText("Match hasn't started yet.");

        }
    }
}

//1262346

// sa vs zi 1263409
// ire vs scotland 1262919