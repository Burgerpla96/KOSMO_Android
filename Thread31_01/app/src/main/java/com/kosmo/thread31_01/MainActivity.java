package com.kosmo.thread31_01;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private TextView tvMainNumber;
    private TextView tvThreadNumber;
    private TextView tvRunnableNumber;

    //TextView 에 뿌려줄 숫자 저장용
    private int mainNumber,threadNumber,runnableNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기
        initView();
        /*
        작업 내용:
        버튼 클릭시 mainNumber는  1증가시키고
        threadNumber는 스레드내에서 시간 간격을 두고 1씩 증가 시켜
        UI스레드인 MainActivity
        의 텍스트뷰에 그 값을 출력한다
        작업 결과:
        버튼을 클릭할때마다  mainNumber는 1씩 증가하지만
        threadNumber와 runnableNumber는 지연된 시간만큼 갑자기 증가되어있음
        */

    }///////////////////////onCreate



    //Thread를 상속받은 작업 스레드
    private class WorkingThread extends Thread{
        @Override
        public void run() {
            while(true){
                threadNumber++;
                try{
                    sleep(10);
                    //작업스레드안에서 위젯의 UI변경시 ANR발생(버튼 연속해서 계속누르기)
                    //기본 스레드가 아닌 스레드에서 UI 개체를 수정하거나 참조하려고 하면 예외,
                    // 자동 실패, 비정상 종료, 기타 정의되지 않은 오동작이 그 결과로 발생할 수 있다
                    //tvThreadNumber.setText(String.valueOf(threadNumber));
                }
                catch (InterruptedException e){e.printStackTrace();}
            }
        }
    }/////////////WorkingThread




    public void thread(View view) {
        mainNumber++;
        tvMainNumber.setText(String.valueOf(mainNumber));
        //작업 스레드 생성해 스레드 시작
        WorkingThread thread = new WorkingThread();
        thread.setDaemon(true);
        thread.start();
        tvThreadNumber.setText(String.valueOf(threadNumber));

    }////////////////thread



    //Runnable 상속받은 스레드
    private class WorkingRunnable implements Runnable{
        @Override
        public void run() {
            while(true){
                runnableNumber++;
                try{
                    Thread.sleep(10);
                    //tvRunnableNumber.setText(String.valueOf(runnableNumber));
                }
                catch (InterruptedException e){e.printStackTrace();}
            }
        }
    }///////////////WorkingRunnable



    public void runnable(View view) {
        mainNumber++;
        tvMainNumber.setText(String.valueOf(mainNumber));
        //작업 스레드 생성해 스레드 시작
        WorkingRunnable runnable = new WorkingRunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        tvRunnableNumber.setText(String.valueOf(runnableNumber));
    }////////////////runnable



    private void initView() {
        tvMainNumber = (TextView) findViewById(R.id.tvMainNumber);
        tvThreadNumber = (TextView) findViewById(R.id.tvThreadNumber);
        tvRunnableNumber = (TextView) findViewById(R.id.tvRunnableNumber);

    }////////////////initView



}////////////////////class