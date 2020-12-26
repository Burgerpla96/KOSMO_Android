package com.kosmo.intent24_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //투표수 저장할 배열
    private int[] votes = new int[9];
    //ImageView 객체를 저장할 배열
    private ImageView[] images = new ImageView[9];
    //영화제목 저장할 배열
    private String[] titles = {"조커","보통의 연예","제미니","퍼펙트맨","소피와 드래곤","장사리",
                                    "세계를 찾아서","벌새","판소리 복서"};
    private int[] resIds = {R.id.iv1,R.id.iv2,R.id.iv3,R.id.iv4,R.id.iv5,
                                R.id.iv6,R.id.iv7,R.id.iv8,R.id.iv9};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("영화선호도 투표");
        setContentView(R.layout.activity_main);
        //ImageView 위젯 얻고 click 리스너 붙이기
        for(int i =0;i<votes.length;i++){
            //내부 클래스에서 지역변수를 사용하려면 final로 지정해야한다.
            final int index = i;
            images[i] = findViewById(resIds[i]);
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    votes[index]++;
                    Log.i("com.kosmo.intent",String.format("%s : %s",titles[index],votes[index]));
                }
            });
        }///for

        findViewById(R.id.btnresult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //명시적 인텐트 생성 - ResultActivity 지정
                //new Intent(Context, 전환할 액티비티.class)
                Intent intent = new Intent(v.getContext(),ResultActivity.class);
                //전환할 액티비티로 제목과 투표수 전달 Intent 의 putExtra사용
                intent.putExtra("votes",votes);
                intent.putExtra("titles",titles);
                //액티비티 전환
                startActivity(intent);
            }
        });

    }//////////onCreate


}///////////class