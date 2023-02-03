package com.example.alphabetadventure;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


import static com.example.alphabetadventure.GameView.screenRatioX;
import static com.example.alphabetadventure.GameView.screenRatioY;

public class Letters {

   // private int openClose = 0; //the time variable that is global for walkingindex
   // private int walkingIndex = 0;
    boolean isGoingForward = false;
    boolean isGoingBackwards = false;
    boolean isJumpingUp = false;
    Bitmap lettera1, lettera2, letterb1, letterb2, letterc1 , letterc2;
   // bird3 ^^^is just to test will be deleted and use letter forward
   private final GameView gameView;
    int x, y, width, height, forward = 0;
    public static int  letterCounter;
    Bitmap[][] array;
Letters(GameView gameView, int screenY, Resources res){//this is the constructor

    this.gameView = gameView;

    lettera1 = BitmapFactory.decodeResource(res, R.drawable.lettera1);//change to letter jump
    lettera2 = BitmapFactory.decodeResource(res, R.drawable.lettera2);
    //letteraDead = BitmapFactory.decodeResource(res, R.drawable.letteraDead);
    letterb1 = BitmapFactory.decodeResource(res, R.drawable.letterb1);//change to letter jump
    letterb2 = BitmapFactory.decodeResource(res, R.drawable.letterb2);
    //letteraDead = BitmapFactory.decodeResource(res, R.drawable.letteraDead);
    letterc1 = BitmapFactory.decodeResource(res, R.drawable.letterc1);//change to letter jump
    letterc2 = BitmapFactory.decodeResource(res, R.drawable.letterc2);

    //letterBackward = BitmapFactory.decodeResource(res, R.drawable.letterBackward);
    //letterDead = BitmapFactory.decodeResource(res, R.drawable.letterDead);

    width = lettera1.getWidth();//makes image smaller
    height = lettera1.getHeight();

    width /= 8;
    height /= 8;
//standard phone is roughly 1050
    if(screenY < 1450 ){//use to change size on different phones
        width /= 2;//adds to /8 so its /10
        height /= 2;
    }

    width = (int) (width * screenRatioX);//changes size for different screens
    height = (int) (height * screenRatioY);//cast to int cause screenratio was a float

    array = new Bitmap[][]{{lettera1 = Bitmap.createScaledBitmap(lettera1, width, height, false),
                            lettera2 = Bitmap.createScaledBitmap(lettera2, width, height, false),
                            lettera2 = Bitmap.createScaledBitmap(lettera2, width, height, false)},
                    {Bitmap.createScaledBitmap(letterb1, width, height, false),
                    Bitmap.createScaledBitmap(letterb2, width, height, false),
                    Bitmap.createScaledBitmap(letterb2, width, height, false)},
            {Bitmap.createScaledBitmap(letterc1, width, height, false),
                    Bitmap.createScaledBitmap(letterc2, width, height, false),
                    Bitmap.createScaledBitmap(letterb2, width, height, false)}
    };


    //letterBackward = Bitmap.createScaledBitmap(letterBackward, width, height, false);
    //
    // y is verticle "up" x is horizontal" this sets location of starting point
    y = screenY ;//todo was screenY /2
    x = (int) (64 * screenRatioX);

}
//todo HOW IM GONNA LINK ALL TOGHETHER---if level letters mathes letter


    Bitmap getLetters() {//was get flight
      //  Bitmap[] walking = {lettera1, lettera2}; // list to alternate images
       // if(level =)



        if(isGoingForward){

            return array[letterCounter][1];
        }else
            return array[letterCounter][0];
       /* if(wrongAnswer)
            return array[0][2];*/
           /* if (openClose != 5) {
            openClose++;

                return lettera1;*****all this code is for using multiple images to make letters walk******
        }else
            openClose = 0;

            //if(isGoingForward) {

            if (walkingIndex == 1) {
                walkingIndex = 0;
            }else
                walkingIndex = 1;

        }
        return walking[walkingIndex];*/
        //i think this has something to do with moveing the letter
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);//gets collision of letter and crate
    }

 /*  Bitmap getDead () {
        return letterDead;
        //return wholeInGround; create bitmap img for ground opening up and swallow letter
    }*/



}


