package com.example.alphabetadventure.sprites.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

public class PowerUp extends Item{

    Letter letterClass;
    TextureRegion region;
    public PowerUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        //todo add flames coming from skateboard when get powerup
        region = new TextureRegion(screen.getAtlas().findRegion("powerUp"),0,0,60,60);
        //  setBounds(getX(), getY(), 30 / MainClass.PPM, 30 / MainClass.PPMz);//setbounds is set to let know how large to render letter on screen
        //setRegion(lettersInBox[0]);
        setRegion(region);
        velocity = new Vector2(0.7f,0);//sets movement


    }

    @Override
    public void defineItem() {
        BodyDef bdef =new BodyDef();//its a new body
        bdef.position.set(getX(),getY());// where it starts
        bdef.type = BodyDef.BodyType.DynamicBody;//it can move and affected by gravity
        body = world.createBody(bdef);//add to world

        FixtureDef fdef = new FixtureDef();//creates the body for collision
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MainClass.PPM);//sets the body object of letter
        fdef.filter.categoryBits = MainClass.POWER_UP_BIT;
        fdef.filter.maskBits = MainClass.LETTER_BIT |//wat can this fixture colide with
                MainClass.OBJECT_BIT |
                MainClass.GROUND_BIT |
                MainClass.POWER_UP_BOX_BIT |
                MainClass.NEXT_LETTER_BOX_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);//attach fixture to body

    }

    @Override
    public void use(Letter letter) {


        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        velocity.y = body.getLinearVelocity().y;//sets movent of item from box
        body.setLinearVelocity(velocity);


    }
}



