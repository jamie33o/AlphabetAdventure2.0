package com.example.alphabetadventure.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

public class Numbers extends Enemy {

    private float stateTime;//used to keep track of it
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    TextureRegion goomba;
    private boolean setToDestroy;
    private boolean destroyed;
    public Numbers(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();//makes goomba look like he is walking
        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("c_goin_forward"), 0, 0, 50, 55));
            walkAnimation = new Animation(0.4f, frames);
            stateTime = 0;
            setBounds(getX(), getY(), 33/ MainClass.PPM, 33 / MainClass.PPM);

            //for enemy
            setToDestroy =false;
            destroyed = false;
        }

    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){//used to change goomba image
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("c_stopped"), 0, 0, 60, 55));
            stateTime = 0;
        }
        else if(!destroyed) {
           b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef =new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();//creates the body for collision
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MainClass.PPM);//sets the body object of letter
        fdef.filter.categoryBits = MainClass.ENEMY_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits = MainClass.GROUND_BIT| MainClass.POWER_UP_BOX_BIT |MainClass.NEXT_LETTER_BOX_BIT |MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.LETTER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


        //Create the Head here: for collisions
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-10, 14).scl(1 / MainClass.PPM);//top left
        vertice[1] = new Vector2(10, 14).scl(1 / MainClass.PPM);// top right
        vertice[2] = new Vector2(-10, 10).scl(1 / MainClass.PPM);//botttom left
        vertice[3] = new Vector2(10, 10).scl(1 / MainClass.PPM);//botom right
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;//makes him bouncy
        //causing game to crash cause
        fdef.filter.categoryBits = MainClass.ENEMY_HEAD_BIT;//gets collision of letter jump on head
        b2body.createFixture(fdef).setUserData(this);

//todo flip enemy image
        /*if((b2body.getLinearVelocity().x < 0 || !runningRight) &&!region.isFlipX()){//if he is running left and image not flipped left
            region.flip(true,false);//flips image to left
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {//if he running right and image is facing left and running right checks that he not standing still
            region.flip(true, false);//flips image to right
            runningRight = true;
        }*/
    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }


    @Override
    public void hitOnHead(Letter letter) {//used to destroy goomba
        setToDestroy = true;
        //MarioBros.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }
    @Override
    public void hitByEnemy(Enemy enemy) {
       /* if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL)
            setToDestroy = true;
        else*/
         reverseVelocity(true, false);
    }

}

