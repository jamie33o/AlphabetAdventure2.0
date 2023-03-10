package com.example.alphabetadventure.sprites.endoflevel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.items.Item;

public abstract class TowerPlanks extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;
    protected MapObject object;
    protected Rectangle bounds;
protected Rectangle bounds1;
    public TowerPlanks(PlayScreen screen, float x, float y,MapObject object) {
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;

        if (object instanceof RectangleMapObject) {
            this.bounds=((RectangleMapObject) object).getRectangle();
        } else if (object instanceof PolygonMapObject) {
            this.bounds=((PolygonMapObject) object).getPolygon().getBoundingRectangle();
        }
       //  setPosition(x, y);
      //  setBounds(getX(), getY(), 30 / MainClass.PPM, 30 / MainClass.PPM);//size of item in box
        defineItem();
    }

    public abstract void defineItem();


    public abstract void use(Letter letter);


    public void update(float dt) {
      //  if (toDestroy && !destroyed) {//
         //   world.destroyBody(body);
           // destroyed = true;
        }
    //todo messy sort out towerplank so anything that extends from it is gettin the right methods

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }



}

