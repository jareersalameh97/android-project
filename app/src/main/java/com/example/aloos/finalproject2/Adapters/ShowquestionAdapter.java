package com.example.aloos.finalproject2.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.HelperClass.ShowQuiz;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.Services.config;
import com.example.aloos.finalproject2.activites.Showquestions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowquestionAdapter extends RecyclerView.Adapter<ShowquestionAdapter.viewitem> {

    ArrayList<Questions> items;
    Context context;
    String Q;
    public ShowquestionAdapter(Context c, ArrayList<Questions> item)
    {
        items=item;
        context=c;

    }
    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView Qname;
        ImageView Qimage;
        Button delete1;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            Qname=  itemView.findViewById(R.id.questionshow);

            delete1=itemView.findViewById(R.id.delQ);
             Qimage=  itemView.findViewById(R.id.imageView2);




        }
    }

    public viewitem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowshow, parent, false);


        //this on click is for the row
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return new viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewitem holder, final int position) {
        holder.Qname.setText("Question: "+items.get(position).getQuestion());

        holder.delete1.setText(R.string.delete);
        holder.delete1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Q=items.get(position).getQuestion();
               del(position).show();
                
                Toast.makeText(context,Q,Toast.LENGTH_SHORT).show();
            new ShowquestionAdapter(context,items);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private AlertDialog del(final int position){

        Toast.makeText(context,Q,Toast.LENGTH_SHORT).show();
        AlertDialog alertDialog =new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Do you want to Delete")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        items.remove(position);
                        notifyDataSetChanged();
                       deleteQuestion();
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


    public void deleteQuestion(){
        final ProgressDialog pd;
        pd = new ProgressDialog(context);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = config.URL + "deleteQuestion.php";


        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();




                try {
                    JSONObject o=new JSONObject(s);
                    String data=o.getString("result");
                    if(data.equals("1"))
                    {
                        Toast.makeText(context,"Delete Question Successfully",Toast.LENGTH_LONG).show();}
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
                param.put("question",Q);



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
