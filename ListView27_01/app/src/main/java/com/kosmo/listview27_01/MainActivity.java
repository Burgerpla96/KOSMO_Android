package com.kosmo.listview27_01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//public class MainActivity extends AppCompatActivity {
//1) ListView 상속 - 가장 짜르게 데이터를 뿌려 주 ㄹ수 있다. 레이아웃 전개가 필요없다.
public class MainActivity extends ListActivity {//ListVeiw 위젯이 내장된 activity
    //2) 데이터용
    private String[] items = {"메이플","포켓몬","마리오","소닉","젤다","피크민","커비","몬스터헌터"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //데이터의 목록만 표시할 때, 아래 주석처리( 레이아웃 전개)
        setContentView(R.layout.activity_main);
        //3) 어뎁터 생성
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
        //this.addHeaderView();
       
        
        
        
        //4) 연결 - ListActivity 상속시는 setAdapter() 가 아닌, setListAdapter()로 연결
        setListAdapter(adapter);

    }//////////////onCreate


    //이벤트 달기 - 기존과 다르게 View를 얻어 콜백메서드를 주는게 아닌 onListItemClick() 이용
    //콜백 메서드로 아이템 클릭시 이벤트 처리
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //헤더나 푸터를 달면 position과 id 가 달라진다.
        //position은 클릭 인덱스(헤더는 0, 푸터는 배열의 length)
        //id도 인덱스(0부터 시작 헤더나 푸터는 -1) - 클릭한 값을 파악할때 id 쓰기
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this,
                String.format("position: %s, id: %s, %s 선택",
                        position,id,items[(int)id]),0).show();

    }//////////onListItemClick

    
}/////////class