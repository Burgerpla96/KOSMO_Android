package com.kosmo.tablayoutviewpager17.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.tablayoutviewpager17.R;

//1)Fragment 상속받아서 Activity처럼 한 화면을 구성하는 Fragment 클래스 생성
public class TabContent2 extends Fragment {
    //2) onCreateView() 오버라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tabmenu2_layout,null,false);
    }
}
