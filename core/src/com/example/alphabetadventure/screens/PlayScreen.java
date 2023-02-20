package com.example.alphabetadventure.screens;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.sprites.endoflevel.BabyLetter;
import com.example.alphabetadventure.sprites.endoflevel.Catapult;
import com.example.alphabetadventure.sprites.endoflevel.FireBall;
import com.example.alphabetadventure.sprites.endoflevel.Plank;
import com.example.alphabetadventure.sprites.enemies.Enemy;
import com.example.alphabetadventure.sprites.Letter;

import com.example.alphabetadventure.sprites.items.CollectFireballs;
import com.example.alphabetadventure.sprites.items.NextLetter;
import com.example.alphabetadventure.sprites.items.Item;
import com.example.alphabetadventure.sprites.items.ItemDef;
import com.example.alphabetadventure.sprites.items.PowerUp;
import com.example.alphabetadventure.tools.B2WorldCreator;
import com.example.alphabetadventure.tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    public MainClass game;//brings in game class
    private OrthographicCamera gamecam;//follows along the game world and wat viewport display
    public static Viewport gamePort;
    public Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //Box2d Variables
    private World world;
    private B2WorldCreator creator;
    private Box2DDebugRenderer b2dr;

    //Sprites
    public Letter player;


    public Catapult catapult;

    private TextureAtlas atlas;
    private Array<Item> items;//array of all item in game world
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;//item definitions

    Music music;

    public  boolean noFireBallsCollecterd = true;

    public boolean isEndOfLevel() {
        return endOfLevel;
    }

    public void setEndOfLevel(boolean endOfLevel) {
        this.endOfLevel = endOfLevel;
    }

    public boolean endOfLevel = false;

    private boolean isfired = true;

    BabyLetter babyletter;
    float stateTime;

    private int letterCounter ;
    public int getLetterCounter() {
        return letterCounter;
    }

    public void setLetterCounter(int letterCounter) {
        this.letterCounter = letterCounter;
    }


    public enum gameState {PAUSED,UNPAUSED,GAMEOVER,NEXTLEVEL,STARTGAME}

    public gameState currentState;

    private Preferences prefs;

    public int level;


    public PlayScreen(final MainClass game){
        atlas = new TextureAtlas("mainatlas.atlas");
        prefs = Gdx.app.getPreferences("my-game");
        level = prefs.getInteger("current-level", 1); // Default to level 1 if no value is stored


        this.game = game;//brings in game class
        gamecam = new OrthographicCamera();//could use Gdx.graphics.getwidth() to set window size and getheight()
        gamePort = new FitViewport(MainClass.V_WIDTH / MainClass.PPM,MainClass.V_HEIGHT / MainClass.PPM,gamecam);//resize game for different screen size fitviewport keeps aspect ratio
        hud = new Hud(game.batch,this);

        //create the tiled map
        mapLoader = new TmxMapLoader();
        if(level == 1) {
            map = mapLoader.load("level1bb.tmx");
        }else if(level ==2){
            map = mapLoader.load("level2.tmx");
        }
        renderer = new OrthogonalTiledMapRenderer(map,1/MainClass.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);


        //world creates gravity
        world = new World(new Vector2(0,-200 / MainClass.PPM),true);//sets the gravity
       // b2dr = new Box2DDebugRenderer();//creates lines around objects

        creator = new B2WorldCreator(this);

        player = new Letter(this);

        world.setContactListener(new WorldContactListener());

        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        items = new Array<Item>();

        catapult = new Catapult(this, creator.poly1.getX() /  MainClass.PPM, creator.poly1.getY() /MainClass.PPM,creator.object);
        babyletter = new BabyLetter(this);




    }


    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }
    public void handleSpawnedItems(){

        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();//poll is like pop() for que
            if (idef.type == NextLetter.class){
                items.add(new NextLetter(this,idef.position.x,idef.position.y));
            }else  if (idef.type == PowerUp.class){
                items.add(new PowerUp(this,idef.position.x,idef.position.y));
            }else  if (idef.type == CollectFireballs.class){
                items.add(new CollectFireballs(this,idef.position.x,idef.position.y));
            }
        }


    }


    public  TextureAtlas getAtlas(){
        return atlas;
    }



    @Override
    public void show() {

    }
    public void handleInput(float dt){
        stateTime+=dt;



        if(player.currentState != Letter.State.DEAD) {

           //menu
            if(Gdx.input.isTouched() &&
                    Gdx.input.getX() > gamePort.getScreenWidth()/1.2 &&
                    Gdx.input.getY() < gamePort.getScreenHeight()/5f

                    &&
                    player.b2body.getLinearVelocity().y <.1) {

                currentState = PlayScreen.gameState.PAUSED;
                System.out.println("menu button" +hud.getFireballcounter());

                game.setScreen(new MenuScreen(game, this, currentState,this.hud));

            }



           //jump
            if(!endOfLevel && Gdx.input.isTouched(1) &&
                    player.b2body.getLinearVelocity().y==0f&&
                    player.b2body.getLinearVelocity().y <.1&&
                    Gdx.input.getY(1) > gamePort.getScreenHeight()/1.17f

                    ||!endOfLevel&& Gdx.input.isTouched() &&
                    player.b2body.getLinearVelocity().y==0f&&
                    Gdx.input.getX() > gamePort.getScreenWidth()/1.1 &&
                    Gdx.input.getY() > gamePort.getScreenHeight()/1.17f
                    &&
                    player.b2body.getLinearVelocity().y <.1)
            {
                player.jump();
            }

            //backwards
            if(!endOfLevel && player.b2body.getLinearVelocity().x>-2f && Gdx.input.isTouched() &&
                    Gdx.input.getX() < gamePort.getScreenWidth()/9 &&
            Gdx.input.getY() > gamePort.getScreenHeight()/1.2 )
                player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);

            //forwards
            if(!endOfLevel && player.b2body.getLinearVelocity().x<2f && Gdx.input.isTouched() &&
                    Gdx.input.getX() > gamePort.getScreenWidth()/9 &&
                    Gdx.input.getX() < gamePort.getScreenWidth()/5.5 &&
                    Gdx.input.getY() > gamePort.getScreenHeight()/1.2 ) {
                player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);
            }


            //power up
            if(!endOfLevel&& hud.getPowerUpCounter() > 0 && player.b2body.getLinearVelocity().x<4f &&
                    Gdx.input.isTouched() &&
                    Gdx.input.getX() > gamePort.getScreenWidth()/1.1 &&
                    Gdx.input.getY() < gamePort.getScreenHeight()/1.2f ||

                    !endOfLevel && Gdx.input.isTouched(1)&&
                            hud.getPowerUpCounter() > 0 &&

                Gdx.input.getY(1) < gamePort.getScreenHeight()/1.2f )
               // Gdx.input.getX(1) > gamePort.getScreenWidth()/1.1 )

            {
                if(player.b2body.getLinearVelocity().x < -0.01f && player.b2body.getLinearVelocity().x > -2.5f&& stateTime>2){
                    stateTime=0;
                    hud.addPowerUp(-1);
                    if(level==2) {
                       player.b2body.applyLinearImpulse(new Vector2(-8f, 0), player.b2body.getWorldCenter(), true);
                   }else{
                       player.b2body.applyLinearImpulse(new Vector2(-4f, 0), player.b2body.getWorldCenter(), true);

                   }
                }else if(stateTime>2&&player.b2body.getLinearVelocity().x > 0.01f || player.b2body.getLinearVelocity().x == 0f&& player.b2body.getLinearVelocity().x < 2.5f) {

                    stateTime=0;
                    hud.addPowerUp(-1);
                    if(level==2) {
                        player.b2body.applyLinearImpulse(new Vector2(8f, 0), player.b2body.getWorldCenter(), true);
                    }else{
                        player.b2body.applyLinearImpulse(new Vector2(4f, 0), player.b2body.getWorldCenter(), true);

                    }
                }


            }

            //catapult button
            if(endOfLevel && player.b2body.getLinearVelocity().x<2f && Gdx.input.isTouched() &&
                    Gdx.input.getX() > gamePort.getScreenWidth()/9 &&
                    Gdx.input.getX() < gamePort.getScreenWidth()/5.5 &&
                    Gdx.input.getY() > gamePort.getScreenHeight()/1.19f ) {
                catapult.fireCatapult();
            }

        }



    }


    public void update(float dt){


        if(currentState == gameState.UNPAUSED) {
        handleSpawnedItems();
        stateTime += dt;



        if(currentState == gameState.NEXTLEVEL)
            stateTime =0;

        handleInput(dt);//updates the input keep at top
       //takes 1 step in the physics simulation (60 times a second)

           if (Gdx.graphics.getHeight() > 1080)
               world.step(1 / 40f, 6, 2);//1/60f , 6 ,2
           else {
               world.step(1 / 60f, 6, 2);//1/60f , 6 ,2
           }


        player.update(dt);//updates the letter

        for(Enemy enemy: creator.getEraserArray()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 424 / MainClass.PPM) //224 is 14 16px tiles when player 14 tiles away enemy wakes
                enemy.b2body.setActive(true);//wakes enemy up when player comes close
        }


            catapult.update(dt);


        for(Plank planks: creator.getPlanks()) {
            planks.update(dt);

        }
        for(Item item: items){
            item.update(dt);

        }
        babyletter.update(dt);


        hud.update(dt);

        //stops gamecam for end of level
        if( player.b2body.getPosition().x >= catapult.armBody.getPosition().x +gamecam.viewportWidth /4.2 && player.b2body.getPosition().y < gamecam.viewportHeight){
           endOfLevel = true;


        }


        //attach our gamecam to our player.x coordinate stops camera moving
        if(player.currentState != Letter.State.DEAD && !endOfLevel){
            gamecam.position.x = player.b2body.getPosition().x;
            if(player.b2body.getPosition().y > gamecam.viewportHeight && player.b2body.getPosition().y < gamecam.viewportHeight+gamecam.viewportHeight) {//{
                gamecam.position.y = gamecam.viewportHeight+gamecam.viewportHeight/2f;
            }
            else if(player.b2body.getPosition().y < gamecam.viewportHeight) {
                gamecam.position.y = gamecam.viewportHeight - gamecam.viewportHeight /2;
            }
            else if(player.b2body.getPosition().y > gamecam.viewportHeight+ gamecam.viewportHeight){
                gamecam.position.y = gamecam.viewportHeight+gamecam.viewportHeight+gamecam.viewportHeight/2.4f;
            }
            else if(player.b2body.getPosition().y < gamecam.viewportHeight+gamecam.viewportHeight){
                gamecam.position.y = gamecam.viewportHeight + gamecam.viewportHeight/ 2;
            }


        }

        gamecam.update();
        renderer.setView(gamecam);
    }
    }

    @Override
    public void render(float delta) {
        if(currentState == gameState.UNPAUSED) {
            update(delta);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clears  screen between frames

            renderer.render();
          //  b2dr.render(world, gamecam.combined);//game.combined projection matrixs for it

            //render letter to screen
            game.batch.setProjectionMatrix(gamecam.combined);//main cam set only wat game can see
            game.batch.begin();//open batch folder


            if (player.isDead()) {
                float x;
                float y;

                for (int i = 0; i <= getLetterCounter(); i++) {

                    if (i <= getLetterCounter() / 2) {
                        x = player.getX() + player.getWidth() * i;
                        y = player.getY() + player.getHeight() * i;
                        game.batch.draw(new TextureRegion(player.lettersStopped[i]), x, y, player.getWidth(), player.getHeight()); // draw the letter

                    } else {
                        float z = player.getX() - player.getWidth() * (i - getLetterCounter() / 2);
                        float c = player.getY() + player.getHeight() * (i - getLetterCounter() / 2);
                        game.batch.draw(new TextureRegion(player.lettersStopped[i]), z, c, player.getWidth(), player.getHeight()); // draw the letter

                    }


                }
            } else {
                player.draw(game.batch); // draw the player using the draw method
            }


            babyletter.draw(game.batch);
            for (Enemy enemy : creator.getEraserArray()) {//draws all enemies

                enemy.draw(game.batch);
            }


            catapult.draw(game.batch);


            for (Plank planks : creator.getPlanks()) {

                planks.draw(game.batch);

            }
            for (Item item : items) {//draws all collectable items
                item.draw(game.batch);

            }

System.out.println(babyletter.nextlevel +" " +babyletter.statetimer);
            game.batch.end();//close batch

            //set our batch to draw what the hud camera sees
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//renders wat camera can see
            hud.stage.draw();
        }
            if (getGameState() == gameState.GAMEOVER||getGameState() == gameState.NEXTLEVEL) {

                game.setScreen(new MenuScreen(game,this,currentState,this.hud));
            }

    }


    public gameState getGameState(){
        if(player.currentState == Letter.State.DEAD&& player.getStateTimer() > 5){

            //todo make all one statement for game over

            endOfLevel = false;
           setLetterCounter(0);

            currentState = gameState.GAMEOVER;


        }else if(hud.getFireballcounter() == 0  && endOfLevel && stateTime > 10/*&& noFireBallsCollecterd*/){
            setLetterCounter(0);
            endOfLevel = false;


            //noFireBallsCollecterd = true;
            currentState = gameState.GAMEOVER;

        }else if (hud.worldTimer == 0) {

            setLetterCounter(0);

            currentState = gameState.GAMEOVER;

    }else if(babyletter.nextlevel && babyletter.getStateTimer() > 5){
            currentState = gameState.NEXTLEVEL;





        }else{
            currentState = gameState.UNPAUSED;

        }


        return currentState;
    }

    public float getStateTimer(){
        return stateTime;
    }
    public void setStateTimer(float stateTime){
        this.stateTime = stateTime;
    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);//resizes viewport to know game size

    }


    public  TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
       // b2dr.dispose();
        hud.dispose();

        player.dispose();






    }

}
