package com.kosmo.ratingbar14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯 얻기
        Button btnInc = findViewById(R.id.button_inc);
        Button btnDesc = findViewById(R.id.button_desc);
        RatingBar bar1 = findViewById(R.id.ratingbar1);
        RatingBar bar2 = findViewById(R.id.ratingbar2);
        RatingBar bar3 = findViewById(R.id.ratingbar3);

        //button에 리스너 부착
        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //기존 rating값 + RatingBar의 stepSize 값
                bar1.setRating(bar1.getRating()+ bar1.getStepSize());
                bar2.setRating(bar2.getRating()+ bar2.getStepSize());
                bar3.setRating(bar3.getRating()+ bar3.getStepSize());
            }
        });

        btnDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //기존 rating값 - RatingBar의 stepSize 값
                bar1.setRating(bar1.getRating()- bar1.getStepSize());
                bar2.setRating(bar2.getRating()- bar2.getStepSize());
                bar3.setRating(bar3.getRating()- bar3.getStepSize());
            }
        });


    }///////onCreate


}