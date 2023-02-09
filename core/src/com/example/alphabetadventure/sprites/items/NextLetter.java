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
    private boolean runningRight;

    Letter letter;

    int nextLetterCounter;
    public NextLetter(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        letter = new Letter(screen);

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

        letter.setLetterCounter(letter.getLetterCounter()+1);

        if(letter.getLetterCounter() >= letter.lettersMoving.length){
            letter.setLetterCounter(0);


        }
    }

    @Override
    public void update(float dt){
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2 , body.getPosition().y - getHeight() / 2);
        if(Letter.getLetterCounter() <= letter.lettersMoving.length-1) {
            setRegion(letter.lettersMoving[Letter.getLetterCounter() + 1]);
        }else {
            setRegion(letter.lettersMoving[1]);
//todo end game on last letter
        }


        velocity.y = body.getLinearVelocity().y;//sets movent of item from box
        body.setLinearVelocity(velocity);

    }



}
