package com.kosmo.intent24_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private int[] tvResIds = {R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,
                            R.id.tv6,R.id.tv7,R.id.tv8,R.id.tv9};
    private int[] rbResIds ={R.id.rb1,R.id.rb2,R.id.rb3,R.id.rb4,R.id.rb5,
                            R.id.rb6,R.id.rb7,R.id.rb8,R.id.rb9};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        //Intent get계열로 데이터 받기
        Intent intent =getIntent();
        int[] votes = intent.getIntArrayExtra("votes");
        String[] titles = intent.getStringArrayExtra("titles");
        //위젯얻고 설정
        for(int i=0;i<tvResIds.length;i++){
            ((TextView)findViewById(tvResIds[i])).setText(titles[i]);
            ((RatingBar)findViewById(rbResIds[i])).setRating(votes[i]);
        }
        //메인으로 전환위한 버튼 설정
        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
