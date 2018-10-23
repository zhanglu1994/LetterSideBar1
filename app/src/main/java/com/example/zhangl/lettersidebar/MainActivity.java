package com.example.zhangl.lettersidebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_letter;
    LetterSideBar mLetterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_letter = findViewById(R.id.tv_letter);
        mLetterSideBar = findViewById(R.id.mLetterSideBar);
        mLetterSideBar.setOnLetterTouchListener(new LetterSideBar.TouchLetterListener() {
            @Override
            public void touch(CharSequence letter,boolean isTouch) {
                if (isTouch){
                    tv_letter.setVisibility(View.VISIBLE);
                }else {
                    tv_letter.setVisibility(View.GONE);
                }
                tv_letter.setText(letter);
            }
        });
    }
}
