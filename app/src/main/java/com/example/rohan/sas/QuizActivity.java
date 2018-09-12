package com.example.rohan.sas;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        final ArrayList<MCQ> list= new ArrayList<>();
        for (int i=0; i<11; i++) {
            MCQ m = new MCQ("Question" + i,"opt1","opt2","opt3","opt4",i%4+1,"" + i);
            list.add(m);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 9"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 10"));
        tabLayout.addTab(tabLayout.newTab().setText("Submit"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        Bundle bundle[] = new Bundle[11];
//        for(int i=0; i<11; i++) {
//            bundle[i].putSerializable("mcq",list.get(i));
//        }
        final com.example.rohan.sas.PagerAdapter adapter = new com.example.rohan.sas.PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),list);



        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("mcq",list.get(tab.getPosition()));
//                adapter.getItem(tab.getPosition()).setArguments(bundle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exam, menu);
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
}
