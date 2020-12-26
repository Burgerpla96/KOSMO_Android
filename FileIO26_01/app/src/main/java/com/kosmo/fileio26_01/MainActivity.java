package com.kosmo.fileio26_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext);
    }//////onCreate

    //버튼 클릭시 레이아웃용 xml의 onClick속성에 지정한 메소드 정의]
    //※단, 버튼에만 사용가능
    /*
    접근 지정자:public
    반환타입:void
    메소드명:onClick속성에 지정한 메소드명
    매개변수:View타입*/

    ////////////////////////////////////////////////////////////////////////file 읽고 쓰기
    public void fileWriterInMemory(View view){
        //     /data/data/패키지/files 디렉토리에 파일쓰기 openFileOutput() 이용
        //파일 미 존재시 생성됨(/data/data/패키지명/files/indata.txt)

        //openFileOutput() 의 두번째 인자, 파일 저장 mode
        //MODE_PRIVATE: 기존 내용이 있는 경우 삭제된다.
        //MODE_APPEND: 기존 파일에 내용이 추가된다.

        //확장자는 안써도 된다.
        try{
            FileOutputStream fos = openFileOutput("Memory.txt",MODE_PRIVATE);
            fos.write(editText.getText().toString().getBytes());
            fos.close();
            //1]File getDir("mydir",Activity.MODE_PRIVATE);라고 설정시
            // /data/data/패키지명/app_mydir라는 디렉토리가 생성됨
            //내장메모리에 임의 디렉토리 생성
            File file = getDir("mydir",MODE_PRIVATE);
            //내가 만든 폴더에 새로운 파일 쓰기
            Toast.makeText(this, file.getAbsolutePath(),1).show();
            fos = new FileOutputStream(file.getAbsoluteFile()+File.separator+"Memory.txt");
            fos.write("java IO로 파일 출력".getBytes());
            fos.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }/////////////////fileWriterInMemory


    public void fileReaderInMemory(View view){
        // /data/data/패키지/files 디렉토리안에 파일 읽어오기
        try{
            FileInputStream fis = openFileInput("Memory.txt");
            byte[] bytes = new byte[fis.available()];
            //fis.available() 파일의 크기를 가져온다.
            fis.read(bytes);
            editText.setText(new String(bytes));
            fis.close();

            //내장 메모리의 임의의 디렉토리에 저장된 파일 읽기
            File file = new File("/data/data/"+getPackageName()+"/app_mydir/Memory.txt");
            fis = new FileInputStream(file);
            bytes = new byte[fis.available()];
            fis.read(bytes);
            editText.append("\r\n"+new String(bytes));
            fis.close();

        } catch (Exception e){e.printStackTrace();}


    }/////////////////////fileReaderInMemory



    public void fileReaderInRaw(View view){///////////RES-RAW-읽기전용
        //res폴더 우클릭->new->Android resources Directory로 raw폴더 생성
        //※Resource Type을 raw선택
        //생성된 raw폴더에 파일 저장시는 DDMS에서 가 아닌
        //탐색기에서 직접 파일을 COPY & PASTE
        //파일 저장시 UTF-8로 저장(한글이 깨지지 않는다.)
        //혹은 raw폴더 우클릭->new->File로 직접 안드로이드에서 생성
        //Resources객체의 openRawResource(리소스아이디)메소드로 읽는다

        try {
            InputStream is = getResources().openRawResource(R.raw.readonly);
            //raw 오류는  File -> Invalidate Caches/Restart 하면 해결된다.
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            editText.setText(new String(bytes));
        } catch(Exception e){e.printStackTrace();}

    }////////////////////fileReaderInRaw






    //////////////////////////////////////////////////////////////////////SD(외부저장소)로 저장

    /*
    몰라도 된다.
  아래 두 메소드(fileWriterInSD,fileReaderInSD) 사용시에는 매니페스트 파일에 아래권한 추가
  단,킷켓버전 이후부터 제공된
  getExternalFilesDir()메소드  나
  getExternalFilesDirs()메소드사용시
  아래 권한 설정 불필요
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
          */
    public void fileWriterInSD(View view){
         /*
        //저장할 파일 유형:미리 정의된 디렉터리 이름으로 시스템에서 파일이 올바르게 처리되도록 한다
        Environment.DIRECTORY_ALARMS:알람으로 사용할 오디오파일
        Environment.DIRECTORY_DCIM:카메라로 촬영한 사진
        Environment.DIRECTORY_DOWNLOADS:다운로드한 파일
        Environment.DIRECTORY_MUSIC:오디오파일
        Environment.DIRECTORY_MOVIES:동영상 파일
        Environment.DIRECTORY_PICTURES:이미지 파일
        Environment.DIRECTORY_RINGTONES:벨소리용 오디오파일
        Environment.DIRECTORY_NOTIFICATIONS:알림음으로 사용할 오디오파일
        null:미리 정의된 하위 디렉터리 이름 중 파일에 알맞은 이름이 없을 경우
        */
        File file = getExternalFilesDir(null);
        //Environment.DIRECTORY_DCIM 지정시 경로
        //   /storage/emulated/0/Android/data/com.kosmo.fileio26_01/files/DCIM
        // null 지정시 경로
        //   /storage/emulated/0/Android/data/com.kosmo.fileio26_01/files
        Log.i("com.kosmo.fileio",file.getAbsolutePath());
        //new FileWriter(file.getAbsolutePath());
        try{
            //다시
            FileWriter fw =  new FileWriter(file.getAbsolutePath()+File.separator+"SDFile.txt");
            fw.write("안녕하세요\r\n안드로이드 수업입니다.");
            fw.close();
        } catch (Exception e){e.printStackTrace();}

    }/////////////////////fileWriterInSD

    public void fileReaderInSD(View view){
        try {
            FileReader fr = new FileReader(getExternalFilesDir(null)
                    .getAbsolutePath() + File.separator + "SDFile.txt");
            BufferedReader br = new BufferedReader(fr);
            String data;
            while ((data = br.readLine()) != null) {
                editText.append(data + "\r\n");
            }
            br.close();
        }catch (Exception e){e.printStackTrace();}
    }/////////////fileReaderInSD




    ///////////////////////////////////////////////////////////////////////////directory 생성
    public void dirMakeInMemory(View view){//   /data/data/패키지/
      /*
        app_가  디렉토리명에 추가 되지 않는다.
        단, getDir()로 내장 메모리에 디렉토리 생성시에는
        app_가 디렉토리명 앞에 붙는다.
         */
        //File객체로 내장 메모리에 디렉토리 만들기
        File file = new File("/data/data/"+getPackageName()+"/MYDIR");
        if(!file.exists()) file.mkdir();
    }///////////////////dirMakeInMemory

    public void dirMakeInSD(View view){//SD 카드에 만든 것은 확인이 불가
        File file = new File(getExternalFilesDir(null).getAbsolutePath()+File.separator+"/MYDIR");
        if(!file.exists()) file.mkdir();
    }////////////////////////dirMakeInSD





}//////////class