package com.example.alphabetadventure;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackgroundFinal {


    int x = 0, y = 0;
    Bitmap backgroundFinal;

    BackgroundFinal (int screenX, int screenY, Resources res) {

        backgroundFinal = BitmapFactory.decodeResource(res, R.drawable.background);
        backgroundFinal = Bitmap.createScaledBitmap(backgroundFinal, screenX, screenY, false);

    }

}
