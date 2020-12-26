package com.kosmo.touchnevent19.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Vector;

//1) View class 상속
public class MyTouchDrawing extends View {
    //붓 준비
    private Paint paint;
    //사용자가 터치한 지점의 좌표를 저장할 컬랙션
    private Vector<Dot> dots = new Vector<Dot>();

    //2) xml용 생성자
    public MyTouchDrawing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //붓생성
        paint = new Paint();
        //붓 색상
        paint.setColor(Color.RED);
        //붓 두께 설정
        paint.setStrokeWidth(20);//px단위
        //붓 마감 설정(깔끔하게)
        paint.setAntiAlias(true);
    }//////////////MyTouchDrawing


    //MyTouchDrawing 이라는 내가 만든 View영역을 터치할 경우 자동 호출되는 콜백 메서드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){// 눌렀을 때
            //최초 터치- 점의 좌표만 저장
            dots.add(new Dot((int)event.getX(), (int)event.getY(),false));
            Log.i("com.kosmo.touchnevent",
                    "[Action_down] X좌표: "+event.getX()+"Y좌표: "+event.getY());
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){//터치후 움직였을 때
            //그릴 수 있게 Dot에 true값 준다.
            dots.add(new Dot((int)event.getX(), (int)event.getY(),true));
            //invalidate(); 메서드로 onDraw() 호출 해서 그린다.
            invalidate();
            Log.i("com.kosmo.touchnevent",
                    "[Action_down] X좌표: "+event.getX()+"Y좌표: "+event.getY());
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            dots.add(new Dot((int)event.getX(), (int)event.getY(),false));
        }
        return true;//이벤트 전파 방지
    }///////onTouchEvent


    @Override
    protected void onDraw(Canvas canvas) {
        //Canvas 배경 설정
        canvas.drawColor(Color.argb(100,125,125,125));
        //백터에 저장도니 점의 좌표로 선 그리기
        //점이 n개인 경우, n-1개의 선이 그려진다.
        for(int i=0; i<dots.size()-1 ; i++){
            //canvas의 drawLine(시작점x,시작점y,끝점x,끝점y,Paint객체)
            if(dots.get(i).isDrawing()){//터치를 유지할 때만 그리기
                canvas.drawLine(
                        dots.get(i).getxPos(),
                        dots.get(i).getyPos(),
                        dots.get(i+1).getxPos(),
                        dots.get(i+1).getyPos(),
                        paint);
            }
        }

    }/////////onDraw



}
