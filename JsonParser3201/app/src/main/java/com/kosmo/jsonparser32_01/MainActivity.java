package com.kosmo.jsonparser32_01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tvname;
    private TextView tvage;
    private TextView tvhobby;
    private TextView tvlogin;

    //파싱작업을 스레드로 처리
    private ParsingJsonTask jsonTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //AsyncTask는 딱 한 번만 execute()할 수 있다. 또 쓰면 오류
        jsonTask = new ParsingJsonTask();
    }////////////onCreate

    private class ParsingJsonTask extends AsyncTask<Void,Void, Map<String,String>>{
        //doInBackground()메서드는 UI변경 불가
        //JSON 데이타를 파싱해서 파싱한 결과를 Map에 담아서 반환
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> map = new HashMap<String,String>();
            try {
                //1) raw 폴더에 있는 데이터 가져오기
                InputStream is = getResources().openRawResource(R.raw.json);
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String data;
                StringBuffer buf = new StringBuffer();
                while((data=br.readLine())!=null){
                    buf.append(data);
                }
                br.close();
                //2]읽어온 문자열 데이타를 JSON으로 파싱하기
                //new JSONObject("JSON형식의 문자열")
                JSONObject json = new JSONObject(buf.toString());
                //파싱해서 map에 저장
                map.put("name",json.getString("name"));
                map.put("age",json.getString("age"));
                map.put("hobbys",json.getJSONArray("hobbys").toString());
                JSONObject login = json.getJSONObject("login");
                map.put("user",login.getString("user"));
                map.put("pass",login.getString("pass"));


            } catch (Exception e){e.printStackTrace();}
            return map;
        }//////////doInBackground

        @Override
        protected void onPostExecute(Map<String, String> results) {
            //widget에 데이터 설정
            tvage.setText(results.get("age"));
            tvhobby.setText(results.get("hobbys"));
            tvlogin.setText(String.format("아이디: %s, 비번: %s",results.get("user"),results.get("pass")));
            tvname.setText(results.get("name"));
            Log.i("kosmo","AsyncTask execute()");

        }////////////////onPostExecute

    }////////////////////////ParsingJsonTask


    public void parsing(View view) {
        //아래처럼 코딩시 버튼을 두번 클릭시 에러
        //즉 스레드 작업 완료(파싱 완료후 텍스트뷰에 표시)
        //후 또 버튼을 누르면 execute()할수 없다(딱 한번만 실행되니까)
        //jsonTask.execute();

        //getStatus()메소드로 AsyncTask의 상태를 얻어와
        //PENDING(실행전)상태일때 execute()호출
        //RUNNING(실행중)
        //FINISHED(종료)
        if(jsonTask.getStatus()==AsyncTask.Status.PENDING){
            jsonTask.execute();
        }
        else{
            jsonTask = new ParsingJsonTask();
            jsonTask.execute();
        }

    }/////////////////parsing


    private void initView() {
        tvname = (TextView) findViewById(R.id.tvname);
        tvage = (TextView) findViewById(R.id.tvage);
        tvhobby = (TextView) findViewById(R.id.tvhobby);
        tvlogin = (TextView) findViewById(R.id.tvlogin);
    }
}//////////////////class