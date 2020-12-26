package com.kosmo.intent24_02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForResultActivity extends AppCompatActivity {

    //오류 코드
    public static final int RESULT_CODE_ID_FAIL = 1000;
    public static final int RESULT_CODE_PWD_FAIL = 1001;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forresult_layout);

        //전달된 Intent받기
        Intent intent = getIntent();
        //넣는것은 putExtra()로 통일, 받는 메서드는 getXXXExtra()
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        //TextView 에 얻어온 값 표시하기
        ((TextView)findViewById(R.id.textStartActivityForResult))
                .setText(String.format("아이디: %s, 비밀번호: %s",user,pass));

        findViewById(R.id.btnStartActivityForResultFinish) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디가 KIM이고 pwd 1234면 회원이라고 가정
                //setResult(); 호출.
                //setResult 호출과 동시에 인텐트를 보낸 액티비티의 onActivityResult()가 호출된다.
                if(!"KIM".equals(user)){
                    intent.putExtra("IDFAIL","아이디가 틀려요");
                    setResult(RESULT_CODE_ID_FAIL,intent);
                }
                else if(!"1234".equals(pass)){
                    intent.putExtra("PWDFAIL","비번이 틀려요");
                    setResult(RESULT_CODE_PWD_FAIL,intent);
                }
                else {
                    intent.putExtra("SUCCESS",user+"님 방가~");
                    setResult(Activity.RESULT_OK,intent);
                }
                finish();
            }
        });



    }//////onCreate
}
