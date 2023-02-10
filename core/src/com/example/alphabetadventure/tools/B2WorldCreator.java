package com.example.alphabetadventure.tools;

import static com.example.alphabetadventure.screens.PlayScreen.gamePort;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.endoflevel.Catapult;
import com.example.alphabetadventure.sprites.endoflevel.Plank;
import com.example.alphabetadventure.sprites.tileobjects.PowerUpBox;
import com.example.alphabetadventure.sprites.enemies.Numbers;
import com.example.alphabetadventure.sprites.tileobjects.NextLetterBox;

public class B2WorldCreator {
    Polygon poly;
    TextureRegion region;
    public Polygon poly1;
    Polygon poly2;
    private Array<Plank> planks;
    private Array<Catapult> catapult;
    public float[] vertices;
    public static float[]  armVertices = null, baseVertices = null;
    private Array<Numbers> numbersArray;

    public MapObject object;
    public B2WorldCreator(PlayScreen screen){
       World world = screen.getWorld();
       TiledMap map = screen.getMap();


        BodyDef bdef = new BodyDef();//creates body for interacting with objects
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        MapProperties vertexString = map.getProperties();
        //creates body for each part of the ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;//create type of movement
            bdef.position.set((rect.getX()+rect.getWidth() / 2)/ MainClass.PPM,(rect.getY()+ rect.getHeight()/2)/ MainClass.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ MainClass.PPM, rect.getHeight() /2/ MainClass.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MainClass.GROUND_BIT;
            fdef.filter.maskBits = MainClass.GROUND_BIT|MainClass.PLANKS_BIT| MainClass.NEXT_LETTER_BOX_BIT|MainClass.POWER_UP_BIT |MainClass.POWER_UP_BOX_BIT |MainClass.OBJECT_BIT|MainClass.ENEMY_HEAD_BIT|MainClass.ENEMY_BIT| MainClass.NEXTLETTER_BIT |MainClass.LETTER_BIT;

            body.createFixture(fdef);
        }

        //creates body for reading when ramps touched
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class)){
            poly = ((PolygonMapObject) object).getPolygon();

            Rectangle rect = poly.getBoundingRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set((rect.getX() +rect.getWidth())/ MainClass.PPM, rect.getY() / MainClass.PPM);
            body = world.createBody(bdef);


            vertices = poly.getVertices();

            for (int i = 0; i < vertices.length; i++) {//resizes the triangle/ramp t
                if (i % 2 == 0) {
                    vertices[i] = vertices[i] * gamePort.getWorldWidth() / MainClass.V_WIDTH;
                } else {
                    vertices[i] = vertices[i] * gamePort.getWorldHeight() / MainClass.V_HEIGHT;
                }
            }

            shape.set(vertices);

            fdef.filter.categoryBits = MainClass.OBJECT_BIT;

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //creates body for items in box coin?
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new NextLetterBox(screen,object);//put all the code in side coin class

        }

        //creates body for emptybox
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

          new PowerUpBox(screen,object);//put all code that was in here in interactive tile object
        }
        //create all goombas
        numbersArray = new Array<Numbers>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            numbersArray.add(new Numbers(screen,rect.getX()/MainClass.PPM, rect.getY()/MainClass.PPM));//put all code that was in here in interactive tile object
        }


        planks = new Array<Plank>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


          planks.add(new Plank(screen,rect.getX()/MainClass.PPM, rect.getY()/MainClass.PPM,object));//put all the code in side coin class

        }



        for(PolygonMapObject object : map.getLayers().get(8).getObjects().getByType(PolygonMapObject.class)){
            if( object.getProperties().containsKey("catapultbase")) {

                this.object = object;
                poly2 = object.getPolygon();
                baseVertices = poly2.getVertices();
                for (int i = 0; i < baseVertices.length; i++) {//resizes the triangle/ramp t
                    if (i % 2 == 0) {
                        baseVertices[i] = baseVertices[i] * gamePort.getWorldWidth() / MainClass.V_WIDTH;
                    } else {
                        baseVertices[i] = baseVertices[i] * gamePort.getWorldHeight() / MainClass.V_HEIGHT;
                    }
                }

            }else if(object.getProperties().containsKey("catapultarm") ){

                poly1 = object.getPolygon();

                armVertices = poly1.getVertices();
                for (int i = 0; i < armVertices.length; i++) {//resizes the triangle/ramp t
                    if (i % 2 == 0 ) {
                        armVertices[i] = armVertices[i] * gamePort.getWorldWidth() / MainClass.V_WIDTH;
                    } else {
                        armVertices[i] = armVertices[i] * gamePort.getWorldHeight() / MainClass.V_HEIGHT;
                    }
                }

            }



        }




    }
    public Array<Numbers> getNumbersArray() {
        return numbersArray;
    }

    public Array<Plank> getPlanks() {

        return planks;
    }


public static float[] getArmVertices(){
        return armVertices;
}


    public static float[] getBaseVertices(){
        return baseVertices;
    }


}
