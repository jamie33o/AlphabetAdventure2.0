package com.example.alphabetadventure.sprites.tileobjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;

import java.awt.Label;

public abstract class InterActiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;
    protected PlayScreen screen;
protected MapObject object;
    public InterActiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds=((RectangleMapObject) object).getRectangle();


        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ MainClass.PPM, (bounds.getY() + bounds.getHeight()/2)/MainClass.PPM);


        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2/MainClass.PPM,bounds.getHeight()/2/MainClass.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);


    }

    public abstract void onHeadHit(Letter letter);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell[] getAdjacentCells(){//gets 4 cell of box hit on layer of tiled map so u can make it disapear
        float x = body.getPosition().x * MainClass.PPM/16 ;//this is the x cordinate of first cell
        float y = body.getPosition().y * MainClass.PPM/16;//this is y cordinate of first cell
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);//this gets the layer
        TiledMapTileLayer.Cell[] cells = new TiledMapTileLayer.Cell[4]; //thiss creates cell array
        cells[0] = layer.getCell((int)x, (int)y);// theses change the cordinates
        cells[1] = layer.getCell((int)x-1, (int)y);
        cells[2] = layer.getCell((int)x, (int)y-1);
        cells[3] = layer.getCell((int)x-1, (int)y-1);
        return cells;//and this  returns the array
    }

    //-1 to get cell beside it on x or y axis


}
