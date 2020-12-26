package com.kosmo.notification22_02;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//나만의 activity 생성
//1) extends AppCompatActivity
public class KosmoNotificationActivity extends AppCompatActivity {
    //2)인자가 하나인  onCreate Override
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //액션바 없애기
        getSupportActionBar().hide();
        //뷰전개
        setContentView(R.layout.notification_layout);
        String value = getIntent().getStringExtra("kosmo");//intent를 통해 전달된 메시지 얻기
        ((TextView)findViewById(R.id.notiText)).setText(value);

    }
}
