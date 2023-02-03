package com.example.alphabetadventure;

import static com.example.alphabetadventure.GameViewFinal.catapultBool;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Catapult {
    Bitmap catapultcup2,catapultcup1,catapultcup, catapultbase;
    public int width, height, baseheight, basewidth;
    int x,y;


    Catapult(Resources res) {

        catapultcup2 = BitmapFactory.decodeResource(res, R.drawable.catapultcup2);
        catapultcup1 = BitmapFactory.decodeResource(res, R.drawable.catapultcup1);
        catapultcup = BitmapFactory.decodeResource(res, R.drawable.catapultcup);
        catapultbase = BitmapFactory.decodeResource(res, R.drawable.catapultbase);

        width = catapultcup.getWidth();
        height = catapultcup.getHeight();
        basewidth = catapultbase.getWidth();
        baseheight = catapultbase.getHeight();

        width /= 8;
        height /= 8;

      /*  if (screenRatioX * width > 144) {//todo still not rite use to change size on different phones
            width /= 2;//144 is size of 10inch tablet
            height /= 2;
        }

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);*/
        catapultbase = Bitmap.createScaledBitmap(catapultbase, width, height, false);
        catapultcup2 = Bitmap.createScaledBitmap(catapultcup2, width, height, false);
        catapultcup1 = Bitmap.createScaledBitmap(catapultcup1, width, height, false);
        catapultcup = Bitmap.createScaledBitmap(catapultcup, width, height, false);



    }

      Bitmap getCatapultcup () {

        if (catapultBool) {//todo change catapultBool to geter and setter

            return catapultcup;
        }

        else {

            return catapultcup2;
        }


      }

}