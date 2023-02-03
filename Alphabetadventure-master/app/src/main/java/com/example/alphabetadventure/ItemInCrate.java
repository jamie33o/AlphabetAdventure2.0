package com.example.alphabetadventure;

import static com.example.alphabetadventure.GameView.screenRatioX;
import static com.example.alphabetadventure.GameView.screenRatioY;
import static com.example.alphabetadventure.Letters.letterCounter;
import static com.example.alphabetadventure.SoundClass.soundCounter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class ItemInCrate {
    static Bitmap[] array;
    public boolean wasHit = true;
    public int x = 0;
    public int y;
    public int width;
    public int height;
    public  int itemCounter = 0;
    Bitmap ant, bat,cat;

    ItemInCrate(Resources res){
        ant = BitmapFactory.decodeResource(res, R.drawable.ant);
        bat = BitmapFactory.decodeResource(res, R.drawable.bat);
        ant = BitmapFactory.decodeResource(res, R.drawable.ant);
        bat = BitmapFactory.decodeResource(res, R.drawable.bat);
        width = ant.getWidth();
        height = ant.getHeight();

        width /= 8;
        height /= 8;

      if(screenRatioY < 1050 ){//use to change size on smaller phones
            width /= 4;//adds to /8 so its /10
            height /= 4;
        }
  /*
        if(screenRatioX * width > 144  ){//todo still not rite use to change size on different phones
            width /= 2;//144 is size of 10inch tablet
            height /= 2;
        }

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

*/
        ant = Bitmap.createScaledBitmap(ant, width, height, false);


        array = new Bitmap[]{Bitmap.createScaledBitmap(ant, width, height, false),
                            Bitmap.createScaledBitmap(bat, width, height, false),
                Bitmap.createScaledBitmap(ant, width, height, false),
                Bitmap.createScaledBitmap(ant, width, height, false)

                         };
        //add bitmap img for the fruit that comes out of crate and sound and question
        //cloud that ask is that rite sound yes or no


        y = -height;

    }


  static Bitmap getItem() {

        if (soundCounter == letterCounter) {
            final Bitmap bitmap = array[soundCounter];
            return bitmap;
        }


      return null;
  }




    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}

