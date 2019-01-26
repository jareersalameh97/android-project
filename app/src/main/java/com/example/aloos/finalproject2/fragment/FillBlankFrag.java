package com.example.aloos.finalproject2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aloos.finalproject2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FillBlankFrag extends Fragment {
String Q , t;
TextView Question;
EditText answer1;
public static String answer;
    public static boolean isAnswer ;

    public FillBlankFrag() {
        // Required empty public constructor
        answer="";
        isAnswer = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fill_blank, container, false);
        answer1= v.findViewById(R.id.answer);
        Question=v.findViewById(R.id.QuestionF);
        Q=getArguments().getString("Question");
        t=getArguments().getString("trueanswer");
        Question.setText(Q);
answer1.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isAnswer=true;
        if(answer1.getText().toString() != null)
      answer = answer1.getText().toString();
        else
            answer = "";
    }
});
        return v;
    }

}
