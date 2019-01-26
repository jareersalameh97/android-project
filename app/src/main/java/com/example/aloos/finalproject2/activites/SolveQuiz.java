package com.example.aloos.finalproject2.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SolveQuiz extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{
boolean ReadFlag = false;
    ImageView img;
    TextInputEditText quizidstd;
    TextView studentname,studentid;
    ProgressDialog pd;
    String stdname,stdid,quizid;
    private QRCodeReaderView qrCodeReaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_quiz);
        img = findViewById(R.id.studentimg);

      //  quizidstd = findViewById(R.id.quizidstd);
        studentname = findViewById(R.id.studentname);
        studentid = findViewById(R.id.studentid);
        byte[] decodedString = Base64.decode(getIntent().getExtras().getString("stdimg"), Base64.DEFAULT );
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);

        studentname.setText("student name :"+getIntent().getExtras().getString("stdname"));
        studentid.setText("student ID :"+getIntent().getExtras().getString("stdid"));
        stdname=getIntent().getExtras().getString("stdname");
        stdid=getIntent().getExtras().getString("stdid");
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener((QRCodeReaderView.OnQRCodeReadListener) this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK);

        return false;
        // Disable back button..............
    }
    public void Logout(View view) {
        Intent i = new Intent(SolveQuiz.this, LogInActivity.class);
        startActivity(i);
    }

    public void getQuestion(final String Quizid){
       final ArrayList<Questions> q = new ArrayList<Questions>();
        pd = new ProgressDialog(SolveQuiz.this);
            pd.setTitle("Loading");
            pd.setMessage("Please wait...");
            pd.show();

            RequestQueue queue = Volley.newRequestQueue(SolveQuiz.this);
            String url = config.URL + "getallQuestion.php";
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {


                    try {
                        JSONObject o=new JSONObject(s);
                        JSONArray arr=o.getJSONArray("result");
                        for (int i = 0; i<arr.length(); i++){
                            JSONObject z = arr.getJSONObject(i);
                            Questions a = new Questions();
                            a.setQuestion(z.getString("question"));
                            a.setT_A(z.getString("TrueAnwser"));
                            a.setF_A1(z.getString("falseanswer1"));
                            a.setF_A2(z.getString("falseanswer2"));
                            a.setF_A3(z.getString("falseanswer3"));
                            a.setFlag(z.getString("flag"));
                            a.setTimer(z.getString("timer"));
                            q.add(a);


                        }
                        Collections.shuffle(q);
                        Intent i = new Intent(SolveQuiz.this , Startquiz.class);
                        i.putExtra("Data" , q);
                        i.putExtra("name",stdname);
                        i.putExtra("id",stdid);
                        i.putExtra("qid",quizid);
                        startActivity(i);



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
                    Toast.makeText(SolveQuiz.this, errorDescription,Toast.LENGTH_SHORT).show();
                }
            })

                {
                    @Override
                    protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("quizid", Quizid);


                    return param;
                }

            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);

        }
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        // resultTextView.setText(text);
        if(ReadFlag == false){
            quizid=text;
        getQuestion(text);

        ReadFlag = true;
        }
        qrCodeReaderView.stopCamera();
        super.onPause();
        finish();
       // Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
        finish();
    }

}
