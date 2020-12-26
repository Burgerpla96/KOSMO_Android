package com.kosmo.jsonparser32_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

public class MainActivity extends ListActivity {

    public static Context APP_CONTEXT;
    private ListView listView;
    private JSONPlaceHolderAdapter adapter;
    private List<JSONPlaceHolderItem> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);//listView만 표시할 것, 다른 위젯 불필요
        APP_CONTEXT = getApplicationContext();
        //데이터로 사용할 컬렉션 생성
        items = new Vector<JSONPlaceHolderItem>();
        //djepqxj todtjd
        adapter = new JSONPlaceHolderAdapter(this, items);
        //리스트 뷰 얻기
        listView = getListView();
        //listView 에 어뎁터 연결
        setListAdapter(adapter);

        new AsyncFromJSONPlaceholder().execute("https://jsonplaceholder.typicode.com/photos");
    }////////onCreate


    private class AsyncFromJSONPlaceholder extends AsyncTask<String, Void, Void> {

        private AlertDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //progressbar 용 다이어 로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false)
                    .setTitle("jsonplaceholder.typicode.com")
                    .setIcon(android.R.drawable.ic_menu_gallery)
                    .setMessage("원격의 데이타 수신중입니다.")
                    .setView(R.layout.progress_layout);
            progressDialog = builder.create();
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(String... params) {
            StringBuffer buf = new StringBuffer();
            try {

                URL url = new URL(params[0]);
                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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
                    //파싱해서 items에 저장
                    JSONArray array = new JSONArray(buf.toString());
                    for(int i=0;i<array.length();i++){
                        JSONObject json = array.getJSONObject(i);
                        String id = json.getString("id");
                        String title = json.getString("title");
                        String thumbnailUrl = json.getString("thumbnailUrl");
                        JSONPlaceHolderItem item = new JSONPlaceHolderItem(id,title,thumbnailUrl);
                        items.add(item);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }//////////////AsyncFromJSONPlaceholder


}/////////////////////////class