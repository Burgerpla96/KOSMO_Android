package com.kosmo.customview10_03;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

//1) AppCompatEditText 상속해서 나만의 EditText만들기
public class EditTextByMe extends AppCompatEditText {

    //2) 생성자 정의
    //자바코드에서 new 연산자로 직접 EditTextByMe 객체 생성시 호출
    public EditTextByMe(Context context){
        super(context);
        Log.i("com.kosmo.customview","자바코드용 생성자");
    }
    //xml에 등록된 widget이 생성될 때 호출되는 생성자
    public EditTextByMe(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.i("com.kosmo.customview","XML용 생성자");
    }

    //3) 내부 인터페이스 정의 //java 파일에서 이벤트 붙일때, 들어가는 interface 정의
    public interface OnTextLengthListener{
        void onTextLength(int textLength);//사용자가 입력한 문자열 갯수를 받음.
    }

    //4) interface 타입의 리스너 선언
    private OnTextLengthListener onTextLengthListener;
    public void setOnTextLengthListener(OnTextLengthListener onTextLengthListener){
        this.onTextLengthListener = onTextLengthListener;
    }

    //Override 해서 기능 변화시키기
    @Override
    protected void onTextChanged(
            CharSequence text,//EditText에 입력된 문자열
            int start, //추가한 문자열의 시작 인덱스
            int lengthBefore, //삭제한 문자열의 갯수
            int lengthAfter) {//추가한 문자열의 갯수
        //EditText의 기존 기능 유지하면서 나만의 새 기능 추가
        Log.i("com.kosmo.customview",text.toString());
        //커스텀 뷰에서 오버라이딩시 문자열의 길이를 내가 정의한 인터페이스의 추상메서드에
        //매개변수로 전달해줌
        if(onTextLengthListener != null){
            //사용자가 입력할 때마다 길이를 알아낸다.
            onTextLengthListener.onTextLength(text.length());
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);//삭제하면 안된다.
    }
}
