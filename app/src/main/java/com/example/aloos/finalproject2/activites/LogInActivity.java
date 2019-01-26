package com.example.aloos.finalproject2.activites;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
TextInputEditText username,password;
TextView t1 , t2;
ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        t1 = findViewById(R.id.txt);
        t2=findViewById(R.id.textView);
        Typeface tt = Typeface.createFromAsset(getAssets() , "fonts/abc.TTF");
        t1.setTypeface(tt);
        t2.setTypeface(tt);
    }

    public void goTosignup(View view) {
       Intent i = new Intent(this,Signup.class);
      startActivity(i);
    }


    public void Login(View view) {
        getUser();

    }

    public void getUser()
    {

        pd = new ProgressDialog(this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = config.URL + "getUser.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {

                    JSONObject o = new JSONObject(s);
                    JSONArray arr = o.getJSONArray("result");
                    if(arr.length() == 0)
                        Toast.makeText(LogInActivity.this,"Username or Password Not correct ",Toast.LENGTH_LONG).show();
                      else {
                        JSONObject ob = arr.getJSONObject(0);
                        String name=ob.getString("name");
                        String mail=ob.getString("email");
                        String  img = ob.getString("img");
                        String type = ob.getString("type");
                        String docid=ob.getString("id");
                        if (type.equals("D")) {

                          //  Toast.makeText(LogInActivity.this, ob.getString("type")+""+name+""+mail, Toast.LENGTH_LONG).show();


                            Intent i = new Intent(LogInActivity.this, NavDoctor.class);

                              i.putExtra("Dusername",name.toString());
                              i.putExtra("email",mail.toString());
                              i.putExtra("img",img.toString());
                              i.putExtra("id",docid);
                           // byte[] decodedString = Base64.decode(img, Base64.URL_SAFE );
                         //   i.putExtra("img",decodedString);
                        /*    Bundle b = new Bundle();
                            b.putString("id",docid);
                            CreateQuiz_Frag fragobj= new CreateQuiz_Frag();
                            fragobj.setArguments(b);
                            startActivity(i);*/
                        startActivity(i);
finish();
                        } else if (type.equals("S")) {
                            Intent i = new Intent(LogInActivity.this, SolveQuiz.class);
                            i.putExtra("stdname",name.toString());
                            i.putExtra("stdid",docid.toString());
                            i.putExtra("stdimg",img);



                            startActivity(i);
                            finish();
                        }
                    }
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
                Toast.makeText(LogInActivity.this, errorDescription,Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("name",username.getText().toString());
                param.put("pass", password.getText().toString());

                //param.put("studentimg", encodedImage);
                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }
    private AlertDialog Exit(){

        AlertDialog alertDialog =new AlertDialog.Builder(LogInActivity.this)
                .setTitle("Exit")
                .setMessage("Do you want to Exit ?")


                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        android.os.Process.killProcess(android.os.Process.myPid());

                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();


        return alertDialog;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Exit().show();


        return false;
        // Disable back button..............
    }

}
