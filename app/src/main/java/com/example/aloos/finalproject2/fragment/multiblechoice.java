package com.example.aloos.finalproject2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aloos.finalproject2.HelperClass.Questions;
import com.example.aloos.finalproject2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class multiblechoice extends Fragment {
    TextView question;
    String Question;

    private String  T_A;
    private String F_A1;
    private String F_A2;
    private String F_A3;

    ArrayList<String > x= new ArrayList<String>();



    String answer = "";
    RadioButton rd1, rd2, rd3, rd4;
    RadioGroup radioGroup;
   public static boolean a;
   public static boolean isAnswer ;
    public multiblechoice() {
a=false;
isAnswer = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_multiblechoice, container, false);
        Question = getArguments().getString("question");
        T_A = getArguments().getString("T_A");
        F_A1 = getArguments().getString("F_A1");
        F_A2 = getArguments().getString("F_A2");
        F_A3 = getArguments().getString("F_A3");
        radioGroup = view.findViewById(R.id.RadioGroup);
        question = view.findViewById(R.id.Qustionm);
        question.setText(Question);

        rd1 = view.findViewById(R.id.trueRadioButton);
        rd2 = view.findViewById(R.id.false1RadioButton);
        rd3 = view.findViewById(R.id.false2RadioButton);
        rd4 = view.findViewById(R.id.false3RadioButton);
        x.add(T_A);
        x.add(F_A1);
        x.add(F_A2);
        x.add(F_A3);

     /*   int z =  (int)(Math.random() * ((x.length - 1) + 1));
       rd1.setText(x[z]);
       x[z] = null;



        List<RadioButton> buttons = new ArrayList<>(4);

        buttons.add(trueAnswer1);
        buttons.add(FalseAnswer1);
        buttons.add(FalseAnser2);
        buttons.add(FalseAnswer3);
        Collections.shuffle(buttons);
        for(RadioButton rb:
                buttons){
            radioGroup.addView(rb,new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }*/
     Collections.shuffle(x);
        rd1.setText(x.get(0));
        rd2.setText(x.get(1));
        rd3.setText(x.get(2));
        rd4.setText(x.get(3));

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    isAnswer = true;
                    answer = ((RadioButton) view.findViewById(group.getCheckedRadioButtonId()))
                            .getText().toString();
                 //   Toast.makeText(getActivity(), answer, Toast.LENGTH_SHORT).show();
                    if (answer.equals(T_A)){
                    a=true;
                    } else
                        a=false;


                }
            });




        return view;

    }
    public boolean Answer(){
        return a;
    }



}
