package com.example.alphabetadventure.endoflevel;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.items.Item;

public class TowerPlanks extends Item {
    public Body b2body;
    private float stateTime;//used to keep track of it

    private boolean setToDestroy;
    private boolean destroyed;
TextureRegion region;
    public TowerPlanks(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        region = new TextureRegion(screen.getAtlas().findRegion("crackedplank"),0,0,60,60);
        //  setBounds(getX(), getY(), 30 / MainClass.PPM, 30 / MainClass.PPMz);//setbounds is set to let know how large to render letter on screen
        //setRegion(lettersInBox[0]);
        setRegion(region);
        velocity = new Vector2(0.7f,0);//sets movement



    }

    @Override
    public void defineItem() {
        BodyDef bdef =new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

       // FixtureDef fdef = new FixtureDef();//creates the body for collision

      /*  fdef.filter.categoryBits = MainClass.ENEMY_BIT;
        //sets wat letter can colide with
        fdef.filter.maskBits = MainClass.GROUND_BIT| MainClass.POWER_UP_BOX_BIT |MainClass.NEXT_LETTER_BOX_BIT |MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.LETTER_BIT;
*/

        //b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTime += dt;

            setRegion(new TextureRegion(screen.getAtlas().findRegion("crackedplank"), 0, 0, 60, 55));

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(region);

    }

    @Override
    public void use(Letter letter) {

    }
}
