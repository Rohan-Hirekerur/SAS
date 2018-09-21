package com.example.rohan.sas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.PriorityQueue;
import java.util.zip.Inflater;

public class Question extends Fragment {
    private TextView qNum;
    private TextView question;
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Integer position;
    OnCallbackReceived mCallback;

    public int getAns() {
        return ans;
    }

    public interface OnCallbackReceived {
        public void Update(Integer pos,Integer markedAns);
    }
    private int ans;

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
            position = (Integer)bundle.getInt("pos");


            question.setText(m.getQuestion());
            option1.setText(m.getOption1());
            option2.setText(m.getOption2());
            option3.setText(m.getOption3());
            option4.setText(m.getOption4());
        }catch (Exception e) {
            e.printStackTrace();
        }


//        Intent i = new Intent(getContext(),QuizActivity.class);
//        i.putExtra("ans",0);
//        startActivity(i);
        //mCallback.Update(0);

//
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.option1:
                        mCallback.Update(position,1);
                        break;
                    case R.id.option2:
                        mCallback.Update(position,2);
                        break;
                    case R.id.option3:
                        mCallback.Update(position,3);
                        break;
                    case R.id.option4:
                        mCallback.Update(position,4);
                        break;
                }

            }
        });



        return view;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnCallbackReceived) activity;
        } catch (ClassCastException e) {

        }
    }

    // You can Call the event from fragment as mentioned below
    // mCallback is the activity context.

}
