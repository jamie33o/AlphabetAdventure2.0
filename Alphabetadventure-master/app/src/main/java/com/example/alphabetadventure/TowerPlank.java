package com.example.alphabetadventure;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

public class TowerPlank {
    Bitmap plank, plankFlat;
    Bitmap[] planks;
    int height, width, flatwidth;
    int x,y;



    TowerPlank(Resources res) {

        plank = BitmapFactory.decodeResource(res, R.drawable.plank);
        plankFlat = BitmapFactory.decodeResource(res, R.drawable.plankflat);



        width = plank.getWidth();
        height = plank.getHeight();
        flatwidth = plank.getWidth();

        width /= 8;
        height /= 8;
        flatwidth /= 3;
  /*
        if (screenRatioX * width > 144) {//todo still not rite use to change size on different phones
            width /= 2;//144 is size of 10inch tablet
            height /= 2;
        }

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);


   */
        plank = Bitmap.createScaledBitmap(plank, width, height, false);
      plankFlat = Bitmap.createScaledBitmap(plankFlat, flatwidth, height, false);


    }






      Bitmap getPlanks () {





return plank;

    }



    Rect getCollisionShape () {
        return new Rect(x, y, x + flatwidth, y + height);
    }

}











