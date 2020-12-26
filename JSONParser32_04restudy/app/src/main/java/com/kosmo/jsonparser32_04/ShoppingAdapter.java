package com.kosmo.jsonparser32_04;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingItem> items;

    public ShoppingAdapter(Context context, List<ShoppingItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_layout,null);
        }
        ((TextView)convertView.findViewById(R.id.title)).setText(items.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.maker)).setText(items.get(position).getMaker());
        ((TextView)convertView.findViewById(R.id.hprice)).setText(items.get(position).getHprice());
        ((TextView)convertView.findViewById(R.id.lprice)).setText(items.get(position).getLprice());


        //picasso 사용
        Picasso.get().load(items.get(position).getImage()).into(
                (ImageView)convertView.findViewById(R.id.image));


        return convertView;
    }//////////////////////


}
