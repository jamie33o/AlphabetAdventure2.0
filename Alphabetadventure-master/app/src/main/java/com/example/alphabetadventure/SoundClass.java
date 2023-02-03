package com.example.alphabetadventure;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;

public class SoundClass{
    private final android.media.SoundPool soundPool;
    //private int a,b,d,i ;
    int[] array;
    public static int  soundCounter = 0;



    SoundClass(Context context){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

           AudioAttributes audioAttributes = new AudioAttributes.Builder()
                   .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                   .setUsage(AudioAttributes.USAGE_GAME)
                   .build();

           soundPool = new android.media.SoundPool.Builder()
                   .setAudioAttributes(audioAttributes)
                   .build();

       } else

           soundPool = new android.media.SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//change to noise when letter hits a box
       array = new int[]{ soundPool.load(context, R.raw.a, 1),
                          soundPool.load(context, R.raw.b, 1),
                          soundPool.load(context, R.raw.c, 1),
                          soundPool.load(context, R.raw.d, 1),
                          soundPool.load(context, R.raw.i, 1)

       };

      /*

*/
   //todo: try create an array or for loop for the sounds






}





    public void getSoundClass() {//was used to return different images now just one
        if(soundCounter != 4) {
            soundPool.play(array[soundCounter], 1, 1, 0, 0, 1);
            //soundCounter++;
        }else
            soundCounter = 0;
        soundPool.play(array[soundCounter], 1, 1, 0, 0, 1);

    }
}
