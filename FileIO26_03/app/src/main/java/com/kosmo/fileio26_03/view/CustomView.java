package com.kosmo.fileio26_03.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    //이미지 리소스 아이디-MainActivity에서 설정 예정
    private int resId = -1;
    public void setResId(int resId) {
        this.resId = resId;
    }

    //자바코드로 생성할때
    public CustomView(Context context) {
        super(context);
    }


    //xml에서 할때
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        /*
          BitmapFactory.decodeResource(Resources객체,int 리소스 아이디):res폴더 밑에 있는 이미지 파일 얻어올때
          BitmapFactory.decodeFile(String 이미지파일 경로):내부/및 외부 저장소에 저장된 이미지 파일 얻어올때
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
        //원본 이미지 크기 설정
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        //커스텀 뷰의 가로/세로 크기에 맞추기
        Rect dst = new Rect(100,610,getWidth()-100,getHeight()-610);

        //이미지 그리기
        canvas.drawBitmap(bitmap,src,dst,null);
        //메모리 누수를 막기위해, 사용한 비트맵을 null 처리하도록
        bitmap.recycle();

    }////////////////onDraw


}///////////class
