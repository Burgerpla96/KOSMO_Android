package com.kosmo.customview10_01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

//1) View class 상속받아서 View class로 만들기
public class CustomView extends View {

    //2) Context를 매개변수로
    //기본 생성자가 없기에 생성자를 만들어 주어야 한다.
    public CustomView(Context context) {
        super(context);
    }

    //3) onDraw() 메서드 오버라이딩
    @Override
    protected void onDraw(Canvas canvas) {
        //배경색 설정
        canvas.drawColor(Color.CYAN);
        //붓의 종류 선택
        //경계부분을 부드럽게(ANTI_ALIAS) 처리할 수 있는 붓 사용
        //Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //붓 색상 설정
        paint.setColor(Color.RED);
        //붓 두께 설정(단위는 px)
        paint.setStrokeWidth(30);
        //점 그리기(x,y 좌표는 좌측 상단이 0,0이고 px단위)
        canvas.drawPoint(100,100,paint);
        //선 그리기
        paint.setStrokeWidth(10);
        canvas.drawLine(150,150,500,150,paint);
        //사각형 그리기
        paint.setColor(0x880000FF);
        canvas.drawRect(150,150,500,350,paint);
        //원
        paint.setColor(Color.argb(255,0,255,0));
        canvas.drawCircle(250,450,100,paint);
        //이미지 그리기
        //Paint는 null지정해야 히미지 그대로의 색으로 그려진다.
        //원본 크기 그대로 출력
        //1) drawBitmap(Bitmap객체, x좌표, y좌표,Paint 객체);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.btn_rating_star_on_normal);
        //canvas.drawBitmap(bitmap,250,550,null);

        //2) drawBitmap(Bitmap객체,null,Rect dext,Paint객체 null);
        //null지정시 원본이미지 전체가 선택된 후 확대 축소 가능
        canvas.drawBitmap(bitmap,null,new Rect(250,550,250+400,550+400),null);

        //3) 원본의 특정 부분만 선택후 축소 가능
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //Logcat으로 확인
        Log.i("com.kosmo.customview","width: "+width+", height: "+height);
        canvas.drawBitmap(bitmap,new Rect(0,0,120,120),
                new Rect(250,550,250+400,550+400),null);


    }////////

}

