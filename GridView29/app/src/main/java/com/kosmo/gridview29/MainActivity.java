package com.kosmo.gridview29;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //이미지 리소스 아이디 배열
    private int[] resIds = {
            R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,
            R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,
            R.drawable.pic9
    };
    //영화 제목
    private String[] movies = {
            "조커","보통의 연애","제미니","퍼펙트맨",
            "마법의 비밀","장사리","잃어버린 세계를 찾아서","제목 없음","판소리 복서"
    };
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯 얻기
        gridView = findViewById(R.id.gridview);

        //adapter 생성 후 데이터 연결, widget(gridview)에 연결
        CustomAdapter adapter = new CustomAdapter(this,movies,resIds);
        gridView.setAdapter(adapter);

        //※리스트뷰와는 다르게 다른 어댑터뷰들은 아이템뷰를 커스텀뷰(내가 만든 뷰)
        //  로 구성시 커스텀 어댑터에서 이벤트 처리를 해도 되고
        //  어댑터뷰(그리드뷰)에 리스너를 부착해도 된다.
        //  그러나 리스트뷰는 커스텀뷰로 구성시 반드시 커스텀 어탭터에서 이벤트 처리 해야한다.

        //커스텀 뷰에 리스너 달기 setOnItemClickListener()
        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //다이어 로그용 레이아웃 전개
                View itemView = View.inflate(MainActivity.this,R.layout.dialog_layout,null);
                //이미지 클릭한 경우 해당 이미지를 ImageView 에 설정
                ImageView movieDialog = itemView.findViewById(R.id.movieDialog);
                movieDialog.setImageResource(resIds[position]);
                new AlertDialog.Builder(parent.getContext())
                        .setIcon(android.R.drawable.ic_menu_compass)
                        .setTitle(movies[position])
                        .setView(itemView).show();
            }
        });
        */



    }///////////////onCreate



}/////////class