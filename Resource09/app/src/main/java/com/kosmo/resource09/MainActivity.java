package com.kosmo.resource09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    /*
    drawable -이미지 파일 혹은 XML파일
	          (도형을 정의한 파일-getDrawable()메소드로 읽어 온다
	          R.drawable.아이디를 메서드의 매개변수로 넣기.)
     values  -문자열,배열,크기,색상,스타일을 지정하는 XML파일
          (getString(), getStringArray(), getIntArray(), getDimension(),getColor()등)
               파일명은 강제사항은 아니나 권장 파일명이 있다]
                strings.xml
                arrays.xml
                dimens.xml
                colors.xml
                styles.xml

     menu     -옵션메뉴나 컨텍스트 메뉴등을 정의한 XML파일(별도의 읽는 메소드 없음)
     raw      -이진파일을 저장하는 폴더(폰트등)(openRawResource())
     layout   -레이아웃을 정의한 XML파일(별도의 읽는 메소드 없음)
     xml      -앱 실행중에 읽어서 사용할 XML파일(getXml())
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //위젯 얻기
        TextView textView = findViewById(R.id.code_textview);
        ImageView imageView = findViewById(R.id.code_imageview);
        Button button = findViewById(R.id.button);
        //res 폴더에 정의된 모든 리소스를 얻어 올 수 있는 클래스(Resources)
        //Resources - getResources() 로 얻는다.
        Resources resources = getResources();
        /*
        values 폴더안에 string.xml과 drawable 폴더의 image에 접근시
        getString() 이나, getDrawable() 로 바로 접근이 가능하다.
        //Resources 객체가 불필요
        */
        Log.i("com.kosmo.resource",String.format(
                "Resources객체 미사용: %s, Resources객체 사용: %s",
                getString(R.string.codeMessage),
                resources.getString(R.string.codeMessage)));

        //자바코드로 위젯의 속성 설정
        //setText(int resId) 사용
        //textView.setText(R.string.codeMessage);
        textView.setText(getString(R.string.codeMessage));
        //2-3 textSize변경
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,resources.getDimension(R.dimen.xmlDimen));
        //getDimension()은 pixel 형태로 변환해서 리턴한다. 따라서 COMPLEX_UNIT_PX을 사용해서 크기지정
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.xmlDimen));

        //2-4 setTextColor()
        //textView.setTextColor(Color.RED);
        //textView.setTextColor(resources.getColor(R.color.xmlColor));
        textView.setTextColor(ContextCompat.getColor(this,R.color.xmlColor));

//android: src 속성 설정
//방법1 ImageView 객체 setImageResource(int 리소스 아이디)이용
//imageView.setImageResource(R.drawable.picture_emergency);
//방법2 setImageDrawable() use
//imageView.setImageDrawable(resources.getDrawable(R.drawable.picture_emergency,null));
//방법3 setImageBitmap() use
//3-1 BitmapDrawable 객체 이용
//Bitmap bitmap = ((BitmapDrawable) getDrawable(R.drawable.picture_emergency)).getBitmap();
//imageView.setImageBitmap(bitmap);
//3-2 BitmapFactory.decodeResource() use
//방법 1이나 3-2 자주 사용
imageView.setImageBitmap(BitmapFactory.decodeResource(resources,R.drawable.picture_emergency));

        //배열보여주는 버튼 만들기
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resources.get계열로 가져온다.
                Toast.makeText(v.getContext(),
                        Arrays.toString(resources.getIntArray(R.array.intArray)),
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(),
                        Arrays.toString(resources.getStringArray(R.array.stringArray)),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
}