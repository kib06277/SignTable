package com.example.signtable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//簽字版
public class MainActivity extends AppCompatActivity {

    private SignView mSignView;
    private Button mBtnClear;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 和程式關聯
        mSignView = findViewById(R.id.signView);
        mBtnClear = findViewById(R.id.clear);
        mBtnSave = findViewById(R.id.save);

        //清空
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSignView != null){
                    mSignView.clear();
                }
            }
        });

        //儲存
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSignView != null){
                    mSignView.save();
                }
            }
        });
    }
}