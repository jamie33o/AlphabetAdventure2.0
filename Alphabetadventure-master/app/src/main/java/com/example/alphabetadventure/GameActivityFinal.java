package com.example.alphabetadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivityFinal extends AppCompatActivity {

    private GameViewFinal gameViewFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameViewFinal = new GameViewFinal(this, point.x, point.y);

        setContentView(gameViewFinal);





    }
    @Override
    protected void onPause() {
        super.onPause();
         gameViewFinal.pause();
        //openMyPopUpWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameViewFinal.resume();
    }


}