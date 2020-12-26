package com.kosmo.intent24_02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //요청코드 -- 식별자로 사용한다.
    //요청코드는 반드시 0이상인 값으로 설정(음수값 안된다.)
    // 그렇지 않으면 onActivityResult() 가 호출이 안된다. 즉 다시 데이터를 받을 수 없다.

    private static final int REQUEST_CODE_IS_MEMBER = 0;
    private static final int REQUEST_CODE_TEST = 1000;

    private EditText editUser,editPass;
    private TextView textViewMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get widget
        Button btnStartActivity = findViewById(R.id.btnStartActivity);
        Button btnStartActivityforResult = findViewById(R.id.btnStartActivityforResult);
        Button btnStartActivityforResult2 = findViewById(R.id.btnStartActivityforResult2);
        editPass = findViewById(R.id.editpass);
        editUser = findViewById(R.id.edituser);
        textViewMain = findViewById(R.id.textviewMain);
        //attach listener
        btnStartActivity.setOnClickListener(handler);
        btnStartActivityforResult.setOnClickListener(handler);
        btnStartActivityforResult2.setOnClickListener(handler);

    }////////////onCreate

    //eventHandler
    private View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //사용자값 받기
            String user = editUser.getText().toString();
            String pass = editPass.getText().toString();
            //사용자 입력값 받기
            Intent intent = new Intent(v.getContext(),StartActivity.class);
            //아이디와 비번 저장
            intent.putExtra("user",user);
            intent.putExtra("pass",pass);
            if(v.getId() == R.id.btnStartActivity){
                startActivity(intent);
                //startActivity 는 코드 없고
                //startActivityForResult 는 코드있어 intent다시 받을 때 편리
            }
            else if(v.getId() == R.id.btnStartActivityforResult){
                //데이터 전달 및 결과값을 다시 받기
                /*
                1]startActivityForResult()메소드 로 인텐트 전달
                2]onActivityResult()메소드 오버라이딩
                 */
                //startActivityForResult(Intent,int 요청코드)
                /*
                    Intent :전달할 Intent 객체
                    int  요청코드:인텐트를 받을 액티비티에서 식별자로 사용하거나
                                  혹은 인텐트를 전달한 액티비티에서 식별자로 사용
                 */
                //전환할 클래스변경
                intent.setClass(v.getContext(),ForResultActivity.class);
                startActivityForResult(intent,REQUEST_CODE_IS_MEMBER);
            }
            else{
                intent.setClass(v.getContext(),ForResultActivity2.class);
                startActivityForResult(intent,REQUEST_CODE_TEST);

            }

        }
    };/////////////////////////handler



    //인텐트 보낸후 다시 인텐트를 받기 위해  onActivityResult() 오버라이딩
    //즉 인텐트를 전달받은 액티비티에서 setResult()메소드 호출시
    //아래 메소드가 자동으로 호출된다.
    /*
    //onActivityResult의 매개 변수
    requestCode:내가 보낸 인텐트 확인용(요청코드)
    resultCode:인텐트를 받은 액티비티에서 보낸 코드
    data:인텐트를 받은 액티비티에서 보낸 인텐트
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IS_MEMBER){
            switch (resultCode){
                case ForResultActivity.RESULT_CODE_ID_FAIL:
                    textViewMain.setText(data.getStringExtra("IDFAIL"));
                    break;
                case ForResultActivity.RESULT_CODE_PWD_FAIL:
                    textViewMain.setText(data.getStringExtra("PWDFAIL"));
                    break;
                default:
                    textViewMain.setText(data.getStringExtra("SUCCESS"));
            }
        }
        else if(requestCode == REQUEST_CODE_TEST){
            textViewMain.setText(data.getStringExtra("TEST"));
        }
    }////////////////onActivityResult




}////class