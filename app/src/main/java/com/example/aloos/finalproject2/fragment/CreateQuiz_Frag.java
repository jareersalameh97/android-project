package com.example.aloos.finalproject2.fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.aloos.finalproject2.activites.NavDoctor;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateQuiz_Frag extends Fragment {
TextInputEditText quizid,quizname,quizd;
Button createquiz;
String id="";
ProgressDialog pd;
Spinner s;
ImageView QuizQR;
Bitmap bitmap ;
String Encodedimg;
    byte imageInByteGlobal[];
    String encodedImage;
    public CreateQuiz_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_create_quiz_, container, false);
       quizid =v.findViewById(R.id.quizid);
        quizname=v.findViewById(R.id.quizname);
        //quizd=v.findViewById(R.id.quizd);
        QuizQR=v.findViewById(R.id.quizQR);
        NavDoctor activity = (NavDoctor) getActivity();
        CreateQuiz_Frag results = activity.newInstance();
        if (results.getArguments() != null) {
            id = results.getArguments().getString("doctid");
            Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();

        }
        quizid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                QRGEncoder qrgEncoder = new QRGEncoder(quizid.getText().toString(), null, QRGContents.Type.TEXT, 0);
                try {
                    // Getting QR-Code as Bitmap
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // Setting Bitmap to ImageView
                    QuizQR.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    // Log.v(TAG, e.toString());
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toast.makeText(getActivity(), "Not "+id, Toast.LENGTH_SHORT).show();


        createquiz=v.findViewById(R.id.createQuizbtn);
        createquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // convert bitmap to byte
                if (quizid.getText().toString().equals("")) {
                    quizid.setError("Required");
                } else if (quizname.getText().toString().equals("")) {
                    quizname.setError("Required");
                } else {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte imageInByte[] = stream.toByteArray();
                imageInByteGlobal = imageInByte;
                String encodedString = Base64.encodeToString(imageInByte, 0);
                encodedImage = encodedString;
                Encodedimg = encodedImage;
                    CreateQuiz();

                }}

        });

        Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
        s=v.findViewById(R.id.spinner);
        String [] arr=getResources().getStringArray(R.array.quiztime);
        ArrayAdapter a= new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,arr);
        s.setAdapter(a);


        return v;
    }

    public void CreateQuiz(){
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = config.URL + "CreateQuiz.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o=new JSONObject(s);
                    String data=o.getString("result");
                    if(data.equals("1"))
                    {
                        Toast.makeText(getActivity(),"Add Quiz successfuly",Toast.LENGTH_LONG).show();}
                    else
                    {Toast.makeText(getActivity(),"this ID taken before , please try another ID",Toast.LENGTH_LONG).show();}
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
                param.put("quizid",quizid.getText().toString());
                param.put("quizname", quizname.getText().toString());
                param.put("quizd", s.getSelectedItem().toString());
                param.put("docid", id.toString());
                param.put("QR",Encodedimg);

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



