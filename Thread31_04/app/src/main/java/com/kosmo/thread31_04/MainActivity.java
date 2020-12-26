package com.kosmo.thread31_04;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText id;
    private EditText pwd;
    private Button btnPlain;
    private Button btnJson;
    private Button btnJsonArray;
    private TextView textResult;

    private AlertDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //progressbar 용 다이어 로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setView(R.layout.progress_layout);
        progressDialog = builder.create();
        //버튼에 리스너 부착
        btnJson.setOnClickListener(handler);
        btnJsonArray.setOnClickListener(handler);
        btnPlain.setOnClickListener(handler);

    }////////////onCreate

    //버튼에 이벤트 처리
    private Button clickButton;
    private View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //onPostExecute 메서드에서 버튼을 구분하기 위한 용도
            clickButton = (Button)v;
            if(v == btnPlain){//서버로 부터 일반 텍스트로 응답 받기: PLANE TEXT
                new AsyncFromServer().execute("http://192.168.0.25:8080/springapp/Ajax/AjaxText.do",
                        "id="+id.getText().toString().trim(),
                        "pwd="+pwd.getText().toString().trim());
            }
            else if(v==btnJson){
                new AsyncFromServer().execute("http://192.168.0.25:8080/springapp/Ajax/AjaxJson.do",
                        "id="+id.getText().toString().trim(),
                        "pwd="+pwd.getText().toString().trim());
            }
            else{
                new AsyncFromServer().execute("http://192.168.0.25:8080/springapp/Ajax/AjaxJsonArray.do");
            }
        }
    };

    //실행전 manifest file에 반드시 인터넷 권한 추가
    //<uses-permission android:name="android.permission.INTERNET"/>
    //android:usesCleartextTraffic="true"를 했다.

    private class AsyncFromServer extends AsyncTask<String, Void, String>{
        //doInBackground 전에 호출
        @Override
        protected void onPreExecute() {
            //선행 작업: 진행 대화상자 띄우기
            if(!progressDialog.isShowing()) progressDialog.show();
        }

        //서버에 데이터 요청 및 서버로 부터 응답 받고 받은 데이터 반환
        @Override
        protected String doInBackground(String... params) {
            StringBuffer serverData = new StringBuffer();
            //서버에 접속하기위한 URL
            URL serverUrl;

            //서버에 get방식으로 요청
            try {
                if (params.length == 1) {//인자가 하나 넘어오는 경우
                    serverUrl = new URL(params[0]);
                }
                else{//인자가 3개
                    //쿼리 스트링으로 연결
                    serverUrl = new URL(String.format("%s?%s&%s",params[0],params[1],params[2]));
                }
                //서버연결
                HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();

                //요청헤더 설정시:conn.setRequestProperty("요청헤더명","요청헤더값");
                //연결제한시간
                //conn.setConnectTimeout(3000);

                //getResponseCode()나 getInputStream() 호출해야 서버에 요청이 전달된다.
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    //서버로 부터 받은 응답 내용  conn.getInputStream()
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String data;
                    while((data=br.readLine())!= null){
                        serverData.append(data);
                    }
                    br.close();
                }
            }catch (Exception e){e.printStackTrace();}



            //서버에 post방식으로 요청
            /*
            try {
                serverUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
                //연결 설정 하기
                //1.POST방식으로 통신 설정:POST방식으로 설정시
                // OutPutStream으로 파라미터를 서버에 전달

                //디폴트는 get방식
                conn.setRequestMethod("POST");
                //setRequestMethod("GET")으로 설정하더라도
                //아래 코드(setDoInput(true))가 추가되면 POST방식으로 변경된다.
                conn.setDoInput(true);
                //연결제한 시간
                conn.setConnectTimeout(3000);
                // 요청 파라미터 출력(서버로 보내는 출력)
                // - 파라미터는 쿼리 문자열의 형식으로 지정
                // - 파라미터의 값으로 한국어 등을 송신하는 경우는 URL 인코딩을 해야 함.
                // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
                if(params.length != 1){//파라미터 전달
                    OutputStream out = conn.getOutputStream();//출력을 위한 빨대
                    //요청바디에 데이터 전달
                    out.write(params[1].getBytes());
                    out.write("&".getBytes());
                    out.write(params[2].getBytes());
                    out.close();

                }
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    //서버로 부터 받은 응답 내용  conn.getInputStream()
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String data;
                    while((data=br.readLine())!= null){
                    }
                    br.close();
                }
            } catch (Exception e){e.printStackTrace();}
            */


            SystemClock.sleep(1000);//dialog 창을 보여주기 위함
            return serverData.toString();
        }

        @Override
        protected void onPostExecute(String results) {
            //위젯변경
            //기존값 클리어
            textResult.setText("");
            Log.i("com.kosmo.thread","서버로 부터 받은 데이터: "+results);
            //서버로부터 받은 데이터 텍스트 뷰에 출력
            if(clickButton == btnPlain){//일반 텍스트로 받을때
                textResult.setText(results);
            }
            else if(clickButton == btnJson){//일반 텍스트로 받을때
                //안드로이드는 json 이 내장되어있다.
                try {
                    JSONObject json = new JSONObject(results);
                    String isLogin = json.getString("isLogin");
                    textResult.setText(isLogin);

                } catch (Exception e){e.printStackTrace();}

            }
            else{//JSON 배열로 받을때
                try{
                    JSONArray array = new JSONArray(results);
                    for(int i=0; i<array.length();i++){
                        JSONObject json = array.getJSONObject(i);
                        String name = json.getString("name");
                        String title = json.getString("title");
                        String postDate = json.getString("postDate");
                        textResult.append(String.format("이름: %s, 게시일: %s%n제목: %s%n",name,postDate,title));

                    }
                }catch (Exception e){e.printStackTrace();}


            }

            progressDialog.dismiss();
        }

    }//////////////AsyncFromServer


    private void initView() {
        id = (EditText) findViewById(R.id.id);
        pwd = (EditText) findViewById(R.id.pwd);
        btnPlain = (Button) findViewById(R.id.btn_plain);
        btnJson = (Button) findViewById(R.id.btn_json);
        btnJsonArray = (Button) findViewById(R.id.btn_json_array);
        textResult = (TextView) findViewById(R.id.text_result);
    }
}////////////////////class