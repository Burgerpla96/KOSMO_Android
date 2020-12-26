package com.kosmo.jsonparser32_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

public class MainActivity extends ListActivity {

    //listView 에 뿌려줄 컬렉션 데이터
    private List<JSONItem> items;
    //custom adapter
    private JSONAdapter adapter;
    //listView를 담을 변수
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //data 용 컬렉션 객체 생성
        items = new Vector<JSONItem>();
        //어뎁터 생성
        adapter = new JSONAdapter(this,items,R.layout.item_layout);
        //ListView 얻기
        listView = getListView();
        //리스트뷰와 어뎁터 연결
        setListAdapter(adapter);

    }///////////onCreate

    //JSON data 읽어서 파싱해 items에 저장후 어뎁터에세 데이타 변경 통지
    //원격의 데이타 사용시 INTERNET 권한 추가 및 스레드로 구성
    private  class JSONParsingTask extends AsyncTask<InputStream,Void,Void>{
        private AlertDialog progressDialog;
        //파싱하는 작업동안 보여줄 프로그래스 다이얼 로그 띄우기
        @Override
        protected void onPreExecute() {
            //프로그래스바 용 다이어 로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            progressDialog = builder.setCancelable(false)
                    .setView(R.layout.progress_layout)
                    .create();
            progressDialog.show();

        }


        @Override
        protected Void doInBackground(InputStream... params) {
            StringBuffer buf = new StringBuffer();
            try {
                //파일 IO 사용해서 원격이나 URL이나 RAW폴어데 있는 파일에서 JSON형식의 데이터 읽기
                BufferedReader br = new BufferedReader(new InputStreamReader(params[0], "UTF-8"));

                String data;
                while((data=br.readLine())!=null){
                    buf.append(data);
                }
                br.close();
                //읽을 데이터를 파싱해서 컬렉션에 저장
                JSONObject json = new JSONObject(buf.toString());
                JSONArray array = json.getJSONArray("members");
                for(int i=0; i<array.length();i++){
                    JSONObject item = array.getJSONObject(i);
                    String name = item.getString("name");
                    String age = item.getString("age");
                    String hobbys = item.getJSONArray("hobbys").toString();
                    JSONObject login = item.getJSONObject("login");
                    String user = login.getString("user");
                    String pass = login.getString("pass");
                    //파싱한 데이터를 컬렉션에 저장
                    JSONItem jsonItem = new JSONItem(name,age,hobbys,String.format("아이디:%s, 비번:%s",user,pass));
                    items.add(jsonItem);

                }

            } catch (Exception e){e.printStackTrace();}
            SystemClock.sleep(1000);
            return null;
        }////////////

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }

    }/////////////////JSONParsingTask



    public void parsing(View view){
        items.clear();
        new JSONParsingTask().execute(getResources().openRawResource(R.raw.json));

    }///////////parsing




}///////////class