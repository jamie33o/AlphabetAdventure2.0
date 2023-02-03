package com.example.alphabetadventure.sprites.endoflevel;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;


public class Plank extends TowerPlanks {
    public Body b2body;
    private float stateTime;//used to keep track of it

    private boolean setToDestroy;
    private boolean destroyed;
    TextureRegion region;
    public Plank(PlayScreen screen, float x, float y, MapObject object) {
        super(screen, x, y,object);
        this.bounds=((RectangleMapObject) object).getRectangle();

        region = new TextureRegion(screen.getAtlas().findRegion("crackedplank"),0,0,100,100);
        setBounds(bounds.getX(), bounds.getY(), 40 / MainClass.PPM, 60 / MainClass.PPM);//setbounds is set to let know how large to render letter on screen


        velocity = new Vector2(0.7f,0);//sets movement
        this.bounds=((RectangleMapObject) object).getRectangle();



    }

    @Override
    public void defineItem() {

        BodyDef bdef =new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ MainClass.PPM, (bounds.getY() + bounds.getHeight()/2)/MainClass.PPM);
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();//creates the body for collision
        PolygonShape shape = new PolygonShape();

        // Rectangle rect = new Rectangle();
        fdef.filter.categoryBits = MainClass.PLANKS_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits = MainClass.GROUND_BIT|MainClass.PLANKS_BIT| MainClass.POWER_UP_BOX_BIT |MainClass.NEXT_LETTER_BOX_BIT |MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.LETTER_BIT;


        shape.setAsBox(bounds.getWidth()/2/MainClass.PPM,bounds.getHeight()/2/MainClass.PPM);
        fdef.shape = shape;
        fdef.density = 1.0f;//density of fixture
        fdef.restitution = 0.5f;
        fdef.friction = 0.5f;

        b2body.createFixture(fdef).setUserData(this);

        MassData massData = new MassData();
        massData.mass = 1f; // Set the mass/weight of the body
        b2body.setMassData(massData);


    }

    public void update(float dt) {
        stateTime += dt;


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(region);


    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    public void onHit(){
      //  b2body.applyLinearImpulse(new Vector2(5, 4),b2body.getWorldCenter(), true);

       // b2body.applyTorque(500, true);
        b2body.setAngularDamping(71);
    }


    @Override
    public void use(Letter letter) {

    }
}