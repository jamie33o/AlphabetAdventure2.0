package com.example.alphabetadventure.sprites.endoflevel;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.tools.B2WorldCreator;


public class Plank extends TowerPlanks {
    public Body b2body;
    private float stateTime;//used to keep track of it

    private boolean setToDestroy;
    private boolean destroyed;
    TextureRegion region;
    B2WorldCreator b2w;
    public Plank(PlayScreen screen, float x, float y, MapObject object) {
        super(screen, x, y,object);
        this.bounds=((RectangleMapObject) object).getRectangle();

        if(object.getProperties().containsKey("rotated")) {
            region = new TextureRegion(screen.getAtlas().findRegion("flatplank"), -6, 14, 150, 70);
        }else {
            region = new TextureRegion(screen.getAtlas().findRegion("verticalplank"), 0, 0, 100, 100);
        }




    }

    @Override
    public void defineItem() {

        BodyDef bdef =new BodyDef();
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ MainClass.PPM, (bounds.getY() + bounds.getHeight()/2)/MainClass.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();//creates the body for collision
        PolygonShape shape = new PolygonShape();

        // Rectangle rect = new Rectangle();
        fdef.filter.categoryBits = MainClass.PLANKS_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits = MainClass.GROUND_BIT|MainClass.PLANKS_BIT| MainClass.POWER_UP_BOX_BIT |MainClass.NEXT_LETTER_BOX_BIT |MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.LETTER_BIT;


        shape.setAsBox(bounds.getWidth()/2/MainClass.PPM,bounds.getHeight()/2/MainClass.PPM);
        fdef.shape = shape;
        fdef.density = 0.1f;//density of fixture
        fdef.restitution = 0.5f;
        fdef.friction = 0.5f;

        b2body.createFixture(fdef).setUserData(this);

        MassData massData = new MassData();

        massData.mass = fdef.density * 2 * 2;//creates the rotation when hit
        massData.I = massData.mass * (2 *2 + 2 * 2) / 12.0f;
        b2body.setMassData(massData);

        shape.dispose();
    }

    public void update(float dt) {
       // stateTime += dt;



        setRegion(region);


    }

    public void draw(Batch batch){
        super.draw(batch);

        // makes planks rotate with the fixture
        batch.draw(region, b2body.getPosition().x - region.getRegionWidth() / 2/MainClass.PPM, b2body.getPosition().y - region.getRegionHeight() / 2/MainClass.PPM,
                region.getRegionWidth() / 2/ MainClass.PPM, region.getRegionHeight() / 2/ MainClass.PPM,
                region.getRegionWidth()/MainClass.PPM, region.getRegionHeight()/MainClass.PPM,
                0.7f, 0.67f, b2body.getAngle() * MathUtils.radiansToDegrees);

    }

    public void onHit(){
      //  b2body.applyLinearImpulse(new Vector2(5, 4),b2body.getWorldCenter(), true);

       // b2body.applyTorque(500, true);

    }

    @Override
    public void use(Letter letter) {

    }
}