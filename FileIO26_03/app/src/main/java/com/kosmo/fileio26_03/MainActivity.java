package com.kosmo.fileio26_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kosmo.fileio26_03.view.CustomView;

public class MainActivity extends AppCompatActivity {

    //이미지 리소스 아이디 저장할 배열
    private int[] resIds = {R.raw.pic1, R.raw.pic2, R.raw.pic3};
    //커스텀 뷰용
    private CustomView customView;
    //현재 인덱스 저장용
    private int currentIndex;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //이미지 없을 때 메세지 뿌려줄 dialog 생성
        dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_menu_compass)
                .setTitle("알림 정보")
                .setPositiveButton("확인",null)
                .create();

        customView = findViewById(R.id.customview);
        customView.setResId(resIds[0]);
        customView.invalidate();//onDraw() 가 호출된다.

    }/////////onCreate




    public void previous(View view){
        if (currentIndex==0){
            dialog.setMessage("이전 이미지가 없어요");
            dialog.show();
            return;
        }
        //현재 인덱스 감소
        currentIndex--;
        //현재 인덱스로 커스텀뷰의 resId 값을 설정
        customView.setResId(resIds[currentIndex]);
        //invalidate() 로 onDraw() 호출시킴
        customView.invalidate();
    }///////////////previous


    public void next(View view){
        if (currentIndex==resIds.length-1){
            dialog.setMessage("다음 이미지가 없어요");
            dialog.show();
            return;
        }
        //현재 인덱스 증가
        currentIndex++;
        //현재 인덱스로 커스텀뷰의 resId 값을 설정
        customView.setResId(resIds[currentIndex]);
        //invalidate() 로 onDraw() 호출시킴
        customView.invalidate();
    }///////////////next

}