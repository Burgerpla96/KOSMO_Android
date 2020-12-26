package com.kosmo.listview27_03;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

//1) ListActivity가 아닌 AppCompatActivity 상속
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ListView listView;
    private ArrayAdapter adapter;
    //데이터는 arrays.xml이나 컬렉션 사용
    //아이템을 동적으로 추가 및 삭제시 사용
    List items = new Vector();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get widget
        editText = findViewById(R.id.edittext);
        listView = findViewById(R.id.listview);

        //adapter 생성
        //방법1. ArrayAdapter.createFromResource(context,자원아이디,레이아웃 아이디);
        //single
        //adapter = ArrayAdapter.createFromResource(this,R.array.games,android.R.layout.simple_list_item_single_choice);
        //multi
        //adapter = ArrayAdapter.createFromResource(this,R.array.games,android.R.layout.simple_list_item_multiple_choice);
        //방법2. ArrayAdapter 생성자 이용
        //adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,getResources().getStringArray(R.array.games));

        //컬렉션 사용
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice, items );



        //어뎁터와 리스트뷰 연결, 모드 선택
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);



        //아이템 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //single mode
                //Toast.makeText(parent.getContext(), listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                //multiple mode
                if(listView.isItemChecked(position)){
                    Toast.makeText(parent.getContext(), listView.getItemAtPosition(position).toString()+ " 선택", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(parent.getContext(), listView.getItemAtPosition(position).toString()+ " 해제", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }///////////////onCreate


    //버튼에 이벤트용 콜벡 메서드들
    public void read(View view){//체크된 아이템을 토스트로 출력
        if(listView.getCount()==0) return;
        //getCount(): 리스트뷰의 연결된 모든 아이템의 총수 반환
        StringBuffer buf  = new StringBuffer();
        for(int i=0; i<listView.getCount();i++){
            if(listView.isItemChecked(i)){
                buf.append(listView.getItemAtPosition(i)+"\r\n");
            }
            Toast.makeText(this,buf,Toast.LENGTH_SHORT).show();
        }


    }///////////////////read



    public void add(View view){
        if(editText.getText().length()==0) {
            Toast toast = Toast.makeText(this, "추가할 아이템을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        //1)입력 문자열 추가
        items.add(editText.getText().toString());
        //2) textEdit 클리어
        editText.setText("");
        //3) 어뎁터에세 데이터 변화 통지
        adapter.notifyDataSetChanged();

    }//////////////////add



    public void remove(View view){
        if(listView.getCheckedItemCount()==0){
            Toast.makeText(this,"삭제할 아이템을 먼저 선택하세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("삭제 확인")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제처리
                        checkedItemsRemove();
                    }
                })
                .setNegativeButton("아니오", null).show();

    }/////////////////////remove


    //사제 로직
    private void checkedItemsRemove(){
        //리스트 계열 컬렉션은 리인덱싱이 발생해서
        //outOfBound 오류 발생 할 수 있어 뒤에서 부터 삭제한다.
        for(int i=items.size() -1 ; i>=0;i--){
            if(listView.isItemChecked(i)){
                items.remove(i);
            }
        }
        //삭제한 후, 체크된 상태 유지없애기
        listView.clearChoices();
        //어뎁터에게 데이터 변경 통지
        adapter.notifyDataSetChanged();

    }//////////////////////////checkedItemsRemove



}//////////////class