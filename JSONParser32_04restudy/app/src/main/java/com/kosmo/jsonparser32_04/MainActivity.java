package com.kosmo.jsonparser32_04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
//1.ListActivity상속
public class MainActivity extends ListActivity {


    private ListView listView;
    List<String> items = new Vector<String>();
    private ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2.어댑터 생성
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice,items);
        //3.리스트뷰와 연결
        setListAdapter(adapter);
        //4.리스트뷰 얻기
        listView = getListView();
        //5.모드 설정
        //위에서 simple_list_item_single_choice 설정해서 mode설정해야한다.
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //6.요청 스레드 실행
        new AsyncFromKakao().execute("https://dapi.kakao.com/v2/vision/product/detect");

    }//////////onCreate

    private class AsyncFromKakao extends AsyncTask<String,Void, List<String>> {
        private AlertDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //프로그래스바용 다이얼로그 생성]
            //빌더 생성 및 다이얼로그창 설정
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false)
                    .setTitle("카카오 비전 API")
                    .setIcon(android.R.drawable.ic_menu_gallery)
                    .setMessage("원격의 데이타 수신중입니다")
                    .setView(R.layout.progress_layout);
            //빌더로 다이얼로그창 생성
            progressDialog = builder.create();
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... params) {

            StringBuffer buf = new StringBuffer();
            try {

                URL url= new URL(params[0]);
                //서버연결
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                //요청헤더 설정
                conn.setRequestProperty("Authorization","KakaoAK 99f305d54be4fa34e307f285a156d804");
                //파라미터 설정
                OutputStream out=conn.getOutputStream();
                out.write("image_url=https://t1.daumcdn.net/alvolo/_vision/openapi/r2/images/06.jpg".getBytes());
                out.close();
                //요청헤더 설정시:conn.setRequestProperty("요청헤더명","요청헤더값");
                //연결제한시간
                //conn.setConnectTimeout(3000);
                //※getResponseCode() 나 getInputStream()호출해야 서버에 요청이 전달됨
                if(conn.getResponseCode() ==HttpURLConnection.HTTP_OK){
                    //서버로부터 받는 응답 내용:conn.getInputStream()
                    InputStream is=conn.getInputStream();
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    String data;
                    while((data=br.readLine())!=null){
                        buf.append(data);
                    }
                    br.close();

                    JSONObject json = new JSONObject(buf.toString());
                    JSONObject result=json.getJSONObject("result");
                    JSONArray classes=result.getJSONArray("objects");
                    for(int i=0;i < classes.length();i++){
                        JSONObject _class=classes.getJSONObject(i);
                        String productName=_class.getString("class");
                        items.add(productName);
                    }
                }

            }
            catch(Exception e){e.printStackTrace();}
            return items;
        }

        @Override
        protected void onPostExecute(List<String> results) {
            //어댑터에 데이타 변경 통지
            adapter.notifyDataSetChanged();
            //대화상자 닫기
            progressDialog.dismiss();
        }
    }///////////////
    //아이템 클릭 이벤트처리용 콜백메소드 오버라이딩
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String searchProduct=listView.getItemAtPosition(position).toString();
        Toast.makeText(this,"선택한 상품 : "+searchProduct,Toast.LENGTH_SHORT).show();
        //네이버 쇼핑검색 api 호출위한
        //Activity 전환
        Intent intent = new Intent(this,ShoppingActivity.class);
        intent.putExtra("searchProduct",searchProduct);
        startActivity(intent);
        //activity 전환으로 화면 뿌리기
        //  https://developers.naver.com/docs/search/shopping/ 에서 이용

    }////////////








}