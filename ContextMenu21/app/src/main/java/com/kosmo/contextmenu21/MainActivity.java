package com.kosmo.contextmenu21;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //OptionMenu 할 때와 유사하다.

    private Button btnBack,btnScale;
    private ImageView imageView;
    private LinearLayout layout;
    private float mRotation;
    private float mScaleXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBack = findViewById(R.id.btn_background);
        btnScale = findViewById(R.id.btn_imagescale);
        imageView = findViewById(R.id.imageview);
        layout = findViewById(R.id.layout);

        //위젯(뷰) 롱 클릭시 컨텍스트 메뉴가 뜨도록 설정
        //registerForContextMenu()만 하면 setOnLongClickListener()로
        // 이벤트 달지 않아도 된다.
        //버튼
        registerForContextMenu(btnBack);
        registerForContextMenu(btnScale);
        registerForContextMenu(layout);

        /*
        위젯이 배치된 레이아웃에 컨텍스트 메뉴를 등록하면
        컨텍스트 메뉴를 가진 위젯에도 부모인 레이아웃의
        컨텍스트 메뉴 아이템이 추가된다.
        또한 부모 레이아웃의 컨텍스트 메뉴를 위젯의 컨텍스트 메뉴보다 아래에 표시하려면
        orderInCategoru 속성값을 위젯의 컨텍스트 메뉴보다 큰 숫자를 주기.
        기본적으로 부모 컨텍스트 메뉴가 아래에 나온다.
         */
        //longClick 이벤트 전파 확인
        /*
        btnBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("com.kosmo.contextmenu","버튼에서 롱클릭 발생");
                return false;//버튼에서는 true, false 모두 이벤트 전달 안된다.
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("com.kosmo.contextmenu","이미지뷰에서 롱클릭 발생");
                return true;
            }
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("com.kosmo.contextmenu","레이아웃에서 롱클릭 발생");
                return false;
            }
        });
        */

        //LongClick 이벤트와 click 이벤트를 동시에 걸 수 있다.
        //return true는 이벤트를 모두 소비.
        //return false는 이벤트를 나눠 소비할 수 있다.





    }//////////onCreate


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        if(v == btnBack){
            //배경색 변경용 레이아웃 xml 전개
            //방법1) xml로 생성
            //inflater.inflate(R.menu.background_menu,menu);
            //방법2)
            menu.add(Menu.NONE, 1, 1, "RED");
            menu.add(Menu.NONE, 2, 2, "GREEN");
            menu.add(Menu.NONE, 3, 3, "BLUE");
        }
        else if (v.getId() == R.id.btn_imagescale){
            //배경색 변경용 레이아웃 xml 전개
            //방법1) xml로 생성
            //inflater.inflate(R.menu.image_menu,menu);
            //방법2)
            menu.add(Menu.NONE, 4, 1, "90도 회전");
            menu.add(Menu.NONE, 5, 2, "2배 확대");
            menu.add(Menu.NONE, 6, 3, "2배 축소");

        }
        else{
            //리니어 레이아웃 롱 클릭시
            //방법1) xml로 생성
            //inflater.inflate(R.menu.layout_menu,menu);
            //방법2)
            menu.add(Menu.NONE, 7, 10, "레이아웃 메뉴");
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.layout_menu: case 7:
                Toast.makeText(this,"레이아웃 컨텍스트 메뉴",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuitem_red: case 1:
                layout.setBackgroundColor(Color.RED);
                break;
            case R.id.menuitem_blue: case 3:
                layout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.menuitem_green: case 2:
                layout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.degree_rotate: case 4:
                if(mRotation==360) mRotation=0;
                mRotation+=90;
                imageView.setRotation(mRotation);
                break;
            case R.id.scale_inc: case 5:
                mScaleXY+=2;
                imageView.setScaleX(mScaleXY);
                imageView.setScaleY(mScaleXY);
                break;
            case R.id.scale_desc: case 6:
                if(mScaleXY > 2){
                    mScaleXY-=2;
                    imageView.setScaleX(mScaleXY);
                    imageView.setScaleY(mScaleXY);
                }
                else if(mScaleXY ==2){
                    mScaleXY-=2;
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                }
                break;

        }
        return super.onContextItemSelected(item);
    }



}///////////class