package com.example.alphabetadventure.sprites.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

public class NextLetter extends Item{

    TextureRegion region;

   TextureRegion forwards;
    public  String[] lettersMoving;

    public NextLetter(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        lettersMoving = new String[]{"a_going_forward","b_going_forward","c_goin_forward", "d_goin_forward", "e_goin_forward", "f_goingforward", "g_goingforward", "h_goingforward"};
        forwards = new TextureRegion(screen.getAtlas().findRegion(lettersMoving[Letter.getLetterCounter()]),0,0,60,53);


        velocity = new Vector2(0.7f,0);//sets movement
        //todo use getter
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
        fdef.filter.categoryBits = MainClass.NEXTLETTER_BIT;
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

        letter.setLetterCounter(Letter.getLetterCounter()+1);

        if(Letter.getLetterCounter() >= lettersMoving.length){
            letter.setLetterCounter(0);


        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2 , body.getPosition().y - getHeight() / 2);
        if(Letter.getLetterCounter() <= lettersMoving.length-1) {
            setRegion(new TextureRegion(screen.getAtlas().findRegion(lettersMoving[Letter.getLetterCounter()+1]),0,0,60,53));
        }


        velocity.y = body.getLinearVelocity().y;//sets movent of item from box
        body.setLinearVelocity(velocity);

    }



}
