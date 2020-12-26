package com.kosmo.intent24_03;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btndial;
    private Button btnweb;
    private Button btnmap;
    private Button btnsearch;
    private Button btnsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기]
        initView();
        //버튼에 리스너 부착
        btndial.setOnClickListener(handler);
        btnmap.setOnClickListener(handler);
        btnsearch.setOnClickListener(handler);
        btnsms.setOnClickListener(handler);
        btnweb.setOnClickListener(handler);
    }///////////onCreate

    //이벤트 핸들러
    private View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //암시적 인텐트: 특정 액티비티 클래스를 지정하지 않는다.
            //new Intent(액티비티 액션,Uri객체)
            if (v == btndial) {
                //[전화걸기]
                //액티비티 액션:ACTION_DIAL
                //uri- tel:01012345678
                //tel은 반드시 소문자로
                //※에뮬레이터(TARGET 23) 즉 마쉬멜로에서는 오류:실제 핸폰에서 동작
                //http://developer.android.com/intl/ko/training/permissions/requesting.htm
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1234-5678"));
                startActivity(intent);
            } else if (v == btnweb) {
                //[브라우저 실행]
                //액티비티 액션:ACTION_VIEW
                //uri- http나 https로 시작하는 URL지정
                //http나 https도 반드시 소문자로
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com"));
                startActivity(intent);
            } else if (v == btnmap) {
                //[구글맵]
                //액션:ACTION_VIEW
                //www.google.co.xn--kr-q04jk9m 구글지도 검색->
                //구글지도(https://maps.google.co.kr/)클릭->
                //해당 지역 클릭후 URL주소창 복사
                //아래에 붙여 넣기.여기서 15.16z는 축소 확대 레벨로 1부터 21까지 조정가능
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@37.4788647,126.8807286,16z?hl=ko"));
                //startActivity(intent);
                //위는 브라우저 실행임으로 에뮬레이터 타겟이 Google APIs로 안되어도 되나 아래는
                //Google APIs생성되어야 한다.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.4788647,126.8807286,20z"));
                startActivity(intent);
            } else if (v == btnsearch) {
                //[구글 검색하기]
                //액티비티 액션:ACTION_WEB_SEARCH
                //putExtra(SearchManager.QUERY,"검색할 단어");
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "이태원");
                startActivity(intent);
            } else {
                //[SMS문자 보내기]
                //액티비티 액션:ACTION_SENDTO
                //uri- smsto로 시작하는 URL지정
                //실제 핸드폰으로 테스트해야 문자가 보내진다.
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:01012345678"));
                startActivity(intent);
            }
        }/////////////onClick
    };

    private void initView() {
        btndial = (Button) findViewById(R.id.btndial);
        btnweb = (Button) findViewById(R.id.btnweb);
        btnmap = (Button) findViewById(R.id.btnmap);
        btnsearch = (Button) findViewById(R.id.btnsearch);
        btnsms = (Button) findViewById(R.id.btnsms);
    }
}///////////////class