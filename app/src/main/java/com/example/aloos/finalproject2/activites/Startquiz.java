package com.example.aloos.finalproject2.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.R;
import com.example.aloos.finalproject2.fragment.FillBlankFrag;
import com.example.aloos.finalproject2.fragment.multiblechoice;

import java.util.ArrayList;


public class Startquiz extends AppCompatActivity {

    ArrayList<Questions> data;
    TextView t;
    Boolean[] arr ;
    int q;
    int count = 0;
    String Flag;
    ArrayList<Questions> Mc,Fill;
    String answer;
    String name,id,qid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startquiz);
        t = findViewById(R.id.Timer);

        data = (ArrayList<Questions>) getIntent().getExtras().getSerializable("Data");
        name=getIntent().getExtras().getString("name");
        id=getIntent().getExtras().getString("id");
        qid=getIntent().getExtras().getString("qid");
         arr  = new Boolean[data.size()];
         for (int i=0;i<arr.length; i++)
             arr[i]=false;
       String quiztime = data.get(0).getTimer();
        q = Integer.parseInt(quiztime);
        new CountDownTimer(q * 60000, 60000) {

            public void onTick(long millisUntilFinished) {
                t.setText("Time remaining: " + millisUntilFinished / 60000 + " Minutes");

            }

            public void onFinish() {
               endQuiz();
            }

        }.start();

        Flag = data.get(count).getFlag();
        if (Flag.equals("A")) {
               multiblechoice f=new multiblechoice();
             FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

            Bundle  bundle= new Bundle();
            bundle.putString("question",data.get(count).getQuestion());
            bundle.putString("T_A",data.get(count).getT_A());
            bundle.putString("F_A1",data.get(count).getF_A1());
            bundle.putString("F_A2",data.get(count).getF_A2());
            bundle.putString("F_A3",data.get(count).getF_A3());

            f.setArguments(bundle);
            ft.replace(R.id.content,f,null);
            ft.commit();
            //  Toast.makeText(this, "" + data.get(count).getFlag(), Toast.LENGTH_SHORT).show();

        } else {
              FillBlankFrag f=new FillBlankFrag();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Bundle b= new Bundle();
            b.putString("Question",data.get(count).getQuestion());
            b.putString("trueanwser",data.get(count).getT_A());
            f.setArguments(b);
            ft.replace(R.id.content,f);
            ft.commit();
        }



    }

    private AlertDialog skipQuestion(){

        AlertDialog alertDialog =new AlertDialog.Builder(Startquiz.this)
                .setTitle("Warning")
                .setMessage("Do you want to Skip this question? the answer will be wrong")


                .setPositiveButton("Skip", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                     nextQuestion();

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
    public void next(View view) {
        if(Flag.equals("A")){
               if(multiblechoice.isAnswer == true)
            nextQuestion();
        else{
            skipQuestion().show();

               }
        } else{

                if(FillBlankFrag.isAnswer == true)
                    nextQuestion();
                else{
                    skipQuestion().show();

                }
            }
}


    public void nextQuestion(){
        if(Flag.equals("A")){
            arr[count] = multiblechoice.a;
        }            else{
            if(FillBlankFrag.answer.equals(data.get(count).getT_A()))
                arr[count] = true;


        }
        count++;
        if (count < data.size()) {
            Flag = data.get(count).getFlag();
            if (Flag.equals("A")) {
                multiblechoice f=new multiblechoice();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

                Bundle  bundle= new Bundle();
                bundle.putString("question",data.get(count).getQuestion());
                bundle.putString("T_A",data.get(count).getT_A());
                bundle.putString("F_A1",data.get(count).getF_A1());
                bundle.putString("F_A2",data.get(count).getF_A2());
                bundle.putString("F_A3",data.get(count).getF_A3());
                f.setArguments(bundle);
                ft.replace(R.id.content,f,null);
                ft.commit();

            } else {
                FillBlankFrag f=new FillBlankFrag();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                Bundle b= new Bundle();
                b.putString("Question",data.get(count).getQuestion());
                b.putString("trueanswer",data.get(count).getT_A());
                f.setArguments(b);
                ft.replace(R.id.content,f);
                ft.commit();

            }
        } else {
            endQuiz();
        }


    }
    public void endQuiz(){
        int score=0;
        for (int i=0;i<arr.length;i++){
            if (arr[i]==true){
                score++;
            }
        }
        //  Toast.makeText(this, ""+scoure, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this , FinalScore.class);
        i.putExtra("score" , score);
        i.putExtra("size" , arr.length);
        i.putExtra("sname",name);
        i.putExtra("sid",id);
        i.putExtra("qid",qid);
        startActivity(i);
        finish();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "You will end the quiz",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

}

