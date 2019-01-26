package com.example.aloos.finalproject2.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.aloos.finalproject2.activites.QuastionType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FillFrag extends Fragment {

TextInputEditText QuastionFill,TrueAnswerFill;
Button btn;
String Flag;
String id;
ProgressDialog pd;
    public FillFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fillintheblank, container, false);
        QuastionType a = new QuastionType();
        FillFrag results = a.newInstance1();
        a.newInstance();
        if (results.getArguments() != null) {
            id = results.getArguments().getString("Qid");


        }
        QuastionFill=v.findViewById(R.id.FillQuastion);
        TrueAnswerFill=v.findViewById(R.id.FillTrueAnswer);
        Flag="B";
        btn=v.findViewById(R.id.btnfill);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QuastionFill.getText().toString().equals(""))
                    QuastionFill.setError("Question is required");
                 else if (TrueAnswerFill.getText().toString().equals(""))
                    TrueAnswerFill.setError("True Answer Required");
else {
                    AddQuastions();
                    Toast.makeText(getActivity(), "Add Question successfully", Toast.LENGTH_SHORT).show();

                    TrueAnswerFill.setText("");
                    QuastionFill.setText("");

                }     }
        });
        return v;

    }
    public void AddQuastions(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = config.URL + "addQuastion.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o=new JSONObject(s);
                    String data=o.getString("result");
                    if(data.equals("1"))
                    {
                        Toast.makeText(getActivity(),"Add Question Successfully",Toast.LENGTH_LONG).show();}
                    else
                    {Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();}
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
                Toast.makeText(getActivity(), errorDescription,Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("question",QuastionFill.getText().toString());
                param.put("trueanswer", TrueAnswerFill.getText().toString());
                param.put("flag", Flag.toString());
                param.put("quizid",id.toString());


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
