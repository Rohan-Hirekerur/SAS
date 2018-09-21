package com.example.rohan.sas;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements Question.OnCallbackReceived, SubmitFragment.submitInterface{
    private FirebaseAuth mAuth;
    private Button submit;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button next;
    private Button previous;
    private TextView stat;
    ArrayList<Integer> markedAnswers;
    private ArrayList<MCQ> list;
    TextView timer;
    Button bookmark;
    Lists lists;
    int page = 0;

    ArrayList<Boolean> bookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        submit = findViewById(R.id.submit);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        stat = findViewById(R.id.stat);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        mAuth = FirebaseAuth.getInstance();
        markedAnswers = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");
        bookmark=findViewById(R.id.bookmark);
        bookmarked=new ArrayList<>();

        for (int i=0; i<11; i++) {
            markedAnswers.add(i,0);
            bookmarked.add(i,false);
        }
        lists=new Lists(bookmarked,markedAnswers);

        tabLayout.addTab(tabLayout.newTab().setText("Q1"));
        tabLayout.addTab(tabLayout.newTab().setText("Q2"));
        tabLayout.addTab(tabLayout.newTab().setText("Q3"));
        tabLayout.addTab(tabLayout.newTab().setText("Q4"));
        tabLayout.addTab(tabLayout.newTab().setText("Q5"));
        tabLayout.addTab(tabLayout.newTab().setText("Q6"));
        tabLayout.addTab(tabLayout.newTab().setText("Q7"));
        tabLayout.addTab(tabLayout.newTab().setText("Q8"));
        tabLayout.addTab(tabLayout.newTab().setText("Q9"));
        tabLayout.addTab(tabLayout.newTab().setText("Q10"));
        tabLayout.addTab(tabLayout.newTab().setText("Review"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        list= new ArrayList<>();

        final ProgressDialog dialog = new ProgressDialog(QuizActivity.this);
        dialog.setTitle("Please Wait...");
        dialog.setMessage("Downloading test questions");
        dialog.setCancelable(false);
        dialog.show();
        previous.setVisibility(View.GONE);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int count = (int)dataSnapshot.getChildrenCount();
                ArrayList<Integer> randomNumbers = new ArrayList<>();
                for(int i=0; i<15; i++) {
                    Integer rand = new Random().nextInt(count);
                    randomNumbers.add(rand);
                }
                try {
                    Integer qNo = new Integer(0);
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        if(randomNumbers.contains(qNo)) {
                            MCQ m = data.getValue(MCQ.class);
                            list.add(m);
                        }
                        qNo++;
                    }

                    dialog.dismiss();


                    new CountDownTimer(900000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            long min=millisUntilFinished/60000;
                            float m=millisUntilFinished/(float)60000;
                            float rem=m-(int)m;
                            int sec =(int)( rem*60);
                            String s;
                            if(sec<10)
                                 s=min+":0"+sec;
                            else
                                 s=min+":"+sec;

                            timer.setText(s);
                        }

                        public void onFinish() {
                            timer.setText("0:00");
                            int result=0;
                            for(int i=0;i<10;i++)
                            {
                                MCQ m= list.get(i);
                                if(m.getCorrectOption()==markedAnswers.get(i))
                                {
                                    result++;
                                }
                            }
                            Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                            intent.putExtra("ans",String.valueOf(result));
                            startActivity(intent);
                            finish();

                        }
                    }.start();
                    final com.example.rohan.sas.PagerAdapter adapter = new com.example.rohan.sas.PagerAdapter
                            (getSupportFragmentManager(), tabLayout.getTabCount(),list);

                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            page = tab.getPosition();
                            viewPager.setCurrentItem(tab.getPosition());
                            int pg = viewPager.getCurrentItem();
                            if(bookmarked.get(pg))
                                bookmark.setBackground(getResources().getDrawable(R.drawable.bookmark));
                            else
                                bookmark.setBackground(getResources().getDrawable(R.drawable.unbook));

                            pg+=1;
                            if(pg!=11) {
                                stat.setText(pg + "/10");
                                submit.setText("Review");
                                next.setVisibility(View.VISIBLE);
                                previous.setVisibility(View.VISIBLE);
                                bookmark.setVisibility(View.VISIBLE);
                            }
                            else {
                                stat.setText("");
                                submit.setText("Submit");
                                next.setVisibility(View.GONE);
                                previous.setVisibility(View.VISIBLE);
                                lists=new Lists(bookmarked,markedAnswers);
                                bookmark.setVisibility(View.GONE);
                            }
                            if(pg==1)
                                previous.setVisibility(View.GONE);

                        }



                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() != 10) {
                    viewPager.setCurrentItem(10);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                    builder.setTitle("Are you sure you want to submit?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int in) {
                                    int result=0;
                                    for(int i=0;i<10;i++)
                                    {
                                        MCQ m= list.get(i);
                                        if(m.getCorrectOption()==markedAnswers.get(i))
                                        {
                                            result++;
                                        }
                                    }
                                    Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                                    intent.putExtra("ans",String.valueOf(result));
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setCancelable(true)
                            .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem()!=10)
                {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
                else
                    Toast.makeText(QuizActivity.this,"No next page",Toast.LENGTH_SHORT).show();
            }

        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem()!=0)
                {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                }
                else
                    Toast.makeText(QuizActivity.this,"No previous page",Toast.LENGTH_SHORT).show();
            }
        });


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bookmarked.get(viewPager.getCurrentItem())) {
                    bookmarked.set(viewPager.getCurrentItem(), true);
                    bookmark.setBackground(getResources().getDrawable(R.drawable.bookmark));
                    Toast.makeText(QuizActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();
                }
                else {
                    bookmarked.set(viewPager.getCurrentItem(), false);
                    bookmark.setBackground(getResources().getDrawable(R.drawable.unbook));
                    Toast.makeText(QuizActivity.this, "Bookmark removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exam, menu);

        timer= new TextView(QuizActivity.this);
        timer.setText("15:00");
        timer.setTextColor(getResources().getColor(R.color.colorWhite));
        timer.setTextSize(22);
        timer.setTypeface(null, Typeface.BOLD);
        menu.add(0,0,1,"").setActionView(timer).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.logOut:
                mAuth.signOut();
                Intent intent=new Intent(QuizActivity.this,MainActivity.class);
                startActivity(intent);
                finish();


        }
        return true;

    }

    public void Update(Integer pos,Integer ans) {
        markedAnswers.set(pos,ans);
    }

    @Override
    public void onBackPressed() {

    }

    public void putPage(int i) {
        viewPager.setCurrentItem(i);
    }

    public Lists getLists()
    {
        return lists;
    }


}
