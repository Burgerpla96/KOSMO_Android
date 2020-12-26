package com.kosmo.sqlite30_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

//※SQLiteOpenHelper클래스를 상속받아서 생성자와 onCreate()
//메소드로에서 데이타베이스와 테이블을 생성하지 않고
//SQLiteDatabase클래스의 openOrCreateDatabase()메소드로
//데이타베이스 생성하기]
public class MainActivity extends AppCompatActivity {

    //데이터 베이스 생성용
    private SQLiteDatabase sqLiteDatabase;
    //select쿼리 결과 저장용
    private Cursor cursor;

    private AlertDialog alertDialog;
    private EditText editDatabase,editTable,editInsert;
    private TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get widget
        editDatabase = findViewById(R.id.editdatabase);
        editTable = findViewById(R.id.edittable);
        editInsert = findViewById(R.id.editinsert);
        tvResult = findViewById(R.id.tvresult);

        //dialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //대화상자 설정
        builder.setCancelable(false).setTitle("데이터 베이스")
                .setPositiveButton("확인",null);
        alertDialog = builder.create();

    }///////////////onCreate


    public void createDatabase(View view){
        //1. 데이터 베이스명 입력 여부 판단
        if(editDatabase.getText().toString().trim().length()==0){
            showMessage("데이터 베이스명을 입력하세요.");
            return;
        }

        //SQLiteDatabase openOrCreateDatabase(String 데이타베이스명,int 모드,Cursorfactory)
        //Cursorfactory는 null지정
        //※데이타 베이스는 /data/data/패키지명/databases에 생성됨.

        //2. 데이터 베이스 존재여부 판단
        File file = new File("/data/data/"+getPackageName()
                +"/databases/"+editDatabase.getText().toString().trim());
        if(file.exists()){
            showMessage("이미 존재하는 데이터베이스 이름입니다.");
            return;
        }
        //3. DB 생성
        sqLiteDatabase = openOrCreateDatabase(
                editDatabase.getText().toString().trim(),MODE_PRIVATE,null);

        //Pie 버전부터는 아래코드 추가해야 실행된다.
        sqLiteDatabase.disableWriteAheadLogging();

        Toast.makeText(this,"데이터 베이스가 생성되었어요",Toast.LENGTH_SHORT).show();
        //입력 상자 비활성화
        editDatabase.setEnabled(false);
        //버튼 비활성화
        view.setEnabled(false);
    }/////////////////////createDatabase



    public void createTable(View view){
        //1. 데이터 베이스의 생성확인
        if(sqLiteDatabase==null){
            showMessage("먼저 데이터베이스를 생성하세요");
            return;
        }
        //2. table 명 입력여부 판단
        if(editTable.getText().toString().trim().length()==0){
            showMessage("테이블 명을 입력하세요.");
            return;
        }
        //3. 기존 테이블 존재시 테이블 삭제
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+editTable.getText().toString().trim());
        //4. 테이블 생성
        sqLiteDatabase.execSQL("CREATE TABLE "+editTable.getText().toString().trim()
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");

        Toast.makeText(this,"테이블이 생성되었어요",Toast.LENGTH_SHORT).show();

        //5. 입력상자, 버튼 비활성화
        editTable.setEnabled(false);
        view.setEnabled(false);
    }//////////////////createTable



    public void insert(View view){
        //1. table 생성 확인 - 비활성화 여부로
        if(editTable.isEnabled()){
            showMessage("먼저 테이블을 생성하세요.");
            return;
        }
        //2. 이름 입력여부 판단
        if(editInsert.getText().toString().trim().equals("")){
            showMessage("이름을 입력하세요.");
            return;
        }
        //3. 데이터 입력
        //SQLiteDatabase 객체의 execSQL() 메서드로 INSERT/DELETE/UPDATE
        sqLiteDatabase.execSQL("INSERT INTO "+editTable.getText().toString().trim()
                +"(name) VALUES('"+editInsert.getText().toString().trim()+"')");
        //입력창 클리어
        editInsert.setText("");
        //포커스 주기
        editInsert.requestFocus();
    }//////////////insert


    public void select(View view){
        if(sqLiteDatabase != null){
            //SQLiteDatabase의 query() 혹은 rawquery() 로 데이터 조회
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+editTable.getText().toString().trim(),null);
            //기존값 없애기
            tvResult.setText("");
            while(cursor.moveToNext()){
                //Cursor의 getXXX() method로 데이터 꺼내오기  //인덱스는 0부터 시작 (자바는 1부터)
                int _id = cursor.getInt(0);
                String name = cursor.getString(1);
                tvResult.append(String.format("번호: %s, 이름: %s%n",_id,name));
            }
        }



    }//////////////select



    //dialog 메세지 출력용
    private void showMessage(String message){
        alertDialog.setMessage(message);
        alertDialog.show();
    }////////////////showMessage


    //데이터 베이스 자원 반납
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null) cursor.close();
        if (sqLiteDatabase != null) sqLiteDatabase.close();
    }//////////////onDestory


}////////////class