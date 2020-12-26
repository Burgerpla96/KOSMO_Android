package com.kosmo.activity23;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnotherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_layout);
        Log.i(MainActivity.TAG,"AnotherActivity의 onCreate() invoked");
        findViewById(R.id.btnBackToTheMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onPause() -> onStop() -> onDestroy()
                //강제 종료가 아닌 정상종료 // 시용자가 호출한 것
                finish();//destroy까지 간다. save 호출 안된다.
            }
        });
    }/////////////onCreate


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(MainActivity.TAG,"AnotherActivity의 onStart() invoked");
    }//////////////onStart

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MainActivity.TAG,"AnotherActivity의 onResume() invoked");
    }////////////onResume


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MainActivity.TAG,"AnotherActivity의 onPause() invoked");
    }////////////onPause


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(MainActivity.TAG,"AnotherActivity의 onStop() invoked");
    }////////onStop


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(MainActivity.TAG,"AnotherActivity의 onRestart() invoked");
    }/////////////onRestart


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.TAG,"AnotherActivity의 onDestroy() invoked");
    }//////////////onDestroy


    //생명주기와 관련된 메서드
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(MainActivity.TAG,"AnotherActivity의 onSaveInstanceState() invoked");
    }/////////////////////onSaveInstanceState


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(MainActivity.TAG,"AnotherActivity의 onRestoreInstanceState() invoked");
    }/////////////////onRestoreInstanceState





}/////////////class
