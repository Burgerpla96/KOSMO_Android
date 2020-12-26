package com.kosmo.touchnevent19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClick = findViewById(R.id.button_click);
        Button btnTouch = findViewById(R.id.button_touch);
        ImageView imageView = findViewById(R.id.imageview);

        //익명 클래스로 이벤트 달기
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//click 이벤트가 발생한 View 가 전달된다.
                Log.i("com.kosmo.touchnevent","버튼에서 이벤트 발생");

            }
        });

        btnTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //버튼을 제외한 대부분의 뷰는
                //true 반환시 다른 뷰에 사용자의 터치 이벤트가 전달되지 않음.

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i("com.kosmo.touchnevent","버튼에서 터치(Down)이벤트 발생");
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i("com.kosmo.touchnevent","버튼에서 터치(Up)이벤트 발생");
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Log.i("com.kosmo.touchnevent","버튼에서 터치(Move)이벤트 발생");
                }

                return false;
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("com.kosmo.touchnevent","이미지 뷰에서 터치 이벤트 발생");
                return false;//다른 뷰에 사용자의 터치 이벤트가 전달
            }
        });


    }///////////onCreate


    //액티비티에 다른 뷰에서 발생한 터치 이벤트가 전달되는지 확인
    //액티비티의 화면 터치시마다 자동으로 호출되는 메서드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("com.kosmo.touchnevent","activity에서 터치 이벤트 발생");
        return false;
    }
}/////////class