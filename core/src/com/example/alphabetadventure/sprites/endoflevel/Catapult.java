package com.example.alphabetadventure.sprites.endoflevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.tools.B2WorldCreator;

public class Catapult extends TowerPlanks {
    Body baseBody;
    Body armBody;
    TextureRegion catapultarm;
    TextureRegion catapultbase;

    public Catapult(PlayScreen screen, float x, float y, MapObject object) {
        super(screen, x, y, object);

        this.bounds = ((PolygonMapObject) object).getPolygon().getBoundingRectangle();

        // if (object.getProperties().containsKey("catapultbase")) {
        catapultarm = new TextureRegion(screen.getAtlas().findRegion("catapultarmflat"), 0, 10, 100, 100);

        //  } else {
        catapultbase = new TextureRegion(screen.getAtlas().findRegion("catapultbase"), 0, 0, 100, 100);

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
        armFixtureDef.filter.maskBits = MainClass.GROUND_BIT | MainClass.PLANKS_BIT | MainClass.POWER_UP_BOX_BIT | MainClass.NEXT_LETTER_BOX_BIT | MainClass.ENEMY_BIT | MainClass.OBJECT_BIT | MainClass.LETTER_BIT;

        FixtureDef baseFixtureDef = new FixtureDef();
        baseFixtureDef.shape = baseShape;
        baseFixtureDef.density = 1;

        baseFixtureDef.filter.categoryBits = MainClass.CATAPULT_BASE_BIT;
        //sets wat letter can colide with
        baseFixtureDef.filter.maskBits = MainClass.GROUND_BIT;

// Attach fixtures to the arm and base bodies
        armBody.createFixture(armFixtureDef).setUserData(this);
        baseBody.createFixture(baseFixtureDef).setUserData(this);;


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
        armBody.applyLinearImpulse(new Vector2(1, 4), armBody.getWorldCenter(), true);

    }


    public void update(float dt) {

        //  stateTime += dt;

        setRegion(catapultbase);


        setRegion(catapultarm);
    }

    public void draw(Batch batch) {
        super.draw(batch);

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
