package com.example.aloos.finalproject2.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aloos.finalproject2.Adapters.RecycleAdapter;
import com.example.aloos.finalproject2.Adapters.ShowquestionAdapter;
import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.Services.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Showquestions extends AppCompatActivity {
String Qid;
RecyclerView recyclerView;
ProgressDialog pd;
ArrayList <Questions> q ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showquestions);
        Qid=getIntent().getExtras().getString("Quizidshow");
        Toast.makeText(this,Qid,Toast.LENGTH_SHORT).show();
        recyclerView=findViewById(R.id.showQrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        q= new ArrayList<>();
        getQuestions();
    }
    public void getQuestions(){
        pd = new ProgressDialog(Showquestions.this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(Showquestions.this);
        String url = config.URL + "getallQuestion.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o = new JSONObject(s);
                    JSONArray arr = o.getJSONArray("result");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject z = arr.getJSONObject(i);
                        Questions a = new Questions();
                        a.setQuestion(z.getString("question"));

                        q.add(a);


                    }
                }



                catch (JSONException e1) {
                    e1.printStackTrace();


                    {Toast.makeText(Showquestions.this,"Failed",Toast.LENGTH_LONG).show();}
                }
                ShowquestionAdapter adapter= new ShowquestionAdapter(Showquestions.this,q);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if( volleyError instanceof NetworkError) {
                    errorDescription="Network Error";
                } else if( volleyError instanceof ServerError) {
                    errorDescription="Server Error";
                } else if( volleyError instanceof AuthFailureError) {
                    errorDescription="AuthFailureError";
                } else if( volleyError instanceof ParseError) {
                    errorDescription="Parse Error";
                } else if( volleyError instanceof NoConnectionError) {
                    errorDescription="No Conenction";
                } else if( volleyError instanceof TimeoutError) {
                    errorDescription="Time Out";
                }else
                {
                    errorDescription="Connection Error";
                }
                Toast.makeText(getApplicationContext(), errorDescription,Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("quizid",Qid);



                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
