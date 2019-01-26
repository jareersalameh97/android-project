package com.example.aloos.finalproject2.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.Services.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFrag extends Fragment {
TextInputEditText doctorname,doctorusername,doctorpass,doctorEmail;
 public String jareer ;
ImageView doctorimg;
    final int CAMERA_REQUEST = 1;
    final int PICK_FROM_GALLERY = 2;
    byte imageInByteGlobal[];
    String encodedImage;
    ProgressDialog pd;
    Button doc;
    public DoctorFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_doctor, container, false);
        doctorname=v.findViewById(R.id.doctorname);
        doctorusername=v.findViewById(R.id.doctorusername);
        doctorpass=v.findViewById(R.id.doctorpass);
        doctorEmail=v.findViewById(R.id.doctoremail);
        doctorimg=v.findViewById(R.id.studentimg);

        doc = v.findViewById(R.id.DocBtn);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidPassword(doctorpass.getText().toString())) {
                    doctorpass.setError("Password must be from 8 to 24 character and Dont have special character");

                }
                if(!isValidEmailAddress(doctorEmail.getText().toString()))
                    doctorEmail.setError("Enter a Valid Email");
            }
        });
        setManyPermissions();
        doctorimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });
        return v;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9_-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    private void setManyPermissions()
    {


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.CAMERA};

        if(!hasPermissions(getActivity(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);



        }

    }

    public void callCamera() {
        Intent i = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        i.putExtra("crop", "true");
//        i.putExtra("aspectX", 0);
//        i.putExtra("aspectY", 0);
//        i.putExtra("outputX", 200);
//        i.putExtra("outputY", 150);
        startActivityForResult(i,CAMERA_REQUEST );

    }

    /**
     * open gallery method
     */

    public void callGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra("crop", "true");
        i.putExtra("aspectX", 0);
        i.putExtra("aspectY", 0);
        i.putExtra("outputX", 200);
        i.putExtra("outputY", 150);
        i.putExtra("return-data", true);
        startActivityForResult(
                i,
                PICK_FROM_GALLERY);

    }

    public void takePicture(View v)
    {
        String[] option = new String[] { "Take from Camera",
                "Select from Gallery" };

        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, option);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Option");
        builder.setAdapter(a, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {


                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_CANCELED)
        {
            return;
        }








        switch (requestCode) {
            case CAMERA_REQUEST:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    doctorimg.setImageBitmap(yourImage);


                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] =stream.toByteArray();
                    imageInByteGlobal=imageInByte;
                    String encodedString = Base64.encodeToString(imageInByte, 0);
                    encodedImage=encodedString;
                   // jareer = encodedString.toString() ;

                }
                break;
            case PICK_FROM_GALLERY:

                final Uri imageUri = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getActivity().getContentResolver().openInputStream(imageUri);
                     jareer = imageStream.toString() ;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                doctorimg.setImageBitmap(selectedImage);


//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte imageInByte[] =stream.toByteArray();
//                imageInByteGlobal=imageInByte;
//                String encodedString = Base64.encodeToString(imageInByte, 0);
//                encodedImage=encodedString;

                break;
        }

    }
    public void addDoctor()
    {

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = config.URL + "addDoctor.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o=new JSONObject(s);
                    String data=o.getString("result");
                    if(data.equals("1"))
                    {
                        Toast.makeText(getActivity(),"Sign Up Successfully",Toast.LENGTH_LONG).show();}
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
                param.put("doctorname",doctorname.getText().toString());
                param.put("doctorpass", doctorpass.getText().toString());
                param.put("doctorid", doctorusername.getText().toString());
                param.put("doctorEmail", doctorEmail.getText().toString());

                param.put("studentimg", encodedImage);
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
