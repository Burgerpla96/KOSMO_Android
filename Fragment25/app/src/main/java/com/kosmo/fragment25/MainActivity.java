package com.kosmo.fragment25;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //fragment 관리자 // androidx.fragment.app.FragmentManager 쓰기(FragmentManager 두 개 중에)
    private FragmentManager fragmentManager;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기
        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);
        Button btnThirdFragment = findViewById(R.id.btnThirdFragment);
        //버튼에 리스너 부착
        btnFirstFragment.setOnClickListener(handler);
        btnSecondFragment.setOnClickListener(handler);
        btnThirdFragment.setOnClickListener(handler);
        //fragment 객체 생성
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        //액티비티에서 프래그먼트에 데이터 전달
        //프래그먼트 객체.setArguments() 로 데이터 전달 Bundle을 만들어서 넘겨줘야 한다.
        Bundle bundle = new Bundle();
        bundle.putString("KOSMO","한국 소프웨어 인재개발원");
        firstFragment.setArguments(bundle);

        //getSupportFragmentManager() 메서드로 FragmentManager얻기
        fragmentManager = getSupportFragmentManager();
        //transaction 생성
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //replace() 로 해당 영역의 레이아웃을 프레그먼트로 대체
        //replace(바꿔질 영역 아이디, 프래그먼트 객체)
        transaction.replace(R.id.bottomLayout,firstFragment);
        //commit해야 반영된다.
        transaction.commit();

    }/////////////onCreate


    //이벤트 헨들러
    private View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnFirstFragment){
                //메서드 체인형식
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, firstFragment).commit();
            }
            else if(v.getId() == R.id.btnSecondFragment){
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, secondFragment).commit();
            }
            else {
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, thirdFragment).commit();
            }
        }

    };









    //TOP부분:XML코드로 프래그먼트 붙이기 시작
    //Fragment를 상속한 클래스 작성(내부 정적 클래스 혹은 별도 외부 클래스로)
    //내부 정적! 클래스 만들기 Fragment를 상속
    public static class TopFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //전개한 view --inflater
            // LayoutInflater로 레이아웃 전개

            return inflater.inflate(R.layout.top_fragment_layout, container);
        }
    }///////////TopFragment


    //첫 번째 화면용
    public static class FirstFragment extends Fragment{

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.first_fragment_layout,container,false);
            view.findViewById(R.id.btnGetData).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //데이터 받아서 Toast로 뿌려주기
                    //getArguments() 로 Bundle 객체 얻기
                    Bundle bundle = getArguments();
                    String value = bundle.getString("KOSMO");
                    Toast.makeText(v.getContext(),"액티비티에서 받은 데이터: "+value,0).show();
                }
            });
            return view;
        }
    }////////////FirstFragment


    public static class SecondFragment extends  Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.second_fragment_layout,container,false);
            //주의: fragment를 교체하려면 inflate() 마지막인자로 false 주기!!
        }
    }///////////////////SecondFragment


    public static class ThirdFragment extends  Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.third_fragment_layout,container,false);
            //주의: fragment를 교체하려면 inflate() 마지막인자로 false 주기!!
        }
    }////////////////ThirdFragment



}///////////class