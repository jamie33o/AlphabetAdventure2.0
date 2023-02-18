package com.example.alphabetadventure.sprites.items;

import static com.example.alphabetadventure.MainClass.manager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

public class NextLetter extends Item{
   TextureRegion forwards;
    public  String[] lettersMoving;

    public NextLetter(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        lettersMoving = new String[]{"a_going_forward","b_going_forward","c_goin_forward", "d_goin_forward", "e_goin_forward", "f_goingforward", "g_goingforward", "h_goingforward"};
        forwards = new TextureRegion(screen.getAtlas().findRegion(lettersMoving[screen.getLetterCounter()+1]),0,0,60,53);


        if(Letter.isRunningRight()) {
            velocity = new Vector2(0.7f, 0);//sets movement
        }else{
            velocity = new Vector2(-0.7f,0);
        }
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
                MainClass.COLLECT_FIREBALL_BIT |
                MainClass.BOX_BIT;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);//attach fixture to body

    }

    @Override
    public void use(Letter letter) {
        destroy();
        //manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
        manager.get("sounds/nextletter.wav", Sound.class).play();


        screen.setLetterCounter(screen.getLetterCounter()+1);

        if(screen.getLetterCounter() >= lettersMoving.length){
            screen.setLetterCounter(0);


        }
    }

    @Override
    public void update(float dt){
        super.update(dt);



        setPosition(body.getPosition().x - getWidth() / 2 , body.getPosition().y - getHeight() / 2);
        if(screen.getLetterCounter() <= lettersMoving.length-1) {
            setRegion(forwards);

        }

        if((body.getLinearVelocity().x < 0) &&!forwards.isFlipX()){//if he is running left and image not flipped left
            forwards.flip(true,false);//flips image to left

        } else if ((body.getLinearVelocity().x > 0) && forwards.isFlipX()) {//if he running right and image is facing left and running right checks that he not standing still
            forwards.flip(true, false);//flips image to right

        }

        if(!toDestroy) {//todo this was affecting fireball velocity so put if not todestroy needs to be sorted out
            velocity.y = body.getLinearVelocity().y;//sets movent of item from box
            body.setLinearVelocity(velocity);

        }
    }



}
