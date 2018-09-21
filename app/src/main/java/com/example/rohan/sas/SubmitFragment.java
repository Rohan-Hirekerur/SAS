package com.example.rohan.sas;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends Fragment {

    private Lists lists;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;
    private TextView t7;
    private TextView t8;
    private TextView t9;
    private TextView t10;
    private TextView answered;
    private TextView bookmarked;


    public SubmitFragment() {
        // Required empty public constructor
    }

    public interface submitInterface{
        public Lists getLists();
        void putPage(int i);
    }

    submitInterface submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit, container, false);

        t1 = view.findViewById(R.id.Q1);
        t2 = view.findViewById(R.id.Q2);
        t3 = view.findViewById(R.id.Q3);
        t4 = view.findViewById(R.id.Q4);
        t5 = view.findViewById(R.id.Q5);
        t6 = view.findViewById(R.id.Q6);
        t7 = view.findViewById(R.id.Q7);
        t8 = view.findViewById(R.id.Q8);
        t9 = view.findViewById(R.id.Q9);
        t10 = view.findViewById(R.id.Q10);
        answered = view.findViewById(R.id.totalans);
        bookmarked = view.findViewById(R.id.totalbookmark);

        ArrayList<Integer> ansList = lists.getMarked();
        ArrayList<Boolean> bookList = lists.getBookmarked();
        ArrayList<TextView> textViews = new ArrayList<>();

        textViews.add(0,t1);
        textViews.add(1,t2);
        textViews.add(2,t3);
        textViews.add(3,t4);
        textViews.add(4,t5);
        textViews.add(5,t6);
        textViews.add(6,t7);
        textViews.add(7,t8);
        textViews.add(8,t9);
        textViews.add(9,t10);


        int countAns=0;
        int countBookmark=0;

        for(int i=0;i<10;i++){
            if(ansList.get(i)!=0){
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                countAns++;
            }
            else {
                textViews.get(i).setBackground(getResources().getDrawable(R.drawable.border));
            }
            if(bookList.get(i)){
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                textViews.get(i).setTextColor(getResources().getColor(R.color.colorWhite));
                countBookmark++;
            }
        }
        answered.setText(countAns+"/10");
        bookmarked.setText(countBookmark+"");

        for (int i=0; i<10; i++) {
            final int x = i;
            textViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submit.putPage(x);
                }
            });
        }

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        submit=(submitInterface) activity;
        lists=submit.getLists();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
