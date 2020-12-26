package com.kosmo.thread31_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mainNumber,threadNumber;
    private TextView textMain,textThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기
        textMain = findViewById(R.id.tvMainNumber);
        textThread = findViewById(R.id.tvThreadNumber);
        /*
        작업 내용:
        버튼 클릭시 mainNumber는  1증가시키고
        threadNumber는 스레드내에서 시간 간격을 두고 1씩 증가 시켜
        UI스레드인 MainActivity
        의 텍스트뷰에 그 값을 출력한다
        작업 결과:
        버튼을 클릭할때마다  mainNumber는 1씩 증가하고
        threadNumber도 0.5초마다 1씩 증가한다.
        */
    }////////////onCreate



    //쓰레드 클래스(작업 스레드): AsyncTask 클래스 사용시 불필요
    private class WorkingThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    threadNumber++;
                    sleep(500);
                    //SystemClock.sleep(500);//try-catch 불필요
                    //=====================================
                    //[방법1]핸들러 객체의 sendXXX()
                    //=====================================
                    // sendXXX()메소드 호출로 메인스레드와 통신

                    //handler.sendEmptyMessage(100);

                    //2)sendMessage(Message msg) 사용
                    //계속 객체 생성해서 비권장.
                    //Message msg = new Message();//비권장

                    //Andoid os의 Message 풀에서 가져다 쓰는 것
                    /*
                    Message msg = Message.obtain();//재사용 측면에서 권장
                    msg.what = 100;
                    msg.arg1 =200;
                    msg.arg2 =300;
                    msg.obj = null;
                    handler.sendMessage(msg);
                    */


                    //==================================
                    //[방법2]핸들러 객체의 post(Runnable)사용
                    //==================================
                    /*
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //메인 스레드 부착된 위젯의 UI 변경
                            textThread.setText(String.valueOf(threadNumber));
                        }
                    });
                    */


                    //=================================
                    //[방법3]핸들러 객체 미 사용
                    //==================================
                    /*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textThread.setText(String.valueOf(threadNumber));
                        }
                    });
                    */



                    //==================================
                    //[방법4]핸들러 객체 역시 미 사용
                    //==================================
                    /*
                    textThread.post(new Runnable() {
                        @Override
                        public void run() {
                            textThread.setText(String.valueOf(threadNumber));

                        }
                    });
                     */





                }
            }catch (InterruptedException e){e.printStackTrace();}
        }
    }////////////////////WorkingThread

    private  WorkingThread workingThread;

    //방법 1)
     /*
    1.메인 스레드(UI스레드)에서 Handeler객체 생성 및 Handeler의 handleMessage()오버라이딩
      handleMessage()안에서 위젯의 UI변경
    2.스레드 클래스(작업스레드)의 run()메소드안에서
     Handler객체의 sendXXXXX()메소드 호출
     */
    /*
    Handler handler = new Handler(){//handler가 작업 스레드의 msg 분석해서 ui 변경
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==100){
                //메인 스레드에 부착된 위젯의 UI 변경
                textThread.setText(String.valueOf(threadNumber));
            }
        }
    };
    */
    public void send(View view){
        if(workingThread == null || !workingThread.isAlive()){
            workingThread = new WorkingThread();
            workingThread.setDaemon(true);
            workingThread.start();
        }
        mainNumber++;
        textMain.setText(String.valueOf(mainNumber));

    }/////////////////////send



    //방법 2)
     /*
        1.메인스레드에서 Handler객체만 생성-handleMessage() 오버라이딩 안함
        2.스레드 클래스의 run()메소드에서 Handler객체의 post()메소드호출
          post()안에서 위젯의 UI변경
     */
    //Handler handler = new Handler();

    public void post(View view){
        send(view);
    }/////////////////////post



    //[방법3]
     /*
    1.Handler객체 불필요
    2.스레드 클래스(작업스레드)의 run()메소드안에서
      runOnUiThread()메소드 호출
      runOnUiThread()메소드안에서 위젯의 UI변경
     */
    public void runonui(View view){
        send(view);
    }/////////////////////runonui



    //[방법4]
     /*
        1.Handler객체 불필요
        2.스레드 클래스(작업스레드)의 run()메소드안에서
           위젯.post()메소드 호출
          위젯.post()메소드안에서 위젯의 UI변경
     */
    public void widgetpost(View view){
        send(view);
    }/////////////////////widgetpost




    //[방법5]
     /*
        1.스레드 클래스 불필요 . Handler객체도 불필요
        2.AsyncTask상속받아서 주요 메소드 오버라딩한 후
          실행시킬때는 AsyncTask객체.execute(파라티터들)
          실행시킬때 파라미터들이 doInBackground(varArgs )의 매개변수로 전달됨.
        3.AsyncTask로 실행한 스레드를 중지할때는 AsyncTask객체.cancel(true)
     */
    //doInBackground()를 오버라이딩해야만 한다.
    private class MyAsyncTask extends AsyncTask<Void,Void,Void> {//Params, Progress, Result

        //doInBackground() 호출전에 선행작업이 있을 때, 호출
        @Override
        protected void onPreExecute() {
            Log.i("com.kosmo.thread","onPreExecute() invoked");
        }

        //doInBackground() 작업이 끝난 후 호출
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("com.kosmo.thread","onPostExecute() invoked");
        }

        //doInBackground() 에서  publishProgress();를 호출 할때마다 자동으로 호출
        @Override
        protected void onProgressUpdate(Void... values) {
            textThread.setText(String.valueOf(threadNumber));
            Log.i("com.kosmo.thread","onProgressUpdate() invoked");
        }

        //스레드 중지시 호출
        @Override
        protected void onCancelled() {
            Log.i("com.kosmo.thread","onCancelled() invoked");
        }

        //필수 오버라이딩  - 작업 스레드의 run() 역할
        @Override
        protected Void doInBackground(Void... voids) {
            //스레드의 run() 메서드에서 수행할 코드 작성 - 위젯의 UI변경시 ANR 발생
            Log.i("com.kosmo.thread","doInBackground() invoked");
            while (true){
                threadNumber++;
                SystemClock.sleep(500);
                //아래 메서드 호출해야 onProgressUpdate()가 호출된다.
                publishProgress();
                //cancel() 호출시 스레드 종료
                if(isCancelled()){//thread 중지시 true 반환
                    break;
                }
            }
            return null;
        }/////////////////doInBackground

    }////////////////MyAsyncTask


    private MyAsyncTask myAsyncTask;

    public void asynctask(View view){
        mainNumber++;
        textMain.setText(String.valueOf(mainNumber));
        if(myAsyncTask == null){
            myAsyncTask = new MyAsyncTask();
            //스레드 실행
            myAsyncTask.execute();
        }
    }/////////////////////asynctask




    public void stop(View view){
        //Thread 상속받은 스레드 클래스를 직접 만들어서 사용할 때
        if(workingThread != null && workingThread.isAlive()){
            workingThread.interrupt();
        }
        //AsyncTask를 사용할 때
        if(myAsyncTask != null) {
            myAsyncTask.cancel(true);
        }
    }/////////////////////stop





}/////////////////class