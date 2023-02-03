package com.example.alphabetadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class MyPopUpWindow extends AppCompatActivity {
    public static boolean yes,no;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pop_up_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.2),(int)(height*.1));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x =0;
        params.y = -20;

        getWindow().setAttributes(params);


        //findViewById(R.id.textView2);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override//Yes button
            public void onClick(View v) {

              yes =true;

                finish();

            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override//No button
            public void onClick(View v) {

                  no=true;

                finish();

            }
        });


    }
}