             package com.example.rc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;

import RC.adapter;
import RC.model;

             public class MainActivity extends AppCompatActivity {
    TextView team1, team2, dtg;
//    model model = new model(1234,"Argentina","Brazil","Thursday @ 9pm");


    RecyclerView recyclerView;

    adapter.RecyclerViewClickListener listener;
    Context context;
    List<String> teamList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONObject  data_data = new JSONObject();
        teamList.add("afghanistan");
        teamList.add("australia");
        teamList.add("bangladesh");
        teamList.add("england");
        teamList.add("india");
        teamList.add("new zealand");
        teamList.add("pakistan");
        teamList.add("south africa");
        teamList.add("sri lanka");
        teamList.add("west indies");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        List<model> modelList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        listener = new adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position){
                Intent i = new Intent(getApplicationContext(),MatchDesc.class);
                i.putExtra("ss",modelList.get(position).isSs());
                i.putExtra("toss",modelList.get(position).getToss());
                i.putExtra("win_team",modelList.get(position).getWt());
                i.putExtra("uid",modelList.get(position).getUid());
                Intent carrier = new Intent(getApplicationContext(),MainActivity.class);
                carrier.putExtra("uid",modelList.get(position).getUid());
                Intent summary_intent = new Intent(getApplicationContext(),Summary.class);

                Intent receiver2 = getIntent();
                String M_Data = receiver2.getStringExtra("api_response");
//                JSONObject match_data = new JSONObject(M_Data);
                //send details to summary class from new api call
                //maybe creating a json object in summary class would helo etter
                summary_intent.putExtra("response",M_Data);

                startActivity(i);
            }

            public void getMatchData(){
                Intent receiver = getIntent();
                int uid = receiver.getIntExtra("uid",0);
                String data_url = "https://cricapi.com/api/fantasySummary?apikey=Oi11keKppSQHtbRaSOW3MX9mcv62&unique_id="+uid;
                StringRequest data_req = new StringRequest(Request.Method.GET, data_url, new Response.Listener<String>() {
//                    String def_reponse = "{\"type\":\"empty\"}"; tried covering an edge case but it may not exist at all todo:on exception, consider this

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            Intent carrier2 = new Intent(getApplicationContext(),MainActivity.class);
                            carrier2.putExtra("api_response", String.valueOf(data));

                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"LINE 105, main",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"LINE 111, main",Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
                rq.add(data_req);
                //return api response
                return;
            }
        };
        String url = "https://cricapi.com/api/matches?apikey=Oi11keKppSQHtbRaSOW3MX9mcv62";

        Toast.makeText(getApplicationContext(), "Toast 1", Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                RC.adapter adapter = new RC.adapter(modelList, context, listener);
                try {
                    int i =0;
                    String ct1, ct2;
                    Toast.makeText(getApplicationContext(), "Toast 2", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("matches");
                    Toast.makeText(getApplicationContext(), ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    for (i = 0; i < jsonArray.length(); i++) {
//                        Toast.makeText(getApplicationContext(), ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                        JSONObject object = jsonArray.getJSONObject(i);
                        int uid = object.getInt("unique_id");
                        String t1 = object.getString("team-1");
                        ct1 = t1.toLowerCase();
                        String t2 = object.getString("team-2");
                        ct2 = t2.toLowerCase();
                        boolean ss = object.getBoolean("matchStarted");
                        String toss = "No toss data";
                        if (object.has("toss_winner_team")){
                        toss = object.getString("toss_winner_team");}
                        String wt = "null";
                        if(object.has("winner_team")){
                            wt = object.getString("winner_team");
                        }

//                        Toast.makeText(getApplicationContext(), "Toast A", Toast.LENGTH_SHORT).show();
                        if (t2.length() > 21) {
                            t2 = abbr(t2);
                        }
                        if (t1.length() > 21) {
                            t1 = abbr(t1);
                        }
                        String d = object.getString("dateTimeGMT") + "\n\n";
                        String T = "";
                        T += ddmmyyyy(d.substring(0, 10));
                        T += "   @  ";
                        T += d.substring(11, 16);
//                        Toast.makeText(getApplicationContext(), "Toast B", Toast.LENGTH_SHORT).show();
                        if(check(ct1,ct2)) {
                            model mobj = new model(uid, t1, t2, T, ss, toss,wt);
//                        Toast.makeText(getApplicationContext(), "Toast C", Toast.LENGTH_SHORT).show();
                            modelList.add(mobj);
                        }
//                        Toast.makeText(getApplicationContext(), "Toast D", Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapter);
                    }
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Response success. Exception", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Toast 4", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Toast.makeText(getApplicationContext(), "Toast 7", Toast.LENGTH_SHORT).show();
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);

    }

    private boolean check(String ct1, String ct2) {
        return teamList.contains(ct1) || teamList.contains(ct2);
    }
//    private void setAdapter() {
//
//
//    }

//    public void setOnClickListener() {
//
//
//    }

    private String ddmmyyyy(String substring) {
        String d = "", m = "", y = "";
        y = substring.substring(0, 4);
        m = substring.substring(5, 7);
        d = substring.substring(8);
        return d + '/' + m + '/' + y;
    }

    private String abbr(String t2) {
        String abbr = "";
        abbr += t2.charAt(0);
        abbr += ".";
        for (int u = 0; u < t2.length(); u++) {
            if (t2.charAt(u) == ' ') {
                abbr += t2.charAt(u + 1);
                abbr += ".";
            }
        }
        return abbr;
    }
}