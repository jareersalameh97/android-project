package com.example.aloos.finalproject2.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.aloos.finalproject2.HelperClass.ShowQuiz;
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.activites.NavDoctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class myquizFragment extends Fragment {



     String id ;
     ProgressDialog pd;
     ArrayList<ShowQuiz> quizzes = new ArrayList<ShowQuiz>();
    RecyclerView rec;


    public myquizFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_myquiz, container, false);

        NavDoctor activity = (NavDoctor) getActivity();
        myquizFragment results = activity.newInstance2();
        if (results.getArguments() != null) {
            id = results.getArguments().getString("doctid");
            Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();

        }
        rec=v.findViewById(R.id.recyclequiz);
        getquizinfo();

        rec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        rec.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        return v;
    }
    public void getquizinfo(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = config.URL + "getQuiz.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o=new JSONObject(s);
                  //  String data=o.getString("result");
                    JSONArray arr=o.getJSONArray("result");

                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject x=arr.getJSONObject(i);
                        String id=x.getString("id");
                        String name=x.getString("quizname");
                        String quizd=x.getString("quizd");
                        ShowQuiz a = new ShowQuiz();
                        a.setQuizid(id);
                        a.setQuizname(name);
                        a.setQuizduration(quizd);
                            quizzes.add(a);
                        //    Toast.makeText(getActivity() , a.getQuizname()+"    " + a.getQuizid() , Toast.LENGTH_LONG).show();

                    }
                    Toast.makeText(getActivity(), ""+quizzes, Toast.LENGTH_SHORT).show();
   RecycleAdapter adapter= new RecycleAdapter(getActivity(),quizzes);

                    rec.setAdapter(adapter);

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
                param.put("id",id.toString());


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
