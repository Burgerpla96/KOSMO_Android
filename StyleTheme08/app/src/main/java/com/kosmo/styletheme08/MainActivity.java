package com.kosmo.styletheme08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends Activity {
    //android.R로 actionbar를 없애려면 Activity 상속 받기
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        내가 만든 태마 적용시 R.*.*(R 클래스, 두번째는 tag이름, 3번째는tag의 name속성)에
        자동으로 등록된다.
        android제공 리소스는 android.R.*.*로 접근
        */
        super.onCreate(savedInstanceState);
        //코드로 테마 없애기
        //this.setTheme(R.style.ThemeMadeByMe);
        //코드로 액션 바만 없애기
        getSupportActionBar().hide();
        // setTheme(android.R.style.Theme_Material_Light_NoActionBar);
        //액션바와 상태바까지 제거하기
        //setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        //레이아웃 전개
        setContentView(R.layout.activity_main);
        //위젯 얻기
        Button btnLandscape = findViewById(R.id.landscapeMode);
        Button btnPortrait = findViewById(R.id.portraitMode);
        //위젯에 리스너 부착
        btnLandscape.setOnClickListener(listener);
        btnPortrait.setOnClickListener(listener);

    }////onCreate



    //이벤트 핸들러 정의
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.landscapeMode){ //R에 정의된 상수로 비교
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            else{//세로모드로 변경
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    };


}