package com.example.alphabetadventure.sprites;

import static com.example.alphabetadventure.MainClass.manager;

import com.badlogic.gdx.audio.Sound;
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
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.enemies.Enemy;


public class Letter extends Sprite {




    //Java Enums Previous Next Enums An enum is a special "class" that represents a group of constants (unchangeable variables, like final variables)
    public enum State {FALLING,JUMPING,STANDING,RUNNING,DEAD}
    public State currentState;
    public State previousState;


    private float stateTimer;//keep track of time in any given state
    public static boolean runningRight1;

    public World world;
    public Body b2body;
    private TextureRegion letterDead;

    private boolean letterIsDead;


    public  TextureRegion[] lettersStopped;
    public  TextureRegion[] lettersMoving;


    TextureRegion region1;
    public PlayScreen screen;

    public int exitDone = 0;
    private boolean setToDestroy;

    public Letter(PlayScreen screen){//put Playscreen screen so it would get atlas

        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
     //  runningRight1 = false;




        lettersMoving = new TextureRegion[]{
                 new TextureRegion(screen.getAtlas().findRegion("a_going_forward"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("b_going_forward"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("c_goin_forward"),0,0,60,53) ,
        new TextureRegion(screen.getAtlas().findRegion("d_goin_forward"),0,0,60,53) ,
        new TextureRegion(screen.getAtlas().findRegion("e_goin_forward"),0,0,60,53) ,
        new TextureRegion(screen.getAtlas().findRegion("f_goingforward"),0,0,60,53) ,
        new TextureRegion(screen.getAtlas().findRegion("g_goingforward"),0,0,60,53) ,
        new TextureRegion(screen.getAtlas().findRegion("h_goingforward"),0,0,60,53)
    };

        lettersStopped = new TextureRegion[]{
        new TextureRegion(screen.getAtlas().findRegion("a_standing"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("b_standing"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("c_stopped"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("d_stopped"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("e_stopped"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("f_standin"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("g_standing"),0,0,60,53),
        new TextureRegion(screen.getAtlas().findRegion("h_standing"),0,0,60,53)
    };

        //create dead mario texture region
        letterDead = new TextureRegion(lettersMoving[screen.getLetterCounter()]);


        defineLetter();           //in the atlas x is top left y is top left then width and height of image
        setBounds(0,0,30/MainClass.PPM,33/MainClass.PPM);//setbounds is set to let know how large to render letter on screen
        setRegion(     new TextureRegion(screen.getAtlas().findRegion("a_standing"),0,0,60,53));


    }


    public void update(float dt){
        stateTimer += dt;

        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);




           setRegion(getFrame(dt));//this updates the jump and run animations



        if(screen.isEndOfLevel() && exitDone < 200) {
            b2body.applyLinearImpulse(new Vector2(-.02f, 0), b2body.getWorldCenter(), true);
            exitDone++;

            if(exitDone == 1)
             exit();


        }

        if(b2body.getPosition().y < 0 )
            die();


    }


public TextureRegion getFrame(float dt){
        currentState = getState();


    switch(currentState){//switch between states to change which for loop animation to use
            case RUNNING:
                region1 = lettersMoving[screen.getLetterCounter()];

                break;
        case DEAD:
        case JUMPING:
            case FALLING:
            case STANDING:
        default:
            region1 =  lettersStopped[screen.getLetterCounter()];
                break;

    }
        if(b2body.getLinearVelocity().x < 0 && !region1.isFlipX() ){//if he is running left and image not flipped left
            region1.flip(true,false);//flips image to left
            runningRight1 = false;
        } else if (b2body.getLinearVelocity().x > 0 && region1.isFlipX()){//if he running right and image is facing left and running right checks that he not standing still
            region1.flip(true,false);//flips image to right
            runningRight1 = true;

        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;//set state timer to equal to does currentstate == previous state if it does then set statetimer  + dt to 0
        previousState = currentState;
        return region1;

}

    public static boolean isRunningRight() {
        return runningRight1;
    }



    public State getState(){//checks if letter running jumping standing
        //checks if he falling after jumping and not just falling of map to create jump animation

    if(letterIsDead) {

            return State.DEAD;

    }else {
        if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;//returns running animation
        else

            return State.STANDING;//returns standing animation
    }
}



    public void jump(){


        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 2f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
            //manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/jump.wav", Sound.class).play();

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
                MainClass.PLANKS_BIT|
                MainClass.GROUND_BIT|
                MainClass.BOX_BIT |
                MainClass.POWER_UP_BIT|
                MainClass.COLLECT_FIREBALL_BIT|
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

            //manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/dead.wav", Sound.class).play();


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
    //manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
    manager.get("sounds/dead.wav", Sound.class).play();


}

public void setToDestroy() {

    b2body.setLinearVelocity(0,0);

        // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
        manager.get("sounds/endoflevel.wav", Sound.class).play();

   setBounds(0, 0,  15/ MainClass.PPM, 15 / MainClass.PPM);

    setToDestroy = false;//destroys body when hits door object
}

public boolean isDead(){


        return letterIsDead;
}

public float getStateTimer(){
        return stateTimer;
}


    public void dispose() {
       getTexture().dispose();

    }

    public void exit(){

        Filter filter = new Filter();
        filter.categoryBits = MainClass.LETTER_BIT;
        filter.maskBits = MainClass.GROUND_BIT|MainClass.DOOR_BIT;

        for (Fixture fixture : b2body.getFixtureList()) {
            fixture.setFilterData(filter);//stops all fixture from collidin
        }
    }

}


