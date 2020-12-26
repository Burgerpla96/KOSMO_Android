package com.kosmo.gridview29;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


//추상 클래스 BaseAdapter 상속
public class CustomAdapter extends BaseAdapter {
    //그리드 뷰가 위치하는 컨텍스트
    private Context context;
    //데이터
    private String [] movies;
    private int[] resIds;



    //생성자
    public CustomAdapter(Context context, String[] movies, int[] resIds){
        this.context=context;
        this.movies=movies;
        this.resIds=resIds;
    }


    @Override
    public int getCount() {
        return movies.length;
    }/////////////getCount - 아이템 총 갯수 반환


    @Override
    public Object getItem(int position) {
        return movies[position];
    }/////////////getItem - index에 해당하는 아이템 반환


    @Override
    public long getItemId(int position) {
        return position;
    }////////////////////getItemId - position에 해당하는 item의 id 반환


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //하나의 아이템을 가진 convertView
        //뷰를 표시하는 뷰 그룹

        //선생님 설명 요약:
        //ListView 와 adapter가 메서드를 호출하며
        //ListView에서 데이터를 뿌려줄 View 를 요구(convertView)
        //parent는 listView 자신을 반환한다.

        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_layout,null);
        }

        //위젯 얻고 position 인덱스에 해당하는 각 위젯의 데이터 설정
        ImageView movieIcon = convertView.findViewById(R.id.movieicon);
        movieIcon.setImageResource(resIds[position]);
        TextView movieTitle = convertView.findViewById(R.id.movietitle);
        movieTitle.setText(movies[position]);

        //이벤트 달기
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이어 로그용 레이아웃 전개
                View itemView = View.inflate(context,R.layout.dialog_layout,null);
                //이미지 클릭한 경우 해당 이미지를 ImageView 에 설정
                ImageView movieDialog = itemView.findViewById(R.id.movieDialog);
                movieDialog.setImageResource(resIds[position]);
                new AlertDialog.Builder(parent.getContext())
                        .setIcon(android.R.drawable.ic_menu_compass)
                        .setTitle(movies[position])
                        .setView(itemView).show();
            }
        });

        return convertView;
    }//////////////////getView - position에 해당하는 View 반환

}
