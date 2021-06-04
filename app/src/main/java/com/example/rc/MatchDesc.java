package com.example.rc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class MatchDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_desc);


     Intent intent = getIntent();
     boolean ss = intent.getBooleanExtra("ss",false);
     String toss = intent.getStringExtra("toss");
     String wt = intent.getStringExtra("win_team");
     int uid = intent.getIntExtra("uid",0);
     String url  = "https://cricapi.com/api/fantasySummary?apikey=Oi11keKppSQHtbRaSOW3MX9mcv62&unique_id="+uid;
        Toast.makeText(getApplicationContext(),""+uid,Toast.LENGTH_SHORT).show();
        TextView tv_ss,tv_toss;
        tv_ss = findViewById(R.id.tv_ss);
        tv_toss = findViewById(R.id.tv_toss);
        tv_toss.setText("Toss : "+toss);
        tv_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details_intent = new Intent(getApplicationContext(),Summary.class);
                details_intent.putExtra("name","call that api in summary.class");
                startActivity(details_intent);
            }
        });
        if(ss && !(wt.equalsIgnoreCase("null"))){
            Toast.makeText(getApplicationContext(),"ss toast",Toast.LENGTH_SHORT).show();
            tv_ss.setText("Match won by : "+wt);
        }
        else if(ss && wt.equalsIgnoreCase("null")){
            tv_ss.setText("Match is in progress...");

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(request);
        }
        else{
            tv_ss.setText("Match hasn't started yet.");

        }
    }
}

//1262346

// sa vs zi 1263409
// ire vs scotland 1262919