package com.kosmo.jsonparser32_04;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

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

public class ShoppingActivity extends ListActivity {

    //네이버 쇼핑 api 호출
    private ListView listView;
    private ShoppingAdapter adapter;
    private List<ShoppingItem> items = new Vector<ShoppingItem>();
    private String searchProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1. intent 가 전달한 상품명 가져오기
        searchProduct = getIntent().getStringExtra("searchProduct");
        //2. 어댑터 생성
        adapter = new ShoppingAdapter(this,items);
        //3. 리스트뷰와 연결
        setListAdapter(adapter);
        //4. 리스트뷰 얻기
        listView = getListView();

        //6. 요청 스레드 실행
        new AsyncFromNaver().execute("https://openapi.naver.com/v1/search/shop.json");

    }

    private class  AsyncFromNaver extends AsyncTask<String,Void,List<ShoppingItem>>{
        private AlertDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //프로그래스바용 다이얼로그 생성]
            //빌더 생성 및 다이얼로그창 설정
            AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
            builder.setCancelable(false)
                    .setTitle("네이버 쇼핑 API")
                    .setIcon(android.R.drawable.ic_menu_gallery)
                    .setMessage("원격의 데이타 수신중입니다")
                    .setView(R.layout.progress_layout);
            //빌더로 다이얼로그창 생성
            progressDialog = builder.create();
            progressDialog.show();
        }

        @Override
        protected List<ShoppingItem> doInBackground(String... params) {

            StringBuffer buf = new StringBuffer();
            try {

                URL url= new URL(params[0]+"?query="+searchProduct+"&display=50");
                //서버연결
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                //요청헤더 설정
                conn.setRequestProperty("X-Naver-Client-Id","QJqEdHMV3CNST4B00AHF");
                conn.setRequestProperty("X-Naver-Client-Secret","dpq87aXZZU");
                //파라미터 설정

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
                    JSONArray shoppingItems=json.getJSONArray("items");
                    for(int i=0;i < shoppingItems.length();i++){
                        JSONObject shoppingItem = shoppingItems.getJSONObject(i);
                        String title = shoppingItem.getString("title");
                        //<b> 검색어 </b> 없애기
                        title = title.replace("<b>","").replace("</b>","");
                        String image = shoppingItem.getString("image");
                        String hprice = shoppingItem.getString("hprice");
                        String lprice = shoppingItem.getString("lprice");
                        String maker = shoppingItem.getString("maker");
                        ShoppingItem shopping = new ShoppingItem(title,image,hprice,lprice,maker);
                        items.add(shopping);
                    }
                }

            }
            catch(Exception e){e.printStackTrace();}
            return items;
        }//////////////////////

        @Override
        protected void onPostExecute(List<ShoppingItem> results) {
            //어댑터에 데이타 변경 통지
            adapter.notifyDataSetChanged();
            //대화상자 닫기
            progressDialog.dismiss();
        }


    }


    //아이템 클릭 이벤트 처리
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }



}
