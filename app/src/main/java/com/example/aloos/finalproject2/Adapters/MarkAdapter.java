package com.example.aloos.finalproject2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.aloos.finalproject2.HelperClass.StudentInfo;
import com.example.aloos.finalproject2.R;

import java.util.ArrayList;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.viewitem> {
    Context context;
    ArrayList<StudentInfo> items;
    TableRow tbrow;
    public MarkAdapter(Context c,ArrayList<StudentInfo> item){
        context=c;
        items=item;
    }

    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare

       TableLayout table;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
         //   row=itemView.findViewById(R.id.);
           //  tbrow = new TableRow(context);
           // tbrow.setBackgroundColor(Color.parseColor("#2d2d5a"));
           // tbrow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            table=itemView.findViewById(R.id.mytable);
            tbrow=itemView.findViewById(R.id.tablerow);




        }
    }
    @NonNull
    @Override
    public viewitem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.markrow, parent, false);
        return new viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewitem holder, int position) {


        for(int i=0;i<items.size();i++)
        {
            TableRow  row=new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            String stdname=items.get(i).getStdname();
            String stdid = items.get(i).getStdid();
            String mark=items.get(i).getMark();
            TextView tv1=new TextView(context);
            tv1.setText(stdid);
            TextView tv2=new TextView(context);
            tv2.setText(stdname);
            TextView tv3=new TextView(context);
            tv3.setText(mark);
            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            holder.table.addView(row);

        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
