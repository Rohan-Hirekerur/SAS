package com.example.rohan.sas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView score;
    private TextView res;
    private Button exit;
    private Button retake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Integer result = new Integer(0);
        res = findViewById(R.id.text2);

        exit = findViewById(R.id.exit);
        retake = findViewById(R.id.retake);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            result = Integer.parseInt(extras.getString("ans"));
        }
        //Integer result= Integer.parseInt(getIntent().getStringExtra("ans"));
        int percent = result*10;
        score = findViewById(R.id.score);
        score.setText(percent+"%");

        if(percent<40){
            score.setTextColor(getResources().getColor(R.color.fail));
            res.setText("You failed, Try again!");
        }
        else{
            score.setTextColor(getResources().getColor(R.color.colorGreenLight));
            res.setText("Congratulations, you passed..!!");
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,InstructionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
