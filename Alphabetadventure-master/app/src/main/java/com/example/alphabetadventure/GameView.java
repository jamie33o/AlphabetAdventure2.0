package com.example.alphabetadventure;




import static com.example.alphabetadventure.Letters.letterCounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;


import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {

    boolean itemCollected;
    boolean correctAnswer = false;
    ItemInCrate item;
    SoundClass soundClass;
    boolean yes, no;
    private Thread thread;
    private boolean isPlaying;
    //private final boolean isGameOver = false;
    private final int screenX;
    public final int screenY;
    private int score = 0;
   // private final int spaceoutCount= 0;
    public static float screenRatioX, screenRatioY;//use to make everything fit on differnt size screen
    private final Paint paint;
    //private  List <Crate> crates;
    private final SharedPreferences prefs;
    //private Random random;
    private final Letters letters;//was Flight flight this is letters class
    private final GameActivity activity;
    private final Background background1;
    private final Background background2;
    public Crate crate;
   // private int jumpCounter = 0;
    List<ItemInCrate> itemArray;
    public static boolean finalLevel;



    // public boolean nextLetter;



    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;



        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX-20;//had to minus 20 so background image would stay together
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;


        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        item = new ItemInCrate(getResources());

        letters = new Letters(this, screenY, getResources());

        MyPopUpWindow bool = new MyPopUpWindow();
        yes = MyPopUpWindow.yes;
        no = MyPopUpWindow.no;

        soundClass = new SoundClass(getContext());


        background2.x = screenX ;

        itemArray = new ArrayList<ItemInCrate>();

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

       // crates =  new ArrayList<>();


        //Todo changed from 4 to 1

       // for(int i=0; i<2; i++)
       // {
         crate = new Crate(getResources());
            //crates.add(crate);
       // }


       }







    //join crates together


    @Override
    public void run() {

        while (isPlaying) {


            update();
            draw();
            sleep();

        }

    }

    private void update() {

        if (letters.isGoingForward) {
            background1.x -= 15;//makes background move and screen ratiox makes fit on
            background2.x -= 15;//different size screens

        }


        if (background1.x + background1.background.getWidth() < 0) {//checks if background  is off screen
            background1.x = screenX;//resets background to right of screen to start again
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }


        if (letters.y < screenY / 3) {//sets height letter can go
            letters.y = (int) (screenY / 3);
            letters.isJumpingUp = false;
        }


        if (letters.y >= screenY - letters.height - letters.height)//todo set the platform stops letter goin off screen at botton
            letters.y = screenY - letters.height - letters.height;

        //letters movement forward
        if (letters.isGoingForward)//set movement of letters right
            letters.x += 5 * screenRatioX;


        if (letters.x < 0)//stops the letters goin off screen at left
            letters.x = 0;

        if (letters.x >= screenY - letters.width)//stops letter goin off screen at right
            letters.x = screenY - letters.width; //screeny instead of screenx makes him only go halfway

        if (letters.isGoingBackwards)// letter goin backward speed
            letters.x -= 10 * screenRatioX;

        if (letters.isJumpingUp) {//set jumping speed of letters
            letters.y -= 20 * screenRatioY;
        } else {
            letters.y += 20 * screenRatioY;//falling speed
        }



      //  for (Crate crate : crates) {      //for loop for each crate in the array
           /* if (crate.x > screenX)*/
/*
            if(crates.get(crate.crateCounter).x == crates.get(crate.crateCounter+1).x){//creates space between crates
                crates.get(crate.crateCounter).x = crates.get(crate.crateCounter+1).x + screenY/3;

            }*/


            if (letters.isGoingForward) {
                crate.x -= 15 * screenRatioX;//speed of the crate
            }

            if (crate.x + crate.width < 0) {

                //if (!crate.wasHit) {//did the letter hit the crate
                //isGameOver = true;
                //return;
                //  }


                crate.x = screenX;//position where crate starts set it at start of right
                crate.y = (int) (screenY / 3);//changes how high or low crate is

                // crate.wasHit = false;
                }


            if (Rect.intersects(letters.getCollisionShape(), crate.getCollisionShape())) {//Sound crate make
                //items++;//todo create items counter
                letters.y = screenY - letters.height - letters.height;


                item.y = crate.y;
                item.x = crate.x;


                crateHit();


                 //crate.crateCounter--;
                 //crates.add(crate);
                 //crates[crate.crateCounter].getBoxBroke()// todo add broken box image
                 //crate.wasHit = true;

                //todo write method to make sound of crate and ask is this the rite sound call it here
                //then give item which the spelling starts with the letter there on


                // isGameOver = true;
                return;

            }
        if (Rect.intersects(letters.getCollisionShape(), item.getCollisionShape())) {
           letterCounter++;
           SoundClass.soundCounter++;
            itemArray.add(item);
           // itemCollected = true;

               score++;
               // item
               item.x = screenX + 500;
               crate.x = screenX + 100;//added 100 cause was still showing on screen screenx variable must be gettin its value changed somewhere

        }
        if (item.y >= screenY - item.height - item.height)//todo set the platform stops item goin off screen at botton
            item.y = screenY - item.height - item.height ;


        //if(itemCollected)




        if(correctAnswer){
            item.y += 20 * screenRatioX;
            //nextLetter = true;



        }

        if(letterCounter == 1){
           finalLevel =true;
            saveIfHighScore();
            waitBeforeExiting();


        }


    }






    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);



          // for (Crate crate : crates) {


                    canvas.drawBitmap(crate.getCrates(), crate.x, crate.y, paint);

                if(MyPopUpWindow.yes == true ) {//todo this where return item if they are correct

                    if (letterCounter == SoundClass.soundCounter) {
//todo change item in crate back to item if any problem possibly causing item to fall at start
                        if(!itemCollected){
                         canvas.drawBitmap(ItemInCrate.getItem(), item.x , item.y, paint);
                         correctAnswer = true;

                        }
                    }
               }if(MyPopUpWindow.no == true) {
                    SoundClass.soundCounter++;
                    letterCounter++;
                crate.x = screenX + 100;
                MyPopUpWindow.no = false;
                }

           // }
            canvas.drawText(score + "", screenX / 2f, 164, paint);




            /*if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(letters.getDead(), letters.x, letters.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }*/

            canvas.drawBitmap(letters.getLetters(), letters.x, letters.y, paint);// draws letter after background so it shows ontop so its visible
         //   canvas.drawBitmap(buttonup, screenX/2, screenY/2, paint);// jump button is positioned
            // for (Bullet bullet : bullets)
            //    canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }




        }


    private void waitBeforeExiting() {




        Intent c = new Intent(activity, MainActivity.class);
        activity.startActivity(c);
        activity.finish();
        try {
            Thread.sleep(3000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }




    public void pause() {

        try {

                isPlaying = false;
                thread.join();


       } catch (InterruptedException e) {
            e.printStackTrace();
       }
    }





    /*getIntent() {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Boolean answerYes = Boolean.valueOf(extras.getString("yes"));
                //The key argument here must match that used in the other activity
            }
            return answerYes;*/
       /* Bundle extras1 = getIntent().getExtras();
        if (extras != null) {
            Boolean anwserNo = extras1.getString("no");
            //The key argument here must match that used in the other activity
        }

        return answerNo;*/

   // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


       if (event.getAction() == MotionEvent.ACTION_DOWN) {//juming movement
            if (event.getY() < screenY/ 2 ) {
               letters.isJumpingUp = true;


            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//forward movement
            if (event.getX() > screenX / 2 && event.getY() > screenY/ 2) {
              letters.isGoingForward = true;

            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {//backwards movement
            if (event.getX() < screenX / 2)
               letters.isGoingBackwards = true;

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {

            letters.isGoingForward = false;
            letters.isGoingBackwards = false;
            letters.isJumpingUp = false;
        }



        return true;
}



    public void crateHit() {   //todo letter gets stuck at side of box and keeps calling popUp method
        if (!prefs.getBoolean("isMute", false))
            soundClass.getSoundClass();

/*

               Item item = new Item(getResources());
             Item.x = crate.x + crate.width;
             item.y = crate.y + (crate.height / 2);
             item.add(item);//todo will be for item that comes out of crate
*/


        try {

                isPlaying = false;

                Thread.sleep(3000);
                activity.startActivity(new Intent(activity, MyPopUpWindow.class));

               } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


