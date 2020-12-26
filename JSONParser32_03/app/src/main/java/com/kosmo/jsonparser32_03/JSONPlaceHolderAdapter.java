package com.kosmo.jsonparser32_03;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;



/*
0.build.gradle에(https://developer.android.com/training/volley)
implementation 'com.android.volley:volley:1.1.1' 추가

1.원격 이미지 로드하기 위해
https://cypressnorth.com/mobile-application-development/setting-android-google-volley-imageloader-networkimageview/
사이트 참고후
https://github.com/CypressNorth/Volley-Singleton에서 다운
2. 1에서 다운 받은 VolleySingleton.java를 추가
3. VolleySingleton.java파일에서
Volley.newRequestQueue(MyApplication.getAppContext());이부분을
Volley.newRequestQueue(MainActivity.APP_CONTEXT);로 수정
단,MainAtivity에 정적 멤버로 APP_CONTEXT추가
예]
public static Context APP_CONTEXT;
onCreate()메소드 안에 아래 내용 추가
APP_CONTEXT=getApplicationContext();
4. android.support.v4.util.LruCache를
   android.util.LruCache로 변경
   (https://developer.android.com/reference/android/support/v4/util/LruCache)

위 라이브러리는 410에러(2020-5월기준):스프링 서버에 이미지 올려놓고 이미지 URL로 테스트시에는 정상)
아래 라이브러리 사용
//Picasso라이브러리 참조 사이트:https://github.com/square/picasso
//외부(원격)로부터 이미지를 불러와야 할 경우 유용하게 사용할 수 있는 라이브러리이다.
//매우 간단한 코드 몇 줄로 이미지 로딩
//라이브러리 등록 방법
// build.gradle (Module:app)파일에 implementation 'com.squareup.picasso:picasso:(insert latest version)' 추가
//마지막으로 아래처럼 코딩
//Picasso.get().load(items.get(position).getImageUrl()).into(imageView);
Picasso사용시에는 NetworkImageView대신 ImageView 사용

*/
public class JSONPlaceHolderAdapter extends BaseAdapter {

    private Context context;
    private List<JSONPlaceHolderItem> items;
    //volley 라이브러리 사용: 이미지뷰에 원격 이미지 로드시
    private ImageLoader imageLoader;

    public JSONPlaceHolderAdapter(Context context, List<JSONPlaceHolderItem> items) {
        this.context = context;
        this.items = items;
        imageLoader = VolleySingleton.getInstance().getImageLoader();
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
        //위젯얻고 데이터 설정
        ((TextView)convertView.findViewById(R.id.itemId)).setText(items.get(position).getId());
        ((TextView)convertView.findViewById(R.id.itemTitle)).setText(items.get(position).getTitle());
        //이미지 뷰 Volly 사용시
        //NetworkImageView itemImage = convertView.findViewById(R.id.itemImage);
        //itemImage.setImageUrl(items.get(position).getThumbnailUrl(),imageLoader);
        //spring 서버에 이미지 저장후 테스트
        //여기!!!
        //itemImage.setImageUrl("http://192.168.0.25:8080/springapp/images/placeholder.png",imageLoader);


        //picasso사용시
        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        Picasso.get().load(items.get(position).getThumbnailUrl()).into(itemImage);


        return convertView;
    }

}
