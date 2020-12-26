package com.kosmo.optionmenu20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mLayout;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");//액션바 공간 확보
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.layout);
        mImageView = findViewById(R.id.imageView);

    }//////////onCreate




    //콜백 메서드: 옵션 메뉴가 생성(xml 혹은 자바코드) 될때 자동으로 호출
    //xml사용이 편하다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu : 메뉴 전개, 아이템들을 액션바에 추가한다.
        //방법1 : 메뉴용 xml로 옵션 메뉴 전개
        //getMenuInflater().inflate(R.menu.option_menu,menu);

        //방법2: 자바코드로만 옵션 메뉴 생성

         /*
        add(int groupId,int itemId,int order,CharSequence title)메소드로 메뉴 추가
		인자 설명]
		groupId:그룹아이디로 그룹에 포함되않은 경우는 Menu.NONE이나 0설정
		itemId:메뉴 아이템의 아이디,필요 없을 경우 Menu.NONE 이나 0
		order:메뉴 아이템의 순서.순서를 지정하고 싶지 않으면 Menu.NONE 이나 0
		title:메뉴명
         */

        menu.add(Menu.NONE, 101, 1, "옵션메뉴1");//option 메뉴 ... 나타남
        menu.add(Menu.NONE, 102, 2, "옵션메뉴2").setIcon(R.drawable.away)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 103, 3, "옵션메뉴3").setIcon(R.drawable.offline)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(Menu.NONE, 104, 6, "RED");
        menu.add(Menu.NONE, 105, 5, "GREEN");
        menu.add(Menu.NONE, 106, 4, "BLUE");

        //서브메뉴 추가
        SubMenu submenu = menu.addSubMenu(Menu.NONE,107,7,"이미지 변환");
        submenu.add(Menu.NONE, 1, 1, "90도 회전");
        submenu.add(Menu.NONE, 2, 2, "2배 축소");
        submenu.add(Menu.NONE, 3, 3, "2배 확대");

        return super.onCreateOptionsMenu(menu);//지우면 안된다.
    }

    private float mRotation;
    private float mScaleXY;
    //콜백 메서드: 옵션 메뉴 아이템을 선택했을 때 자동으로 호출되는 메서드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.opt_menu1: case R.id.opt_menu2: case R.id.opt_menu3: case 101:
            case 102: case 103:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuitem_red: case 104:
                mLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.menuitem_blue: case 106:
                mLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.menuitem_green: case 105:
                mLayout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.degree_rotate: case 1:
                if(mRotation==360) mRotation=0;
                mRotation+=90;
                mImageView.setRotation(mRotation);
                break;
            case R.id.scale_inc: case 3:
                mScaleXY+=2;
                mImageView.setScaleX(mScaleXY);
                mImageView.setScaleY(mScaleXY);
                break;
            case R.id.scale_desc: case 2:
                if(mScaleXY > 2){
                    mScaleXY-=2;
                    mImageView.setScaleX(mScaleXY);
                    mImageView.setScaleY(mScaleXY);
                }
                else if(mScaleXY ==2){
                    mScaleXY-=2;
                    mImageView.setScaleX(1);
                    mImageView.setScaleY(1);
                }
                break;

        }

        return super.onOptionsItemSelected(item);//부모에게 전달위해 지우면 안된다.
    }




}/////////class