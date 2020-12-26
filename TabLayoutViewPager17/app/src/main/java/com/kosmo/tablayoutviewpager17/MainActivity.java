package com.kosmo.tablayoutviewpager17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.kosmo.tablayoutviewpager17.content.TabContent1;
import com.kosmo.tablayoutviewpager17.content.TabContent2;
import com.kosmo.tablayoutviewpager17.content.TabContent3;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new Vector<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //widget 얻기
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        //tab 메뉴 추가 tab은 3,4개 권장
        tabLayout.addTab(tabLayout.newTab().setText("메뉴1"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tabmenu2));
        tabLayout.addTab(tabLayout.newTab().setText("메뉴3").setIcon(R.drawable.tabmenu3));

        //Fragment 생성후 컬렉션에 저장
        TabContent1 tabContent1 = new TabContent1();
        fragments.add(tabContent1);
        TabContent2 tabContent2 = new TabContent2();
        fragments.add(tabContent2);
        TabContent3 tabContent3 = new TabContent3();
        fragments.add(tabContent3);

        //view 페이저를 관리하는 PageAdapter를 생성
        MyPagerAdapter myPagerAdapter =
                new MyPagerAdapter(getSupportFragmentManager(), fragments);
        //ViewPager에 연결
        viewPager.setAdapter(myPagerAdapter);


        //리스너 설정
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//tab 메뉴 선택시 호출 메서드
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {//tab 비선택시

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {//tab 다시 선택시

            }
        });


    }////////onCreate

}