package com.kosmo.autocomplete13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    //android.R로 시작하는 리소스는
    // android sdk 설치 디렉토리 \platforms\플랫폼 버젼\data\res에 있다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1) 위젯에 뿌려줄 데이터 준비(자동 완성 텍스트)
        String[] items = {"apple","application","arirang",
                "baby","baseball","basic","antena","call"};

        //위젯 얻기
        AutoCompleteTextView single = findViewById(R.id.single_auto);
        MultiAutoCompleteTextView multi = findViewById(R.id.multi_auto);
        //3) 어뎁터 객체 생성: 위젯과 데이터를 연결 시켜주고 데이터가 표시되는 모양
        //(레이아웃) 까지
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,items);
        //어뎁터와 위젯 연결
        single.setAdapter(adapter);
        //multi.setAdapter(adapter);//[x]적용안된다.
        //자동완성기능을 위한 MultiAutoCompleteTextView 설정 먼저 해야한다.
        //CommaTokenizer를 multi에 먼저 설정
        MultiAutoCompleteTextView.CommaTokenizer tokenizer =
                new MultiAutoCompleteTextView.CommaTokenizer();
        //, 로 연결이 자동으로 된다.
        multi.setTokenizer(tokenizer);
        multi.setAdapter(adapter);



    }
}