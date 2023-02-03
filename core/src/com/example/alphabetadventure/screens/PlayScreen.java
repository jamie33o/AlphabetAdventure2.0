package com.example.alphabetadventure.screens;


import static com.example.alphabetadventure.sprites.Letter.letterCounter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.sprites.endoflevel.Plank;
import com.example.alphabetadventure.sprites.enemies.Enemy;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.items.NextLetter;
import com.example.alphabetadventure.sprites.items.Item;
import com.example.alphabetadventure.sprites.items.ItemDef;
import com.example.alphabetadventure.sprites.items.PowerUp;
import com.example.alphabetadventure.tools.B2WorldCreator;
import com.example.alphabetadventure.tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private MainClass game;//brings in game class
    private OrthographicCamera gamecam;//follows along the game world and wat viewport display
    public static Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //Box2d Variables
    private World world;
    private B2WorldCreator creator;
    private Box2DDebugRenderer b2dr;

    //Sprites
    public Letter player;

    private TextureAtlas atlas;

    private Array<Item> items;//array of all item in game world
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;//item definitions

Plank plank;

    public PlayScreen(MainClass game){
        atlas = new TextureAtlas("MainAtlas.atlas");


        this.game = game;//brings in game class
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MainClass.V_WIDTH / MainClass.PPM,MainClass.V_HEIGHT / MainClass.PPM,gamecam);//resize game for different screen size fitviewport keeps aspect ratio
        hud = new Hud(game.batch);

        //create the tiled map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1bb.tmx");

        renderer = new OrthogonalTiledMapRenderer(map,1/MainClass.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);


        //world creates gravity
        world = new World(new Vector2(0,-200 / MainClass.PPM),true);
        b2dr = new Box2DDebugRenderer();//creates lines around objects

        creator = new B2WorldCreator(this);

        player = new Letter(this);

        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();


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
        if(player.currentState != Letter.State.DEAD) {

            if(Gdx.input.isTouched() && Gdx.input.getX()< gamePort.getScreenWidth()/3.5&& Gdx.input.getY()> gamePort.getScreenHeight()/1.3 && player.b2body.getLinearVelocity().x<2)
                player.b2body.applyLinearImpulse(new Vector2(-0.04f, 0), player.b2body.getWorldCenter(), true);
            if(Gdx.input.isTouched() && Gdx.input.getX()> gamePort.getScreenWidth()/9&&  Gdx.input.getX()< gamePort.getScreenWidth()/4 && Gdx.input.getY()> gamePort.getScreenHeight()/1.3&& player.b2body.getLinearVelocity().x<2)
                player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);
            if(Gdx.input.isTouched(1) && player.b2body.getLinearVelocity().y <2&& player.getY() < 0.4 ||Gdx.input.isTouched() && Gdx.input.getX()> gamePort.getScreenWidth()/1.3&&player.b2body.getLinearVelocity().y <2&& player.getY() < 0.4 )
                player.jump();





       /*     if (Gdx.input.isTouched(1)) {
                // player.fire();

            }*/
        }

    }


    public void update(float dt){

        handleInput(dt);//updates the input keep at top
      handleSpawnedItems();
       //takes 1 step in the physics simulation (60 times a second)
        world.step(1/60f,6,2);

        player.update(dt);//updates the letter

        for(Enemy enemy: creator.getGoombas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 424 / MainClass.PPM) //224 is 14 16px tiles when player 14 tiles away enemy wakes
                enemy.b2body.setActive(true);//wakes enemy up when player comes close
        }
        for(Plank planks: creator.getPlanks()) {


            planks.update(dt);

        }
        for(Item item: items){
            item.update(dt);

        }



            hud.update(dt);




        //attach our gamecam to our player.x coordinate stops camera moving
        if(player.currentState != Letter.State.DEAD){
            gamecam.position.x = player.b2body.getPosition().x;
        }


        gamecam.update();
        renderer.setView(gamecam);

    }



    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//clears  screen between frames


        renderer.render();

        b2dr.render(world, gamecam.combined);//game.combined projection matrixs for it

        //render letter to screen
        game.batch.setProjectionMatrix(gamecam.combined);//main cam set only wat game can see
        game.batch.begin();//open batch folder



        if (player.isDead()) {
            float x;
            float y;

            for(int i =0 ; i <= letterCounter; i++){

                if(i <= letterCounter/2) {
                    x = player.getX() +player.getWidth() * i;
                    y = player.getY() +player.getHeight() * i;
                    game.batch.draw(player.lettersStopped[i], x, y, player.getWidth(), player.getHeight()); // draw the letter

                }else{
                    float z = player.getX() - player.getWidth() * (i- letterCounter/2);
                    float c = player.getY() +player.getHeight() * (i- letterCounter/2);
                    game.batch.draw(player.lettersStopped[i], z, c, player.getWidth(), player.getHeight()); // draw the letter

                }


            }
        } else {
            player.draw(game.batch); // draw the player using the draw method
        }



        for(Enemy enemy: creator.getGoombas()) {//draws all enemies

            enemy.draw(game.batch);
        }
        for(Plank planks: creator.getPlanks()) {

            planks.draw(game.batch);

        }
        for(Item item: items){//draws all collectable items
            item.draw(game.batch);

        }


        game.batch.end();//close batch

        //set our batch to draw what the hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//renders wat camera can see
        hud.stage.draw();

        if (gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();//all of resources
        }

    }

    public boolean gameOver(){
        if(player.currentState == Letter.State.DEAD && player.getStateTimer() > 5){

            letterCounter =0;

            return true;

        }
        return false;
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
        b2dr.dispose();
        hud.dispose();


    }
}
