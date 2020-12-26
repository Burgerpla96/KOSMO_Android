package com.kosmo.sqlite30_02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

//1. SQLiteOpenHelper 상속
//a) 생성자 정의
//b) onCreate 오버라이딩
//c) onUpgrade() 오버라이딩

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String databaseName, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //context : Activity 등의 Context 인스턴스
        //databaseName: DB 이름
        //factory: 보통 null 지정
        //version: DB 스키마 버젼
        super(context, databaseName, factory, version);
        Log.i(MainActivity.TAG, "MySQLiteOpenHelper의 생성자");

    }///////////////생성자



    //테이블 생성
    //SQLiteDatabase의 getReadableDatabase(), getWritableDatabase() 호출시 한번만 실행
    @Override
    public void onCreate(SQLiteDatabase db) {
        //같은 이름의 테이블 존재하면 삭제
        db.execSQL("DROP TABLE IF EXISTS "+MainActivity.TABLE_NAME);
        //※SimpleCursorAdpapter사용시 반드시 _id컬럼이 존재해야됨.
        //※autoincrement 설정시 그 컬럼은 반드시 primary key로 설정해야 한다.
        db.execSQL(String.format(
                "CREATE TABLE %s(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "user TEXT, name TEXT, age INTEGER DEFAULT 0, regidate DATETIME)",MainActivity.TABLE_NAME));
        Log.i(MainActivity.TAG, "MySQLiteOpenHelper의 onCreate()");

    }///////////////onCreate


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //버젼 업그레이드시
        //새롭게 테이블 생성 구문 실행
        if(oldVersion < newVersion){//버젼 업그레이드 되었을 때
            onCreate(db);
        }
        Log.i(MainActivity.TAG, "MySQLiteOpenHelper의 onUpgrade()");
    }//////////////onUpgrade


    //Pie 버젼 이후 부터는 아래코드 추가
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
        Log.i(MainActivity.TAG, "MySQLiteOpenHelper의 onOpen()");
    }/////////////////////onOpen




}////////class
