package com.kosmo.notification22_01;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
   findViewById()메소드 자동화 플러그인 설치:
   File - Settings-Plugin-findViewByMe로 검색후 install버튼 클릭

   setContentView의 activity_main텍스트 위에 마우스 우 클릭후-Generate-FindViewByMe클릭하면
   코드가 자동완성됨
    */
public class MainActivity extends AppCompatActivity {
    private TextView textview;
    private Button btnAlertBasic;
    private Button btnAlertItems;
    private Button btnAlertRadio;
    private Button btnAlertCheck;
    private Button btnProgress;
    private Button btnCustom;
   /*
   체크박스형태나 목록형 그리고 라디오버튼형으로
   대화상자를 띄울때는 setMessage()대신
   목록형:setItems()
   체크박스형:setMultiChoiceItems()
   라디오버튼형:setSingleChoiceItems()메소드 사용
   setMessage()동시 사용시 setMessage()가 적용됨
   */

    private String[] sports= {"축구","배구","농구","야구"};
    //라디오 버튼 형태에서 선택한 스포츠 종목의 인덱스 저장용
    private int which_radio = -1;
    private boolean[] which_checks = {true,false,true,false};
    //진행 대화상자
    //private ProgressDialog progressDialog;

    //프로그래스 대화상자 관련 변수
    private AlertDialog progressDialog;

    //진행 대화상자 몇 초후 닫기
    //방법 1. CountDownTimer 객체 사용
    private CountDownTimer timer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //widget 얻기
        initView();

        //진행 대화상자 몇 초후 닫기 1
        timer = new CountDownTimer(3000,1000) {//추상 클래스 구현
            //3초(첫 번째 인자)후 onFinish() 실행
            //1초(두 번째 인자)마다 onTick() 실행
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("com.kosmo.notification","1초마다 호츨");
            }

            @Override
            public void onFinish() {
                //3초 후에 프로그래스 다이어로그 닫기
                progressDialog.dismiss();
            }
        };/////////CountDownTimer


        //기본 대화 상자  띄우기
        btnAlertBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBasic = new AlertDialog.Builder(v.getContext());
                //다이얼로그 대화 상자 설정
                alertBasic.setCancelable(false);//다른 영역 클릭시에 alert가 꺼지지 않는다.
                alertBasic.setIcon(android.R.drawable.ic_dialog_email)//대화상자아이콘 설정
                .setTitle("올레 서비스")//대화 상자 제목
                .setMessage("올레 서브스에\r\n가입하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"가입절차 진행",0).show();

                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();

            }
        });


        //목록형 대화상자 띄우기
        btnAlertItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_lock_power_off)
                        .setTitle("당신이 좋아하는 스포츠는?")
                        //.setMessage("내가 좋아하는 스포츠는 축구")// 쓰면 목록이 안나온다.
                        .setItems(sports, new DialogInterface.OnClickListener() {
                            //목록 아이템 클릭시
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //which는 클릭한 아이템의 인덱스
                                Toast.makeText(v.getContext(),"당신이 좋아하는 스포츠는 "
                                        +sports[which], 0).show();
                            }
                        })
                        .setNegativeButton("취소",null).show();
            }
        });


        //radio 대화상자 .setSingleChoiceItems(배열,이벤트 리스너) use - 하나만 선택 가능
        btnAlertRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_dialer)
                        .setTitle("당신이 좋아하는 스포츠는?")
                        .setSingleChoiceItems(sports, -1, new DialogInterface.OnClickListener() {
                            //두번째 인자는 체크된 상태로 보여줄 아이템의 인덱스값을 준다.
                            //-1일 경우는 아무것도 체크되어있지 않은 상태
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //which값이 인덱스
                                which_radio = which;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //which값이 무조건 -1
                                if(which_radio != -1){
                                    Toast.makeText(v.getContext(),"당신이 좋아하는 스포츠는 "
                                            +sports[which_radio], 0).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });



        //체크박스형 대화상자
        btnAlertCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_menu_compass)
                        .setTitle("Which do you like")
                        //new boolean[] 두번째 인자는 선택된 상태로 보여줄 boolean형 배열
                        .setMultiChoiceItems(sports, which_checks, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Log.i("com.kosmo.notification", String.format(
                                        "which값= %s, isChecked= %s",which,isChecked));
                                which_checks[which]=isChecked;

                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               //which: 무조건 값이 -1
                                String checked = "";
                               for(int i=0;i<which_checks.length;i++){
                                   if(which_checks[i]) checked += sports[i]+" ";
                               }
                               Toast.makeText(MainActivity.this, checked, 0).show();
                            }
                        })
                        .setNegativeButton("취소",null).show();
            }
        });




        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 빌더로 AlertDialog 생성
                progressDialog = new AlertDialog.Builder(v.getContext())
                        //.setCancelable(false)
                        .setView(R.layout.progress)
                        .setIcon(android.R.drawable.ic_menu_compass)
                        .setTitle("Login").create();

                // 2. AlertDialog의 show()로 보이기
                progressDialog.show();

                //몇 초후 닫기 방법 1
                //timer.start();
                //몇 초후 닫기 방법 2- android.os.Handler() 사용
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {//3초 후에 실행된다.
                        progressDialog.dismiss();
                    }
                }, 3000);


            }
        });



        //커스텀 대화상자 - 대화상자의 디자인을 내가 정한다.
        /*
        1. Layout 폴더에 사용자 정의용 xml 생성
        2. getLayoutInflater() 메서드로 LayoutInflater 객체 얻어서
        3. Alert
        주석자리
        */

        btnCustom.setOnClickListener(new View.OnClickListener() {

            private AlertDialog customDialog;

            @Override
            public void onClick(View v) {
                //커스텀용 레이아웃 전개하기
                //EditText를 얻기 위해서 아래와 같이 전개.
                View view = View.inflate(v.getContext(),R.layout.custom_layout,null);
                //inflate된 view 로 findViewById()
                EditText editText = view.findViewById(R.id.editText);
                Button btnOK = view.findViewById(R.id.btnOK);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().trim().length() ==0 ){
                            /*
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("입력체크 대화상자")
                                    .setIcon(android.R.drawable.ic_menu_compass)
                                    .setCancelable(false)
                                    .setPositiveButton("확인",null).show();
                                    */
                            //대화상자 열지 않고 빨간 hint 보여주기
                            CharSequence hint = "입력해 주세욧!!!";
                            editText.setHintTextColor(Color.RED);
                            editText.setHint(hint);
                            //띄어쓰기 입력시 띄어쓰기 지우고 hint보이게 하기
                            editText.setText("");

                            return;
                        }
                        customDialog.dismiss();
                        Toast.makeText(MainActivity.this,editText.getText(),0).show();
                    }
                });

                customDialog = new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_map)
                        .setTitle("내가 만든 커스터마이징 대화상자")
                        .setCancelable(false)
                        .setView(view).create();//create()하면 AlertDialog 반환
                customDialog.show();

            }
        });


    }///////////onCreate

    private void initView() {
        textview = (TextView) findViewById(R.id.textview);
        btnAlertBasic = (Button) findViewById(R.id.btnAlertBasic);
        btnAlertItems = (Button) findViewById(R.id.btnAlertItems);
        btnAlertRadio = (Button) findViewById(R.id.btnAlertRadio);
        btnAlertCheck = (Button) findViewById(R.id.btnAlertCheck);
        btnProgress = (Button) findViewById(R.id.btnProgress);
        btnCustom = (Button) findViewById(R.id.btnCustom);
    }




}/////////class