package com.example.alphabetadventure.sprites.endoflevel;

import static com.example.alphabetadventure.MainClass.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;

public class BabyLetter extends Sprite {
TextureRegion region;
    public World world;
    public PlayScreen screen;
    Body bbody;

    public float statetimer;
    public boolean setToDestroy;
    TiledMap map;
    public boolean nextlevel;

    private Preferences prefs;

    public BabyLetter(PlayScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.map = screen.getMap();
        region = new TextureRegion(screen.getAtlas().findRegion("babyA"),0,0,60,60);




        defineBabyLetter();           //in the atlas x is top left y is top left then width and height of image
        setBounds(0,0,25/ MainClass.PPM,28/MainClass.PPM);//setbounds is set to let know how large to render letter on screen
        setRegion(region);


    }

    public void defineBabyLetter(){
        BodyDef bdef =new BodyDef();
        if(screen.level ==1) {
            bdef.position.set(7342 / MainClass.PPM, 502 / MainClass.PPM);//use to set start position on map
        }else{
            bdef.position.set(6392 / MainClass.PPM, 302 / MainClass.PPM);//use to set start position on map

        }
        bdef.type = BodyDef.BodyType.DynamicBody;
        bbody = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MainClass.PPM);//sets the body object of letter
        fdef.filter.categoryBits = MainClass.BABY_LETTER_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits =
                MainClass.PLANKS_BIT|
                        MainClass.GROUND_BIT|MainClass.DOOR_BIT;

        fdef.shape = shape;
        bbody.createFixture(fdef).setUserData(this);


    }

    public void update(float dt) {
        statetimer += dt;


        setPosition(bbody.getPosition().x - getWidth() / 2, bbody.getPosition().y - getHeight() / 2);




        setRegion(region);//this updates the jump and run animations

       if(bbody.getPosition().y < 100/MainClass.PPM) {
           bbody.applyLinearImpulse(new Vector2(-.01f, .0f), bbody.getWorldCenter(), true);
           exit();
       }
    }


    public void babyLetterSetToDestroy() {
         setBounds(0, 0, 7 / MainClass.PPM, 7 / MainClass.PPM);
         nextlevel = true;

         if(screen.level == 1) {
             screen.level++;
         }else {
             screen.level--;
         }
        // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
        manager.get("sounds/endoflevel.wav", Sound.class).play();

        prefs = Gdx.app.getPreferences("my-game");
        prefs.putInteger("current-level", screen.level);
        prefs.flush();
         statetimer = 0;
    }

public float getStateTimer(){
        return statetimer;
}

    public void exit(){

        Filter filter1 = new Filter();
        filter1.categoryBits = MainClass.BABY_LETTER_BIT;
        filter1.maskBits = MainClass.GROUND_BIT|MainClass.DOOR_BIT;

        for (Fixture fixture1 : bbody.getFixtureList()) {
            fixture1.setFilterData(filter1);//stops all fixture from collidin
        }
    }
}
