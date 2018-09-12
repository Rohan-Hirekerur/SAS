package com.example.rohan.sas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

public class Question extends Fragment {
    private TextView qNum;
    private TextView question;
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        qNum = view.findViewById(R.id.qNum);
        question = view.findViewById(R.id.question);
        radioGroup = view.findViewById(R.id.choices);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);

        Bundle bundle = getArguments();
        try {
            MCQ m = (MCQ) bundle.getSerializable("mcq");


            qNum.setText(m.getKey());
            question.setText(m.getQuestion());
            option1.setText(m.getOption1());
            option2.setText(m.getOption2());
            option3.setText(m.getOption3());
            option4.setText(m.getOption4());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }
}
