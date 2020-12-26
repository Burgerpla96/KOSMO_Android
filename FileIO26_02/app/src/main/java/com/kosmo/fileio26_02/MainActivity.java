package com.kosmo.fileio26_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    //메모기 저장용
    private Map memory = new HashMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("shared",MODE_PRIVATE);
    }/////////////onCreate





    /////////////////////////////////////////////Memory에 저장
    //앱을 끄면 데이터가 사라진다. //onDestroy() 시 삭제된다.
    public void saveInMemory(View view){
        //메모리(컬렉션)에 데이터 저장
        memory.put("map","메모리에 데이터 저장");
    }////////////saveInMemory



    public void readInMemory(View view){
        if(memory.get("map") == null){
            Toast.makeText(this,"컬렉션에 저장된 데이터가 없어요",0).show();
            return;
        }
        Toast.makeText(this, memory.get("map").toString(),0).show();
    }////////////readInMemory






    //////////////////////////////////////////////File 에 저장
    public void saveInFile(View view){
        //내장 메모리에 파일 생성후 데이터 쓰기
        //위치: /data/data/패키지명/files
        try {
            FileOutputStream fos = openFileOutput("inner.dat",MODE_PRIVATE);
            fos.write(("내장 메모리에 저장한 데이터\r\n앱삭제나 데이터 삭제시에만 삭제된다. " +
                    "다른 앱에서는 접근 불가.").getBytes());
            fos.close();
        } catch (Exception e){e.printStackTrace();}

    }////////////saveInFile



    public void readInFile(View view){
        //내장 메모리에서 데이터 읽어오기
        try{
            FileInputStream fis = openFileInput("inner.dat");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            Toast.makeText(this, new String(bytes),0).show();
        } catch (Exception e){e.printStackTrace();}
    }////////////readInFile





    ///////////////////////////////////////////////Shared에 저장
    public void saveInShared(View view){
        //파일 IO 생성 안하고 내장 메모리의 shared_prefs 폴더에 shared.xml 파일이 생성된다.
        //파일 위치:   /data/data/패키지명/shared_prefs
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SHARED","shared.xml에 저장한 데이터");
        editor.commit();
    }////////////saveInShared



    public void readInShared(View view){
        //읽기
        Toast.makeText(this, preferences.getString("SHARED","키가 없어요"),0).show();
    }////////////readInShared




}