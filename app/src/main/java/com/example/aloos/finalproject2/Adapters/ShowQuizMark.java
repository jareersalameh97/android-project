package com.example.aloos.finalproject2.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.aloos.finalproject2.activites.Mark;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowQuizMark extends RecyclerView.Adapter<ShowQuizMark.viewitem> {

    ArrayList<ShowQuiz> items;
    Context context;
    String Q;
    public ShowQuizMark(Context c, ArrayList<ShowQuiz> item)
    {
        items=item;
        context=c;

    }
    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView Quizname;
        ImageView Quizimage;
        Button ShowMark;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            Quizname=  itemView.findViewById(R.id.quiznametxt);

            ShowMark=itemView.findViewById(R.id.showmark);
            Quizimage=  itemView.findViewById(R.id.imgquizmark);




        }
    }

    public viewitem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizmarkraw, parent, false);


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
        holder.Quizname.setText("Quiz Name: "+items.get(position).getQuizname());
        holder.ShowMark.setText(R.string.Showmark);
       // holder.Quizname.setText("Show Mark");
        holder.ShowMark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Mark.class);
                i.putExtra("quizidddd",items.get(position).getQuizid());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}

