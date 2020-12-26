package com.kosmo.tablelayout04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TableLayout");
        setContentView(R.layout.activity_main);
        //위젯 얻기
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);
        //이벤트 달기
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = username.getText().toString();
                String pwd = password.getText().toString();
                Toast.makeText(v.getContext(),
                        String.format("아이디: %s, 비번: %s",id,pwd),
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //텍스트 지우기
                username.setText("");
                password.setText("");
                //focus 주기
                username.requestFocus();
            }
        });

    }///////////onCreate
}