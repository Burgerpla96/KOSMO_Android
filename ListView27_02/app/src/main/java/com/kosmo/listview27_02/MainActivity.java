package com.kosmo.listview27_02;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MainActivity extends ListActivity/*AppCompatActivity*/ {
    //2) 데이터용
    //2-2) 배열
//    private List items =
//            new Vector(Arrays.asList(
//                    "메이플","포켓몬","마리오","소닉","젤다","피크민","커비","몬스터헌터",
//                    "겟엠프드","트릭스터","군주","대항해시대","라쳇엔클랭크","록맨"));

    //Arrays.asList()쓰면 오류남...



    private List items = new Vector();

    //리스트 저장용
    private ListView listView;
    //NONE 모드일 때, 클릭한 아이템 저장용
    private String selectedItemNone;
    //SINGLE
    private String selectedItemSingle;
    //MULTIPLE
    private List<String> selectedItems = new Vector<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //목록만 뿌려중 경우, ListActivity 상속시 불필요.
        setContentView(R.layout.activity_main);

        //collection 사용시
        items.add("1"); items.add("2"); items.add("3"); items.add("4"); items.add("5");
        items.add("6"); items.add("7");


        //3) 어뎁터 생성
        //layout이 simple_list_item_1 일때, mode(싱글 혹은 멀티모드) 설정 불필요
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
        //layout이 simple_list_item_checked 일때, mode(싱글 혹은 멀티모드) 설정 필요
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,items);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice,items);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_multichoice,items);



        //4) 연결 - ListActivity 상속시는 setAdapter() 가 아닌, setListAdapter()로 연결
        setListAdapter(adapter);


        //5) listView 얻기
        listView = getListView();
        // 5-1) 꾸미기
        //분리선 색, 두께
        listView.setDivider(new ColorDrawable(Color.RED));
        listView.setDividerHeight(5);

        //자바코드로 헤더와 푸터 얻기
        /*
        TextView header = new TextView(this);
        header.setText("Game List");
        header.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        header.setLayoutParams(layoutParams);//크기 설정
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        header.setTextColor(Color.WHITE);
        header.setBackgroundColor(Color.BLACK);

        //header 붙이기
        listView.addHeaderView(header);


        TextView footer = new TextView(this);
        footer.setText("하고 싶은 게임들");
        footer.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        footer.setLayoutParams(params);
        footer.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        footer.setTextColor(Color.WHITE);
        footer.setBackgroundColor(Color.BLACK);

        listView.addFooterView(footer);
        */

        //xml로 헤더와 푸터 설정
        View header = getLayoutInflater().inflate(R.layout.header_layout,null);
        View footer = getLayoutInflater().inflate(R.layout.footer_layout,null);
        listView.setHeaderDividersEnabled(false);
        listView.setFooterDividersEnabled(false);//footer에 감싸진 선이 없어짐.
        listView.addHeaderView(header);
        listView.addFooterView(footer);


        //6) listView 모드 설정
        // 레이아웃이 simple_list_item_checked나 simple_list_item_single_choice나
        //           simple_list_item_multiple_choice일때는 반드시 모드 설정
        //           simple_list_item_checked일때는 CHOICE_MODE_SINGLE나 CHOICE_MODE_MULTIPLE모드 두개 다 가능
        //즉 CHOICE_MODE_SINGLE는 하나만 체크되고 CHOICE_MODE_MULTIPLE모드일때는 여러개 체크 가능
        //[simple_list_item_checked]

        //listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

    }//////////onCreate


    public void click(View view){
        int choice_mode = listView.getChoiceMode();
        if(choice_mode==ListView.CHOICE_MODE_NONE)
            Toast.makeText(this, selectedItemNone,0).show();
        else if(choice_mode == ListView.CHOICE_MODE_SINGLE)
            Toast.makeText(this, selectedItemSingle,0).show();
        else if(choice_mode== ListView.CHOICE_MODE_MULTIPLE){
            //SparseBooleanArray 미사용
            //Toast.makeText(this, selectedItems.toString(),0).show();

            //희소배열    {키=값} 형태로 출력된다.   - 선택한게 key , 선택여부가 value
            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            //Toast.makeText(this,"sparseBooleanArray: "+sparseBooleanArray,0).show();

            StringBuffer buf = new StringBuffer();
            for(int i=0;i<sparseBooleanArray.size();i++){
                int position = sparseBooleanArray.keyAt(i);
                if(sparseBooleanArray.valueAt(i))
                    buf.append(listView.getItemAtPosition(position).toString()+" ");
            }
            Toast.makeText(this,"sparseBooleanArray: "+sparseBooleanArray,0).show();

        }
    }//////////////////click


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //position:클릭한 아이템의 인덱스 값(헤더는 0 푸터는 배열의 length혹은 length+1(헤더포함시))
        //id:실제 데이타 아이템의 인덱스값(0부터 시작:헤더나 푸터는 -1)
        //오류 고치기
        if(id == -1) return;//헤더나 푸터클릭한 경우
        Toast.makeText(this,String.format(
                "position: %s, id: %s, %s 선택", position,id,items.get((int)id)
        ),0).show();
        //클릭한 아이템의 텍스트 얻기(컬렉션 인스턴스 사용하지 않고 두번째 인자인 View 사용)
//        Toast.makeText(this,String.format(
//                "두번째 인자 View사용 %s 선택", ((TextView)v).getText()
//        ),0).show();
        //ListView 인스턴스로 목록과 관련된 정보 얻기
//        Toast.makeText(this,String.format(
//                "아이템의 총수: %s, 헤더 수: %s, 푸터 수: %s",
//                listView.getCount(),listView.getHeaderViewsCount(),listView.getFooterViewsCount()
//                ),0).show();

        /*
         ※특정 인덱스의 아이템 가져올때는
          getItemAtPosition(int position)메소드 사용
         배열명[인덱스] 비 추천
         왜냐하면
         리스트뷰에 헤더나 푸터 추가시 헤더도 아이템에 포함됨.
         모드가 single인 경우:getCheckedItemPosition()으로 체크된 아이템
               multiple인 경우:getCheckedItemPositions()으로 체크된 아이템을 얻는다
               그리고 getItemAtPosition(int position)메소드로 아이템의 텍스트를 얻는다
         ※isItemChecked(int position) : 반드시 인자로 받은
                                         position 을 넣어야한다
                                        isItemChecked()메소드는 헤더나
                                              푸터를 아이템으로 인식한다

         */

        int choice_mode = listView.getChoiceMode();
        if(choice_mode==ListView.CHOICE_MODE_NONE){
            selectedItemNone = listView.getItemAtPosition(position).toString();
        }
        else if(choice_mode == ListView.CHOICE_MODE_SINGLE){
            selectedItemSingle = listView.getItemAtPosition(position).toString();
        }
        else if(choice_mode== ListView.CHOICE_MODE_MULTIPLE){
            if(listView.isItemChecked(position))//체크한 경우
                selectedItems.add(listView.getItemAtPosition(position).toString());
            else //체크 해제한 경우
                selectedItems.remove(listView.getItemAtPosition(position).toString());
        }


    }/////////////onListItemClick


}