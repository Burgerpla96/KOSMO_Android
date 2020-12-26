package com.kosmo.linearlayout02_02;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //레이아웃용 xml파일 불필요, 아래코드 주석
        //setContentView(R.layout.activity_main);
        setTitle("자바코드로 동적화면 구성");
        //1) LinearLayout 객체 생성
        LinearLayout layout = new LinearLayout(this);//MainActivity 자체가 context라는 의미.
        //2) orientation 속성 설정
        layout.setOrientation(LinearLayout.HORIZONTAL);
        //3) 가로폭/ 세로폭 설정
        LinearLayout.LayoutParams params = //내부 정적클래스
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        //3-1) LayoutParams를 가지고 LinearLayout객체에 적용
        layout.setLayoutParams(params);
        layout.setBackgroundColor(Color.rgb(255,255,0));
        //3-2) content 가운데 정렬
        layout.setGravity(Gravity.CENTER);
        //CENTER는 CENTER_HORIZONTAL과 CENTER_VERTICAL을 합친것
        //4) Button타입 선언
        Button btnOne,btnTwo;
        //5) Button타입 객체 생성
        btnOne = new Button(this);
        btnTwo = new Button(this);
        //6) Button의 속성 설정
        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        btnOne.setLayoutParams(btnParams);
        btnOne.setText("버튼1");
        btnOne.setTextColor(Color.RED);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        //버튼의 가로, 세로 미지정시, 디폴트가 WRAP_CONTENT 이다.
        btnTwo.setText("버튼2");


        //7) 버튼에 이벤트 달기
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnOne){//주소로 비교하기
                    Toast.makeText(v.getContext(), "첫 번째 버튼입니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(v.getContext(), "두 번째 버튼입니다.",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(v.getContext(), R.string.button2,Toast.LENGTH_SHORT).show();
                    Toast.makeText(v.getContext(), getResources().getString(R.string.button2),Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnOne.setOnClickListener(listener);
        btnTwo.setOnClickListener(listener);

        //) layout에 붙이기 addView() method
        layout.addView(btnOne);
        layout.addView(btnTwo);

        //끝) Layout 전개
        setContentView(layout);
    }//onCreate
}