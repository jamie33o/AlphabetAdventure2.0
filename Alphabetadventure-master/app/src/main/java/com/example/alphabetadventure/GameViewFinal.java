package com.example.alphabetadventure;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;


public class GameViewFinal extends SurfaceView implements Runnable{


    public boolean catapultUsed;
    private Thread thread;
    private final boolean isPlaying = true;
    public BackgroundFinal backgroundFinal;
    final int screenX, screenY;
    private final Paint paint;
    public float screenRatioX, screenRatioY;//use to make everything fit on differnt size screen
    final TowerPlank[] flatPlanks;
    final List<TowerPlank> planks;
   final Catapult catapultcup,catapultcup2, catapultbase;
   public ItemInCrate item;
   public static boolean catapultBool = false;
    public TowerPlank plank1,plank2,plank3,plank4,plank5,plank6;

    public SharedPreferences prefs;
    int max = 30;
    int min = 20;
    int range = max - min + 1;
    int b;
    private final GameActivityFinal activity;

    public GameViewFinal(GameActivityFinal activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;



        backgroundFinal = new BackgroundFinal(screenX, screenY, getResources());



        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planks =  new ArrayList<>();


            plank6 = new TowerPlank(getResources());
            plank1 = new TowerPlank(getResources());
            plank2 = new TowerPlank(getResources());
            plank3 = new TowerPlank(getResources());
            plank4 = new TowerPlank(getResources());
            plank5 = new TowerPlank(getResources());

            planks.add(plank1);
            planks.add(plank2);
            planks.add(plank3);
            planks.add(plank4);
            planks.add(plank5);
            planks.add(plank6);



        catapultbase = new Catapult(getResources());
        catapultcup = new Catapult(getResources());
        catapultcup2 = new Catapult(getResources());


        flatPlanks = new TowerPlank[3];

        for (int i = 0;i < 3;i++) {

            TowerPlank flatPlank = new TowerPlank(getResources());
            flatPlanks[i] = flatPlank;

        }


        item = new ItemInCrate(getResources());

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();
        }
    }
        private void update() {

        if(catapultBool) {//sets starting point for item out of catapult
            item.x = 100;
            item.y = screenY - 590;
        }
        if(item.x > screenX)
             catapultUsed = false;

//creates curve of item coming out of catapult
        if (item.x < screenRatioX * 200) {
                    item.x += 30;
                    item.y -= 40;
                } else if(item.x < screenRatioX *400){
                    item.x += 30;
                    item.y -= 30;

                } else if(item.x < screenRatioX *600) {
                    item.x += 30;
                    item.y -= 20;
                }else if(item.x < screenRatioX *800){
                        item.x += 30;
                        item.y -= 10;
                }else if(item.x < screenRatioX *1000){
                    item.x += 30;
                    item.y += 10;
                }else if(item.x <screenRatioX *  1200){
                    item.x += 30;
                    item.y += 15;
                }else if(item.x <screenRatioX *  1300){
                    item.x += 30;
                    item.y += 20;
                }else{
                    item.x += 30;
                    item.y += b;//todo add random number so changes where it hits on tower

                }


            plank1.x = screenX-300;
            plank1.y = screenY-300;

            plank2.x = screenX-500;
            plank2.y = screenY-300;

            flatPlanks[0].x = screenX-500;
            flatPlanks[0].y = screenY-395;

            plank3.x = screenX-300;
            plank3.y = screenY-495;

            plank4.x = screenX-500;
            plank4.y = screenY-500;

            flatPlanks[1].x = screenX-500;
            flatPlanks[1].y = screenY-595;
                plank5.x = screenX - 500;
                plank5.y = screenY - 700;

                plank6.x = screenX - 300;
                plank6.y = screenY - 695;

            flatPlanks[2].x = screenX-500;
            flatPlanks[2].y = screenY-795;


            if(item.y> screenY -270)
             item.x = screenX+500;

            catapultcup.x =  100;
            catapultcup.y = screenY -560;

            catapultcup2.x =  90;
            catapultcup2.y = screenY -425;

            catapultbase.x = 200;
            catapultbase.y = screenY -400;

            for (int l = 0;l < planks.size();l++) {

                if (Rect.intersects(planks.get(l).getCollisionShape(), item.getCollisionShape())) {
                  //  hitCounter++
                    waitBeforeExiting();

                  // wasHit = true;

                    item.x = screenX+500;
                    catapultUsed = false;
                    planks.remove(l);//used to remove array element
                           //needs to be for loop not foreach loop
                    break; //used to get rid of yellow line better code?
                }

            }


        }

        private void draw(){

            if (getHolder().getSurface().isValid()) {

                Canvas canvas = getHolder().lockCanvas();
                canvas.drawBitmap(backgroundFinal.backgroundFinal, backgroundFinal.x, backgroundFinal.y, paint);


                for (TowerPlank flatPlank : flatPlanks)
                    canvas.drawBitmap(flatPlank.plankFlat, flatPlank.x, flatPlank.y, paint);


                canvas.drawBitmap(item.ant, item.x , item.y, paint);


                canvas.drawBitmap(catapultbase.catapultbase, catapultbase.x, catapultbase.y, paint);
                if(catapultBool) {
                    canvas.drawBitmap(catapultcup.catapultcup, catapultcup.x, catapultcup.y, paint);

                }else {
                    canvas.drawBitmap(catapultcup2.catapultcup2, catapultcup2.x, catapultcup2.y, paint);
                }







                for (TowerPlank plank : planks) {

                    canvas.drawBitmap(plank.plank, plank.x, plank.y, paint);
                }
                getHolder().unlockCanvasAndPost(canvas);
            }


        }

        private void sleep(){
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    public void resume(){
thread = new Thread(this);
thread.start();

    }

    public void pause(){

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

    if (event.getAction() == MotionEvent.ACTION_DOWN && !catapultUsed) {//juming movement
         b = (int)(Math.random() * range) + min;

        catapultBool = true;
        catapultUsed = true;

    }

        if (event.getAction() == MotionEvent.ACTION_UP) {//forward movement

            catapultBool = false;


        }
        return true;
    }


    private void waitBeforeExiting() {




        try {
            Thread.sleep(3000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent c = new Intent(activity, MainActivity.class);
        activity.startActivity(c);
        activity.finish();
    }


}
