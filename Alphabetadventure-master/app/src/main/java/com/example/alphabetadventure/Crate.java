package com.example.alphabetadventure;
import static com.example.alphabetadventure.GameView.screenRatioY;
import static com.example.alphabetadventure.GameView.screenRatioX;
import static com.example.alphabetadventure.SoundClass.soundCounter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Crate {
    public int speed = 20;
    public boolean wasHit = false;
    public int x = 0;
    public int y;
    public int width;
    public int height;
    public  int crateCounter = 0;
    Bitmap crate1;
    public Bitmap[] crates;
    //Bitmap value;
    Bitmap brokenbox;

    Crate (Resources res)  {

          crate1 = BitmapFactory.decodeResource(res, R.drawable.crate);

         width = crate1.getWidth();
         height = crate1.getHeight();

         width /= 8;
         height /= 8;

         if(screenRatioX * width > 144  ){//todo still not rite use to change size on different phones
             width /= 2;//144 is size of 10inch tablet
             height /= 2;
         }

         width = (int) (width * screenRatioX);
         height = (int) (height * screenRatioY);

         crates = new Bitmap[]{ crate1 = Bitmap.createScaledBitmap(crate1, width, height, false),
                                         Bitmap.createScaledBitmap(crate1, width, height, false),
                                         Bitmap.createScaledBitmap(crate1, width, height, false),
                                         Bitmap.createScaledBitmap(crate1, width, height, false)

        };


         //add bitmap img for the fruit that comes out of crate and sound and question
         //cloud that ask is that rite sound yes or no
       // y = screenY ;//todo was screenY /2
       // x = (int) (64 * screenRatioX);


         y = -height;



      //  crates =  new ArrayList<>(2);

        //Todo changed from 4 to 1

       /* for(int i=0; i<2; i++)
        {
            Crate crate = new Crate(res);
            crates.add(crate);
        }*/





     }




    Bitmap getCrates () {//was used to return different images now just one
        final Bitmap crate = crates[soundCounter];
       /* if (wasHit){
            return brokenbox; }*/
     /* if(crateCounter != 2) {



      }*/



        return crate;


    }

   /* Bitmap getCrates () {

        if (birdCounter == 1) {
            birdCounter++;
            return crateCounter();
        }

        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }

        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;

        return bird4;
    }
*/



    Rect getCollisionShape () {
       return new Rect(x, y, x + width, y + height);
    }
    /*Bitmap getBoxBroke () {
        return boxBroke;//todo get ava to draw image of broken box
    }*/
}







