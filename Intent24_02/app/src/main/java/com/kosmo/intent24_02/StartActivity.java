package com.kosmo.intent24_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        //전달된 Intent받기
        Intent intent = getIntent();
        //넣는것은 putExtra()로 통일, 받는 메서드는 getXXXExtra()
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        //TextView 에 얻어온 값 표시하기
        ((TextView)findViewById(R.id.textStartActivity))
                .setText(String.format("아이디: %s, 비밀번호: %s",user,pass));
        //메인으로 돌아가는 버튼
        findViewById(R.id.btnStartActivityFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }/////////onCreate
}
