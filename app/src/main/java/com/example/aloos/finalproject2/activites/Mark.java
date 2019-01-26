package com.example.aloos.finalproject2.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.aloos.finalproject2.Adapters.MarkAdapter;
import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.HelperClass.StudentInfo;
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mark extends AppCompatActivity {
    RecyclerView rec;
    ArrayList<StudentInfo> list;
    ProgressDialog pd;
    String quizid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        rec = findViewById(R.id.markrec);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        quizid= getIntent().getExtras().getString("quizidddd");
        Toast.makeText(this,quizid,Toast.LENGTH_SHORT);
        list = new ArrayList<>();

        Getstdinfo();

    }

    public void Getstdinfo() {

        pd = new ProgressDialog(Mark.this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(Mark.this);

        String url = config.URL + "getmarkinfo.php";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                try {
                    JSONObject o = new JSONObject(s);
                    JSONArray arr = o.getJSONArray("result");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject z = arr.getJSONObject(i);
                        StudentInfo a = new StudentInfo();
                        a.setStdid(z.getString("studentid"));
                        a.setStdname(z.getString("studentname"));
                        a.setMark(z.getString("mark"));
                        list.add(a);


                    }

                    MarkAdapter a = new MarkAdapter(Mark.this, list);
                    rec.setAdapter(a);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                String errorDescription = "";
                if (volleyError instanceof NetworkError) {
                    errorDescription = "Network Error";
                } else if (volleyError instanceof ServerError) {
                    errorDescription = "Server Error";
                } else if (volleyError instanceof AuthFailureError) {
                    errorDescription = "AuthFailureError";
                } else if (volleyError instanceof ParseError) {
                    errorDescription = "Parse Error";
                } else if (volleyError instanceof NoConnectionError) {
                    errorDescription = "No Conenction";
                } else if (volleyError instanceof TimeoutError) {
                    errorDescription = "Time Out";
                } else {
                    errorDescription = "Connection Error";
                }
                Toast.makeText(Mark.this, errorDescription, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("quizid", quizid);


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
