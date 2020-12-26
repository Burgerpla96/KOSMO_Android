package com.kosmo.jsonparser32_04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private TextView kakaoText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kakaoText = findViewById(R.id.kakaoText);

        new AsyncFromKakao().execute("https://dapi.kakao.com/v2/vision/product/detect");

    }////onCreate




    private class AsyncFromKakao extends AsyncTask<String, Void, List<String>> {

        private AlertDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //progressbar 용 다이어 로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false)
                    .setTitle("jsonplaceholder.typicode.com")
                    .setIcon(android.R.drawable.ic_menu_gallery)
                    .setMessage("카카오 비전 api")
                    .setView(R.layout.progress_layout);
            progressDialog = builder.create();
            progressDialog.show();
        }


        @Override
        protected List<String> doInBackground(String... params) {
            List<String> productNames = new Vector<String>();
            StringBuffer buf = new StringBuffer();
            try {

                URL url = new URL(params[0]);
                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                //요청 헤더 설정
                conn.setRequestProperty("Authorization","KakaoAK dfee341ed844378fb40e307e703c7cf8");
                OutputStream out = conn.getOutputStream();
                out.write("image_url=https://t1.daumcdn.net/alvolo/_vision/openapi/r2/images/06.jpg".getBytes());
                out.close();

                //요청헤더 설정시:conn.setRequestProperty("요청헤더명","요청헤더값");
                //연결제한시간
                //conn.setConnectTimeout(3000);

                //getResponseCode()나 getInputStream() 호출해야 서버에 요청이 전달된다.
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //서버로 부터 받은 응답 내용  conn.getInputStream()
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String data;
                    while ((data = br.readLine()) != null) {
                        buf.append(data);
                    }
                    br.close();

                    JSONObject json = new JSONObject(buf.toString());
                    JSONObject result = json.getJSONObject("result");
                    JSONArray classes = result.getJSONArray("objects");
                    for(int i =0; i<classes.length(); i++){
                        JSONObject _class = classes.getJSONObject(i);
                        String productName = _class.getString("class");
                        productNames.add(productName);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return productNames;
        }


        @Override
        protected void onPostExecute(List<String> results) {
            kakaoText.setText("");
            for(String productName: results){
                kakaoText.append(productName+"\n");
            }
            progressDialog.dismiss();
        }
    }//////////////AsyncFromJSONPlaceholder


}///////////class