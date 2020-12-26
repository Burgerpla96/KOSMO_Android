package com.kosmo.activity23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

/*
onCreate():
  액티비티가 처음 생성될 때 호출된다.
  시스템에 의해 호출되면 인자로 받은 Bundle은 null
  액티비티가 실행된 적이 있는데, 어떤 이유로 종료된 후 자동으로 재시작되면,
  종료될 때 호출된 onSaveInstanceState()에서 저장한 내용과 동일한 Bundle을 넘겨준다.
  예]디바이스가 회전되어 가로/세로 전환 등 리소스를 새롭게 갱신되어야 할 때 호출

onStart():
   액티비티가 생성된 후, 화면의 전면으로 나타나기 시작할때 호출된다
  동일 앱안에서 화면전환시나 다른 앱으로으로 전환시
  onStop()상태(백그라운드)로 갔다가 다시 전면으로 나올때도
 호출된다

onRestart():
  액티비티가 onStop()상태되었다가 다시 실행될 때 호출된다
onStop():
  HOME버튼을 눌렀을 때와 동일 앱안에서 화면전환시나
  다른 앱으로으로 전환시 호출된다
  즉 액티비티가 백그라운드에 위치하게 된다

onResume():
  액티비티가 전면에 나타날 때 즉 사용자와 상호작용하기 직전상태일때 호출된다.
onPause():
  onStop(), onDestroy()가 호출되기 이전에 호출된다.
  액티비티가 화면에서 가려지는 경우에 호출된다.

  ※일반적으로 onResume()에서 했던 작업을 onPause()에서 정리한다
    예]onResume()에서 쓰레드를 실행시켰으면,
       onPause()가 호출될 때, 아직 쓰레드가 실행중이면 쓰레드를 중지시킨다
       onPause()가 호출되서 앱(또는 액티비티)이 일시 정지된 상태라면
       안드로이드 시스템에서 필요에 따라 완전이 죽일 수 있기 때문에
       그 이후의 작업을 못할 수도 있다는 점을 유의해야한다.

onSaveInstanceState():
  onPause()이후에 호출된다 즉
  액티비티가 전면에서 백그라운드로 숨는 경우 호출된다.
  현재의 액티비티 상태를 저장하려면 이 메서드를 구현한다.
  예]액티비티 전환시나 앱 전환시 혹은 home버튼 클릭시
    즉 onPause()부터 onStop()상태로 이를때나(정상적인 상태)
    onPause()로 가서 메모리 부족으로 인해 안드로이드 시스템에
    의해 액티비티가 강제 종료시에 호출됨
onRestoreInstanceState():
  onSaveInstanceState()에 의해서 저장된 상태가 잇을때 호출된다
  onSaveInstanceState() 함수에서 저장했던 내용은 onCreate()에서
  Bundle 인스턴스로 넘겨받는데, onRestoreInstanceState()에서도 같은 내용을
  받을 수 있다.
  강제 종료(안드로이드 시스템에 의한 종료)후 복귀시 호출됨.
  단, 정상적인 종료시에는 호출안된다.
  즉 finish()메소드(정상종료)로로 액티비티를 종료하거나
     back 버튼(정상종료에 해당) 클릭으로 인한 앱 종료시에는 호출 안됨

onDestroy():
  액티비티가 종료되기 전 호출된다.
  액티비티내부에서 finish()를 실행하면 호출된다.
  시스템 메모리가 부족하면, 안드로이드가 강제로 죽일 때도 호출이 되는데,
  메모리 확보가 매우 시급할 때는 호출조차 되지 않는 경우도 있다.

※onPuase()이후를 killable상태라 한다. 즉 언제든지
종료될수 있다는 의미(정상적이든 강제 종료(메모리 부족에 의한)든)
*/


//View 만들기
//1) extends AppCompatActivity
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "com.kosmo.activity";
    //데이터 복구 테스트용
    private ToggleButton toggleButton;
    //앱에서 간단하게 데이터 저장용- 파일(.xml)형태로 저장된다.
    private SharedPreferences preferences;

       /*
    onCreate()의 매개변수인 savedInstanceState는
    강제종료후에 다시 앱이 재시작시에만
    안드로이드 시스템이 Bundle 객체를 생성해서 매개변수로 전달해 준다
    즉 정상종료후 다시 앱을 실행할때는 Bundle 객체는 null 이다

     */





    //2) 인자가 하나인 onCreate Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Map과 유사한 Bundle - 액티비티의 상태가 저장되어있다.
        super.onCreate(savedInstanceState);
        //3) setContentView(Layout 아이디) 사용해서 전개하기
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity의 onCreate() invoked");
        Log.i(TAG,"onCreate()의 savedInstanceState:"+savedInstanceState);

        //위젯에 리스너 부착
        findViewById(R.id.btnActivityChange).setOnClickListener(this);
        findViewById(R.id.btnAppChange).setOnClickListener(this);
        findViewById(R.id.btnDataDelete).setOnClickListener(this);
        findViewById(R.id.btnDataRead).setOnClickListener(this);
        findViewById(R.id.btnDataSave).setOnClickListener(this);
        findViewById(R.id.btnDestroy).setOnClickListener(this);
        findViewById(R.id.btnModeChange).setOnClickListener(this);

        //null check
        if(savedInstanceState != null){
            Log.i(TAG,"MainActivity의 onCreate() invoked : key는 "+savedInstanceState.getString("key"));
        }
        //SharedPreferences 객체 얻기
        preferences = getSharedPreferences("appData",MODE_PRIVATE);


    }///////////////onCreate

    //2)
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"MainActivity의 onStart() invoked");

    }///////////onStart

    //3)
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"MainActivity의 onResume() invoked");
    }///////////onResume


    //4) 다른 액티비티나 앱으로 전환시 이후
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"MainActivity의 onPause() invoked");
    }///////////////onPause


    //5)
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"MainActivity의 onStop() invoked");
    }///////////////onStop


    //5-1) onStop 상태(백그라운드 상태) 다시 액티비티를 실행시킬때
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"MainActivity의 onRestart() invoked");
    }


    //6)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"MainActivity의 onDestroy() invoked");
    }///////////onDestroy


    /*※액티비티 라이프 사이클에 속하지는 않지만
        액티비티 생명주기와 관련된 메소드
        화면전환 전후 상태를 저장하는데  사용되는 콜백 메소드]
        onPause()다음에 호출되는 콜백 메소드]
     -호출되는 경우
        1.현재 액티비티가 onStop()으로 갈때(onStop이후 onDestroy까지 갈때 제외)
        예]다른 화면(액티비티)이나 앱으로 전환시
           혹은 HOME버튼 클릭시(즉 onStop까지만 간다)
        2.강제종료되는 경우(onDestroy까지 갈수도 있다):

     -호출 안되는 경우
        정상적으로 onDestroy까지 갈때
        예]finish()호출시
           혹은
           BACK버튼 클릭시
    ※반드시 매개변수 하나짜리 오버라이딩.*/


    //생명주기에 속하지 않은 콜백 메서드
    //정상 종료시 호출 안된다.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"MainActivity의 onSaveInstanceState() invoked");
        //※View의 상태(UI상태)값 저장 불필요.OS가 알아서 복원시키니까.
        //우리는 데이타 저장]
        outState.putString("key","복원용 데이터");

    }///////////onSaveInstanceState

/*onResume()전에 호출되는 콜백 메소드]
        -복구할 데이타가 있을때 오버라이딩 즉 호출된다
         -호출되는 경우
           강제종료후 앱을 다시 시작할때
           예] 디바이스의 화면회전(가로모드/세로모드)이 발생할 때나,
              프로세스 킬러 앱을 이용하여 강제종료 후 재시작 할 때 발생.
         -호출안되는 경우
          정상종류후 앱 다시 시작하거나
          현재 액티비티가 onStop()으로 간후 앱 다시 시작할때
            */

 /*
    뷰의 상태를 복원하고자 한다면 굳이
    아래 메소드를 오버라이딩 할 필요없다.
    안드로이드 OS에서 강제종료후 알아서 이전상태로 복원시켜준다.
    즉 onSaveInstanceState에서 저장한 데이타는
    onCreate메소드의 인자인 savedInstanceState를 널 체크한후
    복원하자
    */

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //데이터 복원위해서 null체크 불필요 Bundle 생성해준다.(@NonNull 도 있고)
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"MainActivity의 onRestoreInstanceState() invoked");
        Log.i(TAG,"onRestoreInstanceState()의 savedInstanceState:"+savedInstanceState);
        Log.i(TAG,"onRestoreInstanceState()의 savedInstanceState : key는 "+savedInstanceState.getString("key"));
    }////////////onRestoreInstanceState


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnAppChange){
            //다른 앱으로 전환
            //stop()까지 거쳐 save 메서드가 호출된다.

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: 010-1234-5678"));
            startActivity(intent);
        }
        else if(v.getId() ==R.id.btnActivityChange){
            startActivity(new Intent(this,AnotherActivity.class));
        } else if(v.getId() ==R.id.btnModeChange){
            //화면 모드 변경]-   setRequestedorientation(ActivityInfo클래스 상수)
            //현재 화면 모드 얻기:getRequestedorientation();
            //onPause()->onSaveInstanceState(Bundle outState)->onStop()->onDestroy()
            //->onCreate(Bundle savedInstanceState)->onStart()-> onRestoreInstanceState(Bundle savedInstanceState)
            //->onResume()
             /*
                getRequestedOrientation()는 화면모드 미 설정시 -1반환
                -1:화면 모드 미설정.화면모드는 디폴트가 세로모드(원래 값이 1임임)
            */
            int mode = getRequestedOrientation();
            Log.i(TAG,String.format("화면모드: %s",mode));
            if(mode == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED || mode == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            else
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        else if (v.getId() ==R.id.btnDestroy){
            finish();
        }
        //파일은DDMS의 File Explorer탭에서
        // /data/data/패키지/shred_prefs/이름.xml로 확인 가능
        //https://developer.android.com/studio/profile/monitor?hl=ko
          /*
        보안상의 이유로 DDMS에서는 확인 불가
        생성된 파일 확인 방법-
        1.SDK가 설치된 디렉토리로 이동
        예]C:\Users\kosmo_0\AppData\Local\Android\Sdk>
        2.adb.exe가 설치된 폴더로 이동
        예]C:\Users\kosmo_0\AppData\Local\Android\Sdk\platform-tools>
        3.adb shell명령어로 shell 로 접속
        예]C:\Users\kosmo_0\AppData\Local\Android\Sdk\platform-tools>adb shell

        4.현재 앱의 패지키로 실행행
         run-as 패키지명
         예]$ run-as com.kosmo.activity23
        5. shared_prefs디렉토리가 보임 이후부터는 리눅스 명령어로 확인 가능

        혹은  View - Tool Windows - Device File Explorer
        혹은 안드로이드 스튜디오 오른쪽 하단의 Device File Explorer서 확인
         */
        else if(v.getId()==R.id.btnDataSave){
            //별도 DB없이 파일로 앱에 간단한 데이터 저장하기
            SharedPreferences.Editor editor = preferences.edit();
            //putXXX("키값",데이터) 로 저장
            editor.putString("mykey","코코코 코스모");
            //commit으로 반영
            editor.commit();
        }
        else if(v.getId() == R.id.btnDataRead){
            //SharedPreferences 값 읽기
            String app_name = preferences.getString("app_name","키가 존재하지 않을 때 기본값");
            Toast.makeText(this,app_name,0).show();
            Toast.makeText(this,preferences.getString("mykey",null),0).show();
        }
        else{
            //데이타 삭제
            // SharedPreferences.Editor객체의 remove("키값")으로 삭제
            //삭제후 commit()호출해야 반영됨.
            //sharedPreferences.edit()로 SharedPreferences.Editor얻기
            //preferences.edit()로 SharedPreferences.Editor얻기
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("app_name");
            editor.commit();
        }


    }////////////onClick
}///////////class