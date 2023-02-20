package com.example.alphabetadventure.sprites.endoflevel;

import static com.example.alphabetadventure.MainClass.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.tools.B2WorldCreator;

public class Catapult extends TowerPlanks {
    Body baseBody;
    public Body armBody;
    TextureRegion catapultarm;
    TextureRegion catapultbase;
    private Array<FireBall> fireballs;
    private static boolean isLoaded;

    public Catapult(PlayScreen screen, float x, float y, MapObject object) {
        super(screen, x, y, object);

        this.bounds = ((PolygonMapObject) object).getPolygon().getBoundingRectangle();

        // if (object.getProperties().containsKey("catapultbase")) {
        catapultarm = new TextureRegion(screen.getAtlas().findRegion("catapultarmflat"), 0, 10, 100, 100);

        //  } else {
        catapultbase = new TextureRegion(screen.getAtlas().findRegion("catapultbase"), 0, 0, 100, 100);
        fireballs = new Array<FireBall>();

        // }


    }

    @Override
    public void defineItem() {


        // Create the body definitions for the arm and base
        BodyDef baseDef = new BodyDef();
        baseDef.type = BodyDef.BodyType.StaticBody;
        baseDef.position.set((bounds.getX()) / MainClass.PPM, (bounds.getY() + bounds.getHeight()) / MainClass.PPM);
        baseBody = world.createBody(baseDef);


        BodyDef armDef = new BodyDef();
        armDef.type = BodyDef.BodyType.DynamicBody;
        armDef.position.set((bounds.getX() + bounds.getWidth()/2) / MainClass.PPM, (bounds.getY() + bounds.getHeight() + getBoundingRectangle().getHeight()/2) / MainClass.PPM);
        armBody = world.createBody(armDef);



        PolygonShape baseShape = new PolygonShape();
        baseShape.set(B2WorldCreator.getBaseVertices());

        PolygonShape armShape = new PolygonShape();
        armShape.set(B2WorldCreator.getArmVertices());




        FixtureDef armFixtureDef = new FixtureDef();
        armFixtureDef.shape = armShape;
        armFixtureDef.density = 1;


        armFixtureDef.filter.categoryBits = MainClass.CATAPULT_ARM_BIT;
        armFixtureDef.filter.maskBits = MainClass.FIREBALL_BIT ;

        FixtureDef baseFixtureDef = new FixtureDef();
        baseFixtureDef.shape = baseShape;
        baseFixtureDef.density = 50f;

        baseFixtureDef.filter.categoryBits = MainClass.CATAPULT_BASE_BIT;
        //sets wat letter can colide with
        baseFixtureDef.filter.maskBits = MainClass.GROUND_BIT;

// Attach fixtures to the arm and base bodies
        armBody.createFixture(armFixtureDef).setUserData(this);
        baseBody.createFixture(baseFixtureDef).setUserData(this);;

//edgeshape is just line between two points used to keep the fireball on catapult
        EdgeShape head = new EdgeShape();
        //head. set sets where line will be
        head.set(new Vector2(-75/MainClass.PPM,0/MainClass.PPM),new Vector2(-75/MainClass.PPM, 20/MainClass.PPM));//-2 offset and 5 above letter head
        armFixtureDef.filter.categoryBits = MainClass.CATAPULT_ARM_CATCH_BIT;
       armFixtureDef.filter.maskBits = MainClass.FIREBALL_BIT;

        armFixtureDef.shape = head;//

        armBody.createFixture(armFixtureDef).setUserData(this);//uniquely identify this head ficture as head

// Define the joint that will link the arm and base fixtures

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(armBody, baseBody, new Vector2(bounds.getX() / MainClass.PPM, bounds.getY() / MainClass.PPM));
        jointDef.enableLimit = true;
        jointDef.lowerAngle = -.5f;
        jointDef.upperAngle = (float) Math.PI / 3;

        world.createJoint(jointDef);


    }

    @Override
    public void use(Letter letter) {

    }

    public void fireCatapult(){


        armBody.applyLinearImpulse(new Vector2(.0f, .03f), armBody.getWorldCenter(), true);


    }
    public static boolean IsLoaded(){return isLoaded;}

    public void setIsLoaded(boolean isLoaded){
        this.isLoaded = isLoaded;
    }



    public void update(float dt) {

            if(armBody.getLinearVelocity().y == 0 && screen.isEndOfLevel() && armBody.getAngle()>0 && screen.getStateTimer()>3) {
                fire();
        }



        for(FireBall ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }





        setRegion(catapultbase);


        setRegion(catapultarm);
    }

    public void fire(){

      if(!isLoaded && screen.hud.getFireballcounter() >0) {
           screen.setStateTimer(0);

            //  manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/fireballhitsomething.wav", Sound.class).play();
            screen.hud.addFireball(-1);
            fireballs.add(new FireBall(screen, armBody.getPosition().x, armBody.getPosition().y, true));
            isLoaded =true;

      }
    }



    public void draw(Batch batch) {
        super.draw(batch);
        for(FireBall ball : fireballs)
            ball.draw(batch);


        batch.draw(catapultbase,baseBody.getPosition().x -catapultbase.getRegionWidth()/1.5f/MainClass.PPM,baseBody.getPosition().y-catapultbase.getRegionHeight()/1.1f /MainClass.PPM,
            catapultbase.getRegionWidth()/MainClass.PPM,catapultbase.getRegionHeight()/MainClass.PPM,
            catapultbase.getRegionWidth()/MainClass.PPM,catapultbase.getRegionHeight()/MainClass.PPM,
            0.7f,0.8f,0);

        batch.draw(catapultarm, armBody.getPosition().x - catapultarm.getRegionWidth()/1.15f / MainClass.PPM, armBody.getPosition().y- catapultarm.getRegionHeight()/1.6f / MainClass.PPM,
                catapultarm.getRegionWidth() /1.15f/ MainClass.PPM, catapultarm.getRegionHeight()/1.6f / MainClass.PPM,
                catapultarm.getRegionWidth() / MainClass.PPM, catapultarm.getRegionHeight() / MainClass.PPM,
                0.9f, 0.8f, armBody.getAngle() * MathUtils.radiansToDegrees);

}

}
