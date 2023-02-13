package com.example.alphabetadventure.sprites.endoflevel;

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

import java.rmi.UnexpectedException;

public class BabyLetter extends Sprite {
TextureRegion region;
    public World world;
    public PlayScreen screen;
    Body b2body;

    public static float statetimer;
    public boolean setToDestroy;
    TiledMap map;
    public boolean nextlevel;
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
        bdef.position.set(7342/ MainClass.PPM,502/ MainClass.PPM);//use to set start position on map
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MainClass.PPM);//sets the body object of letter
        fdef.filter.categoryBits = MainClass.BABY_LETTER_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits =
                MainClass.PLANKS_BIT|
                        MainClass.GROUND_BIT|MainClass.DOOR_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    public void update(float dt) {
        statetimer += dt;


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);




        setRegion(region);//this updates the jump and run animations

       if(b2body.getPosition().y < 100/MainClass.PPM) {
           b2body.applyLinearImpulse(new Vector2(-.01f, .0f), b2body.getWorldCenter(), true);
           exit();
       }
    }


    public void babyLetterSetToDestroy() {
         setBounds(0, 0, 7 / MainClass.PPM, 7 / MainClass.PPM);
        screen.nextlevel = true;


         statetimer = 0;
    }

public static float getStateTimer(){
        return statetimer;
}

    public void exit(){

        Filter filter1 = new Filter();
        filter1.categoryBits = MainClass.BABY_LETTER_BIT;
        filter1.maskBits = MainClass.GROUND_BIT|MainClass.DOOR_BIT;

        for (Fixture fixture1 : b2body.getFixtureList()) {
            fixture1.setFilterData(filter1);//stops all fixture from collidin
        }
    }
}
