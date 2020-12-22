package com.example.myapplication_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity {



    TextView sub_result;
    Button sub_retry;
    SharedPreferences spf = null;
    TextView hhh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result); //주소로 알고있는 xml을 눈에 보이는 view로 바꿔줌 ->InfLate
        ImageView load=(ImageView) findViewById(R.id.loading_view);
        hhh=findViewById(R.id.hhh);
        Glide.with(this).load(R.raw.rabbit).into(load);




        spf = getSharedPreferences("spfScore",MODE_PRIVATE); // 키값이 또 있으면 덮어쓰겠다

        sub_result = findViewById(R.id.sub_result);
        sub_retry = findViewById(R.id.sub_retry);

        int score = getIntent().getIntExtra("score", -1);
        sub_result.setText(String.valueOf(score));

        if(score<5){
            hhh.setText("열심히 분발하세요! BAD");
        }
        else{
            if(score<10){
                hhh.setText("조금 더 열심히하면 될거에요! SOSO");
            }
            else{
            hhh.setText("와우 잘하고있어요!GOOD");
        }}


        if(spf.getInt("spfscore",0) < score){ //내점수가 저번 점수보다 크면
            spf.edit().putInt("spfscore",score).commit(); //반영의 commit(). 현재상태 저장
            sub_result.setText("신기록달성\n"+score);
        }


        sub_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(intent);
                finish();


            }
        });

    }
}