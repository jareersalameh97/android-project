package com.example.aloos.finalproject2.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.fragment.CreateQuiz_Frag;
import com.example.aloos.finalproject2.fragment.markFragment;
import com.example.aloos.finalproject2.fragment.myquizFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NavDoctor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String Dname,img,id;
    String doctorname, email;
    ProgressDialog pd;
    TextView tvHeaderName,emaila;
    NavigationView navHeaderView;
    ImageView imgdoc;
    TextView txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //**************************************************************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Dname = getIntent().getExtras().getString("Dusername") ;
        email = getIntent().getExtras().getString("email");
        img=getIntent().getExtras().getString("img");
        id=getIntent().getExtras().getString("id");
        txt = findViewById(R.id.WelcomeTxt1);
        txt.setText("Welcome Dr. " + Dname);

      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hv = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //**************************************************************************
        tvHeaderName = (TextView) hv.findViewById(R.id.hederDocname);
        emaila=hv.findViewById(R.id.emailheder);
        imgdoc=hv.findViewById(R.id.imghederdoc);

        //Sname=getIntent().getExtras().getString("Susername");
      //  Toast.makeText(NavDoctor.this, Dname + "" + email+""+img+"ghdhd "+id, Toast.LENGTH_SHORT).show();

      //  navHeaderView= navigationView.inflateHeaderView(R.layout.nav);
      // tvHeaderName= (TextView) navHeaderView.findViewById(R.id.tvHeaderName);

        tvHeaderName.setText(Dname);
      emaila.setText(email);
        byte[] decodedString = Base64.decode(img, Base64.DEFAULT );
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgdoc.setImageBitmap(decodedByte);


       // Bundle bundle=new Bundle();
       // bundle.putString("doctid",id);
       // CreateQuiz_Frag fragobj = new CreateQuiz_Frag();
       // fragobj.setArguments(bundle);
        newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.createquiz) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contener, new CreateQuiz_Frag(), null);
            ft.commit();
        } else if (id == R.id.myquiz) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contener, new myquizFragment(), null);
            ft.commit();


        } //else if (id == R.id.mark) {
           /// FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           // ft.replace(R.id.contener, new markFragment(), null);
          //  ft.commit();

       // }
        else if (id == R.id.loguot) {
            Intent i = new Intent(NavDoctor.this, LogInActivity.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        txt.setText("");
        return true;
    }


    public void getuser(final String uname) {
        pd = new ProgressDialog(NavDoctor.this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(NavDoctor.this);
        String url = config.URL + "getAllUsersWithImage.php";
        //Toast.makeText(getApplicationContext(),"Mohammad",Toast.LENGTH_LONG).show();


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                Toast.makeText(getApplicationContext(), "Mohammad", Toast.LENGTH_LONG).show();


                try {
                    JSONObject o = new JSONObject(s);
                    String data = o.getString("result");
                    JSONArray arr = new JSONArray(data);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject x = arr.getJSONObject(i);
                        String name = x.getString("name");
                        String Email = x.getString("Email");
                        doctorname = name;
                        email = Email;
                    }
                    Toast.makeText(NavDoctor.this, doctorname + "" + email, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(NavDoctor.this, errorDescription, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", uname.toString());

                return param;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);

    }

    public CreateQuiz_Frag newInstance() {
        CreateQuiz_Frag fragment = new CreateQuiz_Frag();
        Bundle args = new Bundle();
        args.putString("doctid", id);
        fragment.setArguments(args);
        return fragment;
    }
    public  myquizFragment newInstance2(){
        myquizFragment fragment = new myquizFragment();
        Bundle args = new Bundle();
        args.putString("doctid", id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK);

        return false;
        // Disable back button..............
    }
}

