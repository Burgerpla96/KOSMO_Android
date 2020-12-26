package com.kosmo.sqlite30_02;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/*
    1]CusorAdapter상속
    2]생성자 정의
    3]newView()와 bindView()메소드 오버라이딩-
    커서 어댑터가 아닌 다른 어댑터(BaseAdapter상속)의 getView()기능과 동일
    getView() = newView(껍데기 생성)+bindView(데이타 표시)

 */
public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }/////////////////생성자



    //text 뿐만 아니라 이미지뷰 같은 것도 사용하기 위함
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //두번째 인자의 ViewGroup은 반드시 null 지정

        //방법 1) context.getSystemService() 사용
        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = inflater.inflate(R.layout.record_layout,null);

        //방법 2)  LayoutInflater.from() 사용
        //LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.record_layout,null);

        //방법 3) View.inflate() 이용
        View view = View.inflate(context,R.layout.record_layout,null);

        return view;
    }///////////////newView



    //newView() 가 반환한 view를 받는다.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //위젯 얻기
        TextView tvAge = view.findViewById(R.id.tvage);
        TextView tvDate = view.findViewById(R.id.tvdate);
        TextView tvName = view.findViewById(R.id.tvname);
        TextView tvUser = view.findViewById(R.id.tvuser);

        //텍스트 뷰에 데이터 설정 -- 데이터는 cursor에서 얻어온다.
        tvAge.setText(cursor.getString(3));
        tvDate.setText(cursor.getString(4));
        tvName.setText(cursor.getString(2));
        tvUser.setText(cursor.getString(1));




    }///////////////bindView


}/////////////class
