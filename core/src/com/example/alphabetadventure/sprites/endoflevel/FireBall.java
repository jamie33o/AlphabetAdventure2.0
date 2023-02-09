package com.example.alphabetadventure.sprites.endoflevel;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

public class FireBall extends Sprite {

    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fire;

    Body b1body;
    public FireBall(PlayScreen screen, float x, float y, boolean fire){
        this.fire = fire;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fireballs"), i * 25, 0, 25, 30));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 30 / MainClass.PPM, 30 / MainClass.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + 12, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        if(!world.isLocked())
         b1body = world.createBody(bdef);

        System.out.println(world.isLocked());
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / MainClass.PPM);
        fdef.filter.categoryBits = MainClass.FIREBALL_BIT;
        fdef.filter.maskBits =
                MainClass.GROUND_BIT |
               MainClass.PLANKS_BIT|
                MainClass.CATAPULT_ARM_BIT|
                MainClass.OBJECT_BIT;
        fdef.shape = shape;
        fdef.restitution = 1;

        fdef.friction = 0;
        System.out.println(b1body);
        b1body.createFixture(fdef);
        b1body.setLinearVelocity(new Vector2(fire ? 2 : -2, 2.5f));
    }

    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b1body.getPosition().x - getWidth() / 2, b1body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b1body);
            destroyed = true;
        }
        if(b1body.getLinearVelocity().y > 2f)
            b1body.setLinearVelocity(b1body.getLinearVelocity().x, 2f);
        if((fire && b1body.getLinearVelocity().x < 0) || (!fire && b1body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}

