package com.kosmo.webview18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //위젯 얻기 (버튼 4개, webView 1개, EditText 1개)
        Button btnGo =findViewById(R.id.button_go);
        Button btnBack =findViewById(R.id.button_back);
        Button btnData1 =findViewById(R.id.btnData1);
        Button btnData2 =findViewById(R.id.btnData2);
        EditText editText = findViewById(R.id.edittext);
        WebView webView = findViewById(R.id.webview);
        
        //WebView 설정
        //1) WebView 객체의 getSettings()로 WebSettings 객체 얻기
        WebSettings settings = webView.getSettings();
        //자스가 실행되도록 설정 - 기본적으로 웹뷰는 자스를 지원하지 않음
        settings.setJavaScriptEnabled(true);//필수 설정

        //확대모드 설정- 옵션
        settings.setBuiltInZoomControls(true);

        //스마트폰 웹 뷰안에 사이트가 들어오도록 설정
        //아래부분 생략시 브라우저가 켜진다. - 옵션
        webView.setWebViewClient(new CustomWebViewClient());
        //자스의 alert 띄우기 (그대로 사용)- 옵션 기능
        //webView.setWebChromeClient(new WebChromeClient());
        //자스의 alert() 대신 Toast사용
        webView.setWebChromeClient(new CustomWebChromeClient());

        
        
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                //WebView의 loadUrl() method 호출
                //1) 사용자가 입력한 url을 로딩하기
                Log.i("com.kosmo.webview",url);
                // AndroidManifest.xml에
                //android:usesCleartextTraffic="true" 를 추가해야 한다.
                webView.loadUrl(url);

                //2) 앱안에 있는 html 파일 로딩하기
                //Android tab -> app 선택후 New -> Folder -> Assets Folder 생성
                //assets가 아닌 android_asset으로 url 설정
                //assets 폴더에 new directory 해서 폴더 생성
                //webView.loadUrl("file:android_asset/html/GoogleChart.html");

                //3) html 소스를 데이터로 사용해서 로드하기.
                String htmlData = "<html>\n" +
                        "  <head>\n" +
                        "  \t<meta charset=\"UTF-8\">\n" +
                        "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                        "    <script type=\"text/javascript\">\n" +
                        "      google.charts.load('current', {'packages':['corechart']});\n" +
                        "      google.charts.setOnLoadCallback(drawChart);\n" +
                        "\n" +
                        "      function drawChart() {\n" +
                        "\n" +
                        "        var data = google.visualization.arrayToDataTable([\n" +
                        "          ['Task', 'Hours per Day'],\n" +
                        "          ['일',     11],\n" +
                        "          ['식사',      2],\n" +
                        "          ['사회생활',  2],\n" +
                        "          ['TV보기', 2],\n" +
                        "          ['잠',    7]\n" +
                        "        ]);\n" +
                        "\n" +
                        "        var options = {\n" +
                        "          title: '나의 하루 일과'\n" +
                        "        };\n" +
                        "\n" +
                        "        var chart = new google.visualization.PieChart(document.getElementById('piechart'));\n" +
                        "\n" +
                        "        chart.draw(data, options);\n" +
                        "      }\n" +
                        "    </script>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>\n" +
                        "    <img alt=\"image\" src=\"../Images/sumnail.png\"/>\n" +
                        "  </body>\n" +
                        "</html>";
                //이렇게 하면 이미지가 표시가 안된다.
                //webView.loadData(htmlData,"text/html; charset=UTF-8","UTF-8");
                //loadDataWithBaseURL() 대신 사용
                //이미지가 들어있는 baseUrl을 설정 추가, historyUrl 추가
                /*
                webView.loadDataWithBaseURL("file:android_asset/Images/",
                        htmlData,"text/html; charset=UTF-8","UTF-8",
                        null);
                */
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });


        btnData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pieData="['20대',5],['30대',5],['40대',10],['50대',20]";
                webView.loadDataWithBaseURL("file:android_asset/Images/" ,
                    getHtmlString("연령대","연령비","연령대별 분포",pieData),
                        "text/html; charset=UTF-8","UTF-8",null);
            }
        });

        btnData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pieData="['등산',5],['드라이브',5],['수영',20],['서핑',20],['헬스',15]";
                webView.loadDataWithBaseURL("file:android_asset/Images/",
                        getHtmlString("연령대","연령비","연령대별 분포",pieData),
                        "text/html; charset=UTF-8","UTF-8",null);
            }
        });


    }/////////////////////onCreate


    //WebViewClient 상속 - 웹페이지 로딩 담당
    private class CustomWebViewClient extends WebViewClient{
        //shouldOverrideUrlLoading Override만 하면된다.
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }/////////


    //alert창을 Toast 로 주기 위한 클래스
    private class CustomWebChromeClient extends WebChromeClient{
        //onJsAlert() override
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(view.getContext(),message,Toast.LENGTH_SHORT).show();
            //자바 스크립트 경고창의 확인버튼을 클릭한 것으로 처리하도록 호출해야 한다.
            //alert() 는 모달이라 클릭 처리해야한다.
            result.confirm();//modal의 확인용
            return true;
        }
    }



    private String getHtmlString(String subTitle1, String subTitle2, String title, String pieData){

        return "<html>\n" +
                "  <head>\n" +
                "  \t<meta charset=\"UTF-8\">\n" +
                "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "      google.charts.load('current', {'packages':['corechart']});\n" +
                "      google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "      function drawChart() {\n" +
                "\n" +
                "        var data = google.visualization.arrayToDataTable([\n" +
                "          ['"+subTitle1+"', '"+subTitle2+"'],\n" +
                pieData +
                "        ]);\n" +
                "\n" +
                "        var options = {\n" +
                "          title: '"+title+"'\n" +
                "        };\n" +
                "\n" +
                "        var chart = new google.visualization.PieChart(document.getElementById('piechart'));\n" +
                "\n" +
                "        chart.draw(data, options);\n" +
                "      }\n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>\n" +
                "    <img alt=\"image\" src=\"../Images/sumnail.png\"/>\n" +
                "  </body>\n" +
                "</html>";

    }







}//////////class