package com.kosmo.kosmoapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.kosmoapp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Fragment_4 extends Fragment {
    //아이디 비번 저장용
    private String id;
    private String pwd;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("com.kosmo.kosmoapp","onAttach:4");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //shared 로 로그인 정보 저장 확인 Session이 아니다.
        SharedPreferences preferences = getContext().getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);

        id=preferences.getString("id",null);
        pwd=preferences.getString("pwd",null);
        Log.i("com.kosmo.kosmoapp",id+":"+pwd);
    }

    //2]onCreateView()오버 라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("com.kosmo.kosmoapp","onCreateView:4");



        //프래그먼트 레이아웃 전개
        View view=inflater.inflate(R.layout.tablayout_4,null,false);
        //웹뷰 얻기]
        WebView webView = view.findViewById(R.id.webview);
        //WebView설정]
        //1]WebView의 getSettings()메소드로 WebSettings객체
        WebSettings settings=webView.getSettings();
        //자스가 실행되도록 설정- 기본적으로 웹뷰는 자스를 지원하지 않음]
        settings.setJavaScriptEnabled(true);//필수 설정
        // 아래부분 생략시 웹뷰가 전체 레이아웃을 차지함(사이트 로드시)]
        webView.setWebViewClient(new CustomWebViewClient());
        //자스의 alert()모양을 Toast 로 변경
        webView.setWebChromeClient(new CustomWebChromeClient());
        //get요청_loadUrl() method 이용
        //webView.loadUrl("http://hwanyhee.iptime.org:8080/onememo/");
        //post요청_ postUrl() 이용
        try {
            String params = "id=" + URLEncoder.encode(id, "UTF-8") + "&pwd=" + URLEncoder.encode(pwd, "UTF-8");
            //주의: Spring_me 에 맞춰서 주소를 바꿈_ webView 를 이용해서 여러 스프링 서버를 연결했다.
            //토큰 인증 방식을 이용하지 않고서, REST API 사용해서 json으로 받고 SharedPreferences 사용
            //id와 pwd를 URLEncoder사용해서 보냄
            webView.postUrl("http://192.168.0.25:9090/springapp/OneMemo/Auth/LoginProcess.do", params.getBytes());
        }
        catch(UnsupportedEncodingException e){e.printStackTrace();}

        return view;
    }/////////


    //WebViewClient상속]-웹 페이지 로딩 담당
    private class CustomWebViewClient extends WebViewClient{
        //오버라이딩만하면 된다]
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }////////////


    private class CustomWebChromeClient extends WebChromeClient{
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //경고 메시지를 Toast로 보여주기
            Toast.makeText(view.getContext(),message,Toast.LENGTH_SHORT).show();
            //자바스크립트 경고창의 확인버튼을 클릭한것으로 처리하도록 호출
            //해야한다 alert()는 모달이라 클릭한 것으로 처리안하면
            //다른 메뉴를 클릭 할 수 없다
            result.confirm();
            return true;
        }
    }



}
