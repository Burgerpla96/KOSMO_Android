package com.kosmo.compoundbutton12;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "com.kosmo.compoundbut";
    //스피너의 데이터로 사용할
    private String[] items= {"자바","오라클","HTML5","CSS3","JavaScript(ES5)",
            "JSP&SERVLET", "Spring(Mybatis)","JQuery/Ajax","Bootstrap","Tiles",
            "Spring security", "git"};
    //결과 출력용 멤버 변수들
    private String radioString = "남성";
    private String spinnerString = "JSP&SERVLET";
    //체크박스 / 토글버튼 / 스위치 체크 여부 판단
    private List checkboxChecked = new Vector();
    private String toggleString = "OFF";
    private String switchString = "ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //위젯 얻기
        TextView textView = findViewById(R.id.textview);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        //글꼴 설정
        textView.setTypeface(Typeface.SANS_SERIF);//글꼴 몇개 없다.
        //checkbox류
        CheckBox cbPol = findViewById(R.id.check_politics);
        CheckBox cbEco = findViewById(R.id.check_economics);
        CheckBox cbEnt = findViewById(R.id.check_entertainments);
        CheckBox cbSpo = findViewById(R.id.check_sports);

        //자바코드로 상태변경
        cbEnt.setChecked(false);
        cbEco.setChecked(true);
        checkboxChecked.add(((CheckBox)findViewById(R.id.check_economics)).getText());

        //라디오 그룹
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        //라디오 버튼 체크 해제: clearCheck()
        radioGroup.clearCheck();
        //라디오 버튼 체크: check(리소스 아이디)
        //라디오 버튼 위젯 하나하나를 얻지 않아도 된다.
        radioGroup.check(R.id.radio_male);

        //토글 버튼
        ToggleButton toggleButton = findViewById(R.id.togglebutton);
        Switch switchButton = findViewById(R.id.switch_button);

        //자바코드로 온/오프 상태 바꾸기
        toggleButton.setChecked(false);
        switchButton.setChecked(true);


        //스피너
        Spinner spinner = findViewById(R.id.spinner);
        //어뎁터 설정
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,items);
        //위젯에 어뎁터 적용
        spinner.setAdapter(adapter);
        //처음에 보여줄 아이템 선택
        spinner.setSelection(5);

        //버튼
        Button button = findViewById(R.id.button);
        //리스너 달기
        //this로 listener 처리하기 위해서 implements CompoundButton.OnCheckedChangeListener
        //후에 Override
        cbEco.setOnCheckedChangeListener(this);
        cbPol.setOnCheckedChangeListener(this);
        cbEnt.setOnCheckedChangeListener(this);
        cbSpo.setOnCheckedChangeListener(this);

        toggleButton.setOnCheckedChangeListener(this);
        switchButton.setOnCheckedChangeListener(this);

        //라디오 그룹은 위와 다른 함수 적용,
        // 라디오 그룹의 setOnCheckedChangeListener를 달아야한다.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //두번째 인자는 체크된 라디오 버튼의 리소스 아이디값
                //방법1)
                /*
                switch (checkedId){
                    case R.id.radio_male: radioString ="남성"; break;
                    case R.id.radio_female: radioString ="여성"; break;
                    default: radioString="트랜스젠더";
                }
               */

                //방법2)
                radioString = ((RadioButton)findViewById(checkedId)).getText().toString();
                Log.i(TAG,checkedId+" 선택: "+radioString);

            }
        });


        //spiner는 setOnItemSelectedListener로 선택을 가져오기
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"position: "+position+", row id: "+id);
                //위에서 spinner.setSelection(5);으로 선택하여 호출이 된다.
                Log.i(TAG,items[position]+" 선택");//index 개념의 position 사용
                Log.i(TAG,((CheckedTextView)view).getText()+" 선택");//얻어진 view로 가져오기
                spinnerString = items[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG,"스피너: onNothingSelected()");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(
                        String.format(
                            "체크박스: %s%n라디오 버튼: %s%n토클버튼: %s%n 스위치: %s%n스피너: %s%n",
                                Arrays.toString(checkboxChecked.toArray()),
                                radioString,
                                toggleString,
                                switchString,
                                spinnerString
                        ));

            }
        });




    }//onCreate

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //isChecked 가 true, false로 나온다.
        Log.i(TAG, "isChecked : "+isChecked);
        if(buttonView instanceof  CheckBox){//체크박스
            if(isChecked){//체크한 경우
                checkboxChecked.add(buttonView.getText());
                Log.i(TAG, "선택한 체크박스 : "+buttonView.getText());

            }
            else{//해제한 경우
                checkboxChecked.remove(buttonView.getText());
                Log.i(TAG, "체크해제한 체크박스 : "+buttonView.getText());
            }
        }
        else if(buttonView.getId() == R.id.togglebutton){//토글 버튼
            //toggle button에서는
            //text속성은 안보이고, 부보에 없던 속성인 textOn/textOff사용
            //getTextOn,Off()는 반환 타입이 CharSequence //toString() 하기

            if(isChecked){//체크한 경우
                toggleString = ((ToggleButton)buttonView).getTextOn().toString();
                Log.i(TAG, ((ToggleButton)buttonView).getTextOn()+"온");

            }
            else{//해제한 경우
                toggleString = ((ToggleButton)buttonView).getTextOff().toString();
                Log.i(TAG, ((ToggleButton)buttonView).getTextOff()+"오프");
            }

        }
        else{//스위치
            if(isChecked) switchString ="ON";
            else switchString ="OFF";
        }



    }///////////





}