package com.example.aloos.finalproject2.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.RenderScript;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.aloos.finalproject2.HelperClass.ShowQuiz;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.activites.QuastionType;
import com.example.aloos.finalproject2.activites.Showquestions;
import com.example.aloos.finalproject2.activites.Signup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleAdapter extends    RecyclerView.Adapter<RecycleAdapter.viewitem> {



    ArrayList<ShowQuiz> items;
    Context context;
    String quizid2;


    public RecycleAdapter(Context c, ArrayList<ShowQuiz> item)
    {
        items=item;
        context=c;

    }


    //The View Item part responsible for connecting the row.xml with
    // each item in the RecyclerView
    //make declare and initalize
    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView name,quizid,quizd;
       // ImageView image;
       Button btn,btn2,delete;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            name=  itemView.findViewById(R.id.quiznametxt);
            quizid=itemView.findViewById(R.id.quizidtxt);
            delete=itemView.findViewById(R.id.del);
           // image=  itemView.findViewById(R.id.img);
            quizd= itemView.findViewById(R.id.quizdurationtxt);
            btn=itemView.findViewById(R.id.addQ);
            btn2=itemView.findViewById(R.id.showquastion);
            btn2.setText(R.string.myquastions);



        }
    }



    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);


        //this on click is for the row
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //this on click is for the button
       /* itemView.findViewById(R.id.btnBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"Sa7a wa Hana",Toast.LENGTH_SHORT).show();

                Intent i=new Intent(context,A2.class);
                context.startActivity(i);
            }
        });*/



        return new viewitem(itemView);
    }






    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(final viewitem holder, final int position) {
        holder.name.setText(holder.name.getText().toString()+ items.get(position).getQuizname());
        holder.quizid.setText(holder.quizid.getText().toString()+ items.get(position).getQuizid());
        holder.quizd.setText(holder.quizd.getText().toString()+ items.get(position).getQuizduration());
        quizid2 = holder.quizid.getText().toString();
        holder.btn.setText(R.string.addquastion);


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Qid = items.get(position).getQuizid();
                Toast.makeText(context, items.get(position).getQuizname(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, QuastionType.class);
                i.putExtra("qid", Qid);
                context.startActivity(i);


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                quizid2=items.get(position).getQuizid();
                del(position).show();



            }
        });
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizid2=items.get(position).getQuizid();
                Intent i = new Intent(context, Showquestions.class);
                i.putExtra("Quizidshow",quizid2);
                context.startActivity(i);

            }
        });
    }

       private AlertDialog del(final int position){
           Toast.makeText(context,position +"",Toast.LENGTH_SHORT).show();

           AlertDialog alertDialog =new AlertDialog.Builder(context)
                   .setTitle("Delete")
                   .setMessage("Do you want to Delete")


                   .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                       public void onClick(DialogInterface dialog, int whichButton) {
                           deleteQuiz();
                           items.remove(position);
                           notifyDataSetChanged();
                           dialog.dismiss();

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
    public int getItemCount() {
        return items.size();
    }

public void deleteQuiz(){
        final ProgressDialog pd;
    pd = new ProgressDialog(context);
    pd.setTitle("Loading");
    pd.setMessage("Please wait...");
    pd.show();

    RequestQueue queue = Volley.newRequestQueue(context);
    String url = config.URL + "deleteQuiz.php";


    StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {

            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




            try {
                JSONObject o=new JSONObject(s);
                String data=o.getString("result");
                if(data.equals("1"))
                {
                    Toast.makeText(context,"Delete Quiz Successfully",Toast.LENGTH_LONG).show();}
                else
                {Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();}
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
            Toast.makeText(context, errorDescription,Toast.LENGTH_SHORT).show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> param = new HashMap<String,String>();
            param.put("quizid",quizid2);



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

