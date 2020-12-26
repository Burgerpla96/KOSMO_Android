package com.kosmo.chronometer11_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//implements View.OnClickListener 해서 this로 click이벤트 처리 가능하게 하였다.
    //익명 클래스로 구현해도 된다.

    private Chronometer chronometer;
    private Button btnTimeSet, btnDateSet, btnStart, btnStop;
    private TextView textView;
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //레이아웃 전개- inflate
        setContentView(R.layout.activity_main);
        //위젯 얻기
        chronometer = findViewById(R.id.timer_chronometer);
        btnDateSet = findViewById(R.id.dateset_button);
        btnTimeSet = findViewById(R.id.timeset_button);
        btnStart = findViewById(R.id.start_button);
        btnStop = findViewById(R.id.stop_button);
        textView = findViewById(R.id.textview);

        //위젯에 리스너 부착
        btnDateSet.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnTimeSet.setOnClickListener(this);

        //크로노미터는 분:초로 표시되어, 시:분:초로 표시하게 코드 추가
        //익명 클래스로 이벤트 핸들러 구현
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            //초가 변할때마다 자동으로 호출되는 콜백 메서드
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //크로노 미터를 시작한 이후 흘러온 시간
                long elapsedTime = SystemClock.elapsedRealtime()-chronometer.getBase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String timeString = dateFormat.format(new Date(elapsedTime));
                chronometer.setText(timeString);

                //텍스트 뷰에 크로노미터의 텍스트 설정
                textView.setText(chronometer.getText());

                //os가 부팅된 이후 흘러온 시간
                Log.i("com.kosmo.chronometer",
                        "elapsedRealtime() : "
                                +dateFormat.format(new Date(SystemClock.elapsedRealtime())));
                //setBase()로 설정한 시간
                Log.i("com.kosmo.chronometer",
                        "getBase() : "
                                +dateFormat.format(new Date(chronometer.getBase())));


            }
        });

    }



    @Override
    public void onClick(View v) {
        if(v==btnTimeSet){//주소로 비교
            //부팅후 틀릭시 까지 흘러온 시간을 기준 시간으로 설정
            chronometer.setBase(SystemClock.elapsedRealtime());
            //Chronometer 시작
            chronometer.start();
            new TimePickerDialog(v.getContext(), android.R.style.Theme_DeviceDefault_Dialog,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //chronometer 중지
                            chronometer.stop();
                            textView.setText(String.format("%s시 %s분",hourOfDay,minute));
                        }
                    },cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),true).show();
            //종료버튼만 있는 다이어로그 창

        }
        else if(v.getId() == R.id.dateset_button){
            //부팅후 틀릭시 까지 흘러온 시간을 기준 시간으로 설정
            chronometer.setBase(SystemClock.elapsedRealtime());
            //Chronometer 시작
            chronometer.start();
            new DatePickerDialog(v.getContext(),
                    android.R.style.Theme_DeviceDefault_Light_Dialog_Alert,
                    new DatePickerDialog.OnDateSetListener() {
                        //날짜 설정 완료시
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //chronometer 중지
                            chronometer.stop();
                            textView.setText(String.format("%s년 %s월 %s일",year,month+1,dayOfMonth));
                        }
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DATE)).show();//show를 해야 창에 보인다.
        }////////else if
        else if(v == btnStart){
            //1) 시스템 시간으로 시간 설정
            chronometer.setBase(SystemClock.elapsedRealtime());
            //Chronometer 시작
            chronometer.start();
        }
        else{
            //Chronometer 중지
            chronometer.stop();

        }



    }//////onCreate




}