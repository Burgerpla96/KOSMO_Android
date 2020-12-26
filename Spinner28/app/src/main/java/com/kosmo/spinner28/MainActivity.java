package com.kosmo.spinner28;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //성별과 이동통신사 데이터
    private String[] genders = {"남자","여자","트랜스젠더"};
    private String[] telecomes = {"LG","SKT","KT"};
    //영화 데이터는 res 폴더에 values에 arrays.xml로
    private String[] movies;

    //로직을 실행하지 않기 위한 플래그 변수
    private boolean isAutoSelected;

    private Spinner genderSpinner;
    private Spinner telecomSpinner;
    private Spinner movieSpinner;
    private Button btnResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get widget
        genderSpinner = findViewById(R.id.genderSpinner);
        telecomSpinner = findViewById(R.id.telecomSpinner);
        movieSpinner = findViewById(R.id.movieSpinner);
        btnResult = findViewById(R.id.btnresult);

        //어뎁터 생성
        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,genders);
        ArrayAdapter telecomAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,telecomes);
        //방법 1. 자원 얻기 getResources() 사용
        movies = getResources().getStringArray(R.array.movies);
        ArrayAdapter movieAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,movies);

        //방법2. ArrayAdapter.createFromResource() 사용
        //ArrayAdapter movieAdapter =
         //       ArrayAdapter.createFromResource(this,R.array.movies,android.R.layout.simple_spinner_dropdown_item);

        //스피너와 어뎁터 연결) 스피너.setAdapter(어뎁터)
        genderSpinner.setAdapter(genderAdapter);
        telecomSpinner.setAdapter(telecomAdapter);
        movieSpinner.setAdapter(movieAdapter);

        //스피너에 리스너 부착 -setOnItemSelectedListener() 이용
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("com.kosmo.spinner", "onItemSelected invoked");
                if(!isAutoSelected){
                    isAutoSelected=true;
                    return;
                }

                //1. 배열 사용 // 비추천
                //Toast.makeText(parent.getContext(),movies[position]+" 선택",Toast.LENGTH_SHORT).show();
                //2. 어뎁터 뷰의 getItemAtPosition() method 사용 // 추천
                //Toast.makeText(parent.getContext(),movieSpinner.getItemAtPosition(position)+" 선택",Toast.LENGTH_SHORT).show();
                //3. 어뎁터의 getItem(position) 사용
                Toast.makeText(parent.getContext(),movieAdapter.getItem(position)+" 선택",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("com.kosmo.spinner", "onNothingSelected invoked");
            }
        });


        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 아이템 얻기
                String gender = genderSpinner.getSelectedItem().toString();
                //선택된 position으로 아이템 얻기 
                String telecom = telecomSpinner.getItemAtPosition(telecomSpinner.getSelectedItemPosition()).toString();
                //선택된 뷰로 텍스트 얻기
                String movie = ((TextView)movieSpinner.getSelectedView()).getText().toString();
                Toast.makeText(v.getContext(),String.format("성별: %s, 통신사: %s, 영화: %s",gender,telecom,movie),Toast.LENGTH_SHORT).show();
            }
        });



    }/////////////onCreate


}//////////class