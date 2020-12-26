package com.kosmo.jsonparser32_02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class JSONAdapter extends BaseAdapter{

    private Context context;
    private List<JSONItem> items;
    private int layoutResId;

    public JSONAdapter(Context context, List<JSONItem> items, int layoutResId) {
        this.context = context;
        this.items = items;
        this.layoutResId = layoutResId;
    }////////////////constructor

    @Override
    public int getCount() {
        return items.size();
    }/////////////////getCount

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }////////////getItem

    @Override
    public long getItemId(int position) {
        return position;
    }/////////////getItemId


    //리스트 뷰에서 매개변수를 전달해 준다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context,layoutResId,null);
        }
        //위젯얻고 데이터 설정
        ((TextView)convertView.findViewById(R.id.tvage)).setText(items.get(position).getAge());
        ((TextView)convertView.findViewById(R.id.tvhobbys)).setText(items.get(position).getHobbys());
        ((TextView)convertView.findViewById(R.id.tvlogin)).setText(items.get(position).getLoginInfo());
        ((TextView)convertView.findViewById(R.id.tvname)).setText(items.get(position).getName());

        if(position%2 ==0){
            convertView.setBackgroundColor(0x8899FF77);
        }
        else{
            convertView.setBackgroundColor(0x8899FF99);
        }
        return convertView;
    }////////////getview


}////////////class
