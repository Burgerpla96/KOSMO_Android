package com.kosmo.thread31_03;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private TextView seektext;
    private SeekBar seekbar;
    private TextView asynctext1;
    private SeekBar asyncseekbar1;
    private TextView asynctext2;
    private SeekBar asyncseekbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get widget
        initView();
        //seekbar에 이벤트 달기
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seektext.setText("seekbar 진행율: "+(int)((double)progress/seekBar.getMax()*100)+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


    }///////onCreate



    public void increase(View view) {
        if(progressbar.getProgress() < progressbar.getMax()){
            //방법 1] setProgress()
            //progressbar.setProgress(progressbar.getProgress()+10);
            //방법2] incrementProgressBy()
            progressbar.incrementProgressBy(10);
        }

    }/////////////////increase



    public void decrease(View view) {
        if(progressbar.getProgress() > progressbar.getMin()){
            //방법 1] setProgress()
            //progressbar.setProgress(progressbar.getProgress()-10);
            //방법2] incrementProgressBy()
            progressbar.incrementProgressBy(-10);
        }
    }/////////////////decrease


    //[AsyncTask 연습]
    //AsyncTask를 상속받은 클래스 정의]
    /*
    AsyncTask<Param,Progress,Result>
    Param: execute()메소드나 doInBackground()메소드의 인자 타입
    Progress:onProgressUpdate()메소드의 인자 타입
    Result:doInBackround()의 반환타입.onPostExcecute()의 인자 타입이 됨
    */
    //doInBackground() 일 끝난 후,  onPostExecute() 호출
    private class MyAsyncTask extends AsyncTask<String,Void,String>{
        //seekbar 의 진행정도를 랜덤하게 설정하기 위한 변수
        private Random random = new Random();

        @Override
        protected String doInBackground(String... strings) {
            //위젯 UI변경 불가 - 작업 스레드니까
            //execute(String... params) 호출시 전달된 문자열 출력
            for(String param:strings) Log.i("com.kosmo.thread",param);
            while(true){
                publishProgress();//호출시 onProgressUpdate() 호출된다.
                SystemClock.sleep(100);
                if(isCancelled() || asyncseekbar1.getProgress() == asyncseekbar1.getMax() &&
                        asyncseekbar2.getProgress() == asyncseekbar2.getMax()) break;

            }
            return Arrays.toString(strings);
        }//////////////doInBackground

        @Override
        protected void onProgressUpdate(Void... values) {
            //여기서는 UI 변경이 가능하다.
            int bar1 = random.nextInt(10)+1;//1~10
            asyncseekbar1.setProgress(asyncseekbar1.getProgress()+bar1);

            int bar2 = random.nextInt(10)+1;//1~10
            asyncseekbar2.setProgress(asyncseekbar2.getProgress()+bar2);

            //TextView에 진행률 표시
            asynctext1.setText("1번 진행율"+asyncseekbar1.getProgress());
            asynctext2.setText("2번 진행율"+asyncseekbar2.getProgress());

        }///////onProgressUpdate

        @Override
        protected void onPostExecute(String result) {
            Log.i("com.kosmo.thread", "doInBackground()에서 반환한 값: " + result);
        }///////////////onPostExecute

        //doInBackground()에서 cancel() 호출시 호출된다.
        @Override
        protected void onCancelled() {
            myAsyncTask = null;
        }///////////onCancelled


    }////////////MyAsyncTask
    private MyAsyncTask myAsyncTask;


    public void start(View view) {
        myAsyncTask = new MyAsyncTask();
        if(asyncseekbar1.getProgress() == asyncseekbar1.getMax() &&
                asyncseekbar2.getProgress() == asyncseekbar2.getMax()){
            asyncseekbar1.setProgress(0);
            asyncseekbar2.setProgress(0);
        }
        myAsyncTask.execute("doInBackground()로 전달","두번째 문자열","세번쨰 문자열");

    }/////////////////start


    public void stop(View view) {
        //onCanclled(String s) 혹은 onCancelled() 호출된다.
        if(myAsyncTask != null) myAsyncTask.cancel(true);
    }/////////////////stop




    private void initView() {
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        seektext = (TextView) findViewById(R.id.seektext);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        asynctext1 = (TextView) findViewById(R.id.asynctext1);
        asyncseekbar1 = (SeekBar) findViewById(R.id.asyncseekbar1);
        asynctext2 = (TextView) findViewById(R.id.asynctext2);
        asyncseekbar2 = (SeekBar) findViewById(R.id.asyncseekbar2);
    }
}////////////////////class