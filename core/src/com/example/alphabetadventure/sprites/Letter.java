package com.example.alphabetadventure.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.enemies.Enemy;

public class Letter extends Sprite {


    //Java Enums Previous Next Enums An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables)
    public enum State {FALLING,JUMPING,STANDING,RUNNING,DEAD};
    public State currentState;
    public State previousState;
    private Animation letterJump;

    private float stateTimer;//keep track of time in any given state
    public static boolean runningRight;

    public World world;
    public Body b2body;
    private  TextureRegion  aStopped,bStopped,cStopped, dStopped,eStopped;
    private  TextureRegion aForward,bForward,cForward,dForward,eForward;
    private TextureRegion letterDead;
    private boolean letterIsDead;

    public  TextureRegion[] lettersMoving;
    public  static int letterCounter;
    public  TextureRegion[] lettersStopped;
    TextureRegion region;
    public static long time;
    int i;
    public PlayScreen screen;
    static long startTime = 0;


    public Letter(PlayScreen screen){//put Playscreen screen so it would get atlas

        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;


       /* Array<TextureRegion> frames = new Array<TextureRegion>();//array to pass the constructor for the animation of letter
        for(int i = 0; i<0; i++)//runs true the atlas images to create running animation
            frames.add(new TextureRegion(getTexture(),i*50,0,54,53));
        letterRun = new Animation(0.1f,frames);//creates the animatior frame duration how long between frames
        frames.clear();

        for(int i = 0; i<1; i++){//gets 4th img in atlas i*50 = 4th img 50 is pixels
            frames.add(new TextureRegion(getTexture(),i*50,0,54,53));
            letterJump = new Animation(0.1f,frames);//creates the animatior frame duration how long between frames
            frames.clear();
        }*/
        lettersMoving = new TextureRegion[]{
                aStopped = new TextureRegion(screen.getAtlas().findRegion("a_going_forward"),0,0,60,53),
                bStopped = new TextureRegion(screen.getAtlas().findRegion("b_going_forward"),0,0,60,53),
                cStopped = new TextureRegion(screen.getAtlas().findRegion("c_goin_forward"),0,0,60,53),
                dStopped = new TextureRegion(screen.getAtlas().findRegion("d_goin_forward"),0,0,60,53),
                eStopped = new TextureRegion(screen.getAtlas().findRegion("e_goin_forward"),0,0,60,53)

        };

        lettersStopped = new TextureRegion[]{
                aForward = new TextureRegion(screen.getAtlas().findRegion("a_standing"),0,0,60,53),
                bForward = new TextureRegion(screen.getAtlas().findRegion("b_standing"),0,0,60,53),
                cForward = new TextureRegion(screen.getAtlas().findRegion("c_stopped"),0,0,60,53),
                dForward = new TextureRegion(screen.getAtlas().findRegion("d_stopped"),0,0,60,53),
                eForward = new TextureRegion(screen.getAtlas().findRegion("e_stopped"),0,0,60,53)

        };







        //get jump animation frames and add them to marioJump Animation
       // marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);
       // bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 80, 0, 16, 32);

        //create texture region for mario standing
       // bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        //create dead mario texture region
        letterDead = new TextureRegion(screen.getAtlas().findRegion("a_going_forward"), 0, 0, 50, 50);





        defineLetter();           //in the atlas x is top left y is top left then width and height of image
        setBounds(0,0,30/MainClass.PPM,33/MainClass.PPM);//setbounds is set to let know how large to render letter on screen
        setRegion(aStopped);
    }

    public void updateLetterCounter() {
        letterCounter++;
        if (letterCounter >= lettersMoving.length) {
            letterCounter = 0;
        }

    }


    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
       setRegion(getFrame(dt));//this updates the jump and run animations

        if(b2body.getPosition().y < 0 )
            die();
    }
public TextureRegion getFrame(float dt){
        currentState = getState();


    switch(currentState){//switch between states to change which for loop animation to use
        case DEAD:
                region = lettersStopped[0];
                break;
            case JUMPING:
                region = (TextureRegion) letterJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region =  lettersMoving[letterCounter];
                break;
            case FALLING:
            case STANDING:
            default:
                region = lettersStopped[letterCounter];
                break;

        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) &&!region.isFlipX()){//if he is running left and image not flipped left
            region.flip(true,false);//flips image to left
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {//if he running right and image is facing left and running right checks that he not standing still
            region.flip(true,false);//flips image to right
            runningRight = true;

        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;//set state timer to equal to does currentstate == previous state if it does then set statetimer  + dt to 0
        previousState = currentState;
        return region;

}

    public static boolean isRunningRight() {
        return runningRight;
    }


    public State getState(){//checks if letter running jumping standing
        //checks if he falling after jumping and not just falling of map to create jump animation

    if(letterIsDead) {

            return State.DEAD;

    }else {
    /*        if(b2body.getLinearVelocity().y >0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;//returs the jumping animation
        else if (b2body.getLinearVelocity().y<0)
            return State.FALLING;//returns falling animation*/
        if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;//returns running animation
        else

            return State.STANDING;//returns standing animation
    }
}



    public void jump(){


        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 0.3f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }




    public void defineLetter(){
        BodyDef bdef =new BodyDef();
        bdef.position.set(362/ MainClass.PPM,32/ MainClass.PPM);//use to set start position on map
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / MainClass.PPM);//sets the body object of letter
        fdef.filter.categoryBits = MainClass.LETTER_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits =
                MainClass.GROUND_BIT|
                MainClass.NEXT_LETTER_BOX_BIT |
                MainClass.POWER_UP_BIT|
                MainClass.POWER_UP_BOX_BIT |
                MainClass.OBJECT_BIT|
                MainClass.ENEMY_HEAD_BIT|
                MainClass.ENEMY_BIT|
                MainClass.NEXTLETTER_BIT;




        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();//edgeshape is just line between two points
        //head. set sets where line will be
        head.set(new Vector2(-5/MainClass.PPM,16/MainClass.PPM),new Vector2(5/MainClass.PPM, 16/MainClass.PPM));//-2 offset and 5 above letter head
        fdef.filter.categoryBits = MainClass.LETTER_HEAD_BIT;

        fdef.shape = head;//

        fdef.isSensor = true;//it no longer collide it use to check if user data or anything
        b2body.createFixture(fdef).setUserData(this);//uniquely identify this head ficture as head

    }


    public void die() {




        if (!isDead()) {

         //  MarioBros.manager.get("audio/music/mario_music.ogg", Music.class).stop();
          //  MarioBros.manager.get("audio/sounds/mariodie.wav", Sound.class).play();


            letterIsDead = true;

            Filter filter = new Filter();
            filter.maskBits = MainClass.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);//stops all fixture from collidin
            }

            b2body.applyLinearImpulse(new Vector2(1, 4), b2body.getWorldCenter(), true);
        }
    }



public void hit(Enemy enemy){
die();

}

public boolean isDead(){


        return letterIsDead;
}

public float getStateTimer(){
        return stateTimer;
}

    public void ramp(){

    }
public void speedUp(){



        b2body.applyLinearImpulse(new Vector2(6, 0), b2body.getWorldCenter(), true);



}


}


