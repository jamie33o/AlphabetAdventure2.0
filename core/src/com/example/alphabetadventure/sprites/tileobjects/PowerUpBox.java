package com.example.alphabetadventure.sprites.tileobjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.items.ItemDef;
import com.example.alphabetadventure.sprites.items.NextLetter;
import com.example.alphabetadventure.sprites.items.PowerUp;

public class PowerUpBox extends InterActiveTileObject {

    private static TiledMapTileSet tileset;
    private final int BLANK_COIN = 1134;//used to get the
    int a;

    public PowerUpBox(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileset = map.getTileSets().getTileSet("newtileset2");//gets the tileset
        fixture.setUserData(this);//sets theser data to object itself
        setCategoryFilter(MainClass.POWER_UP_BOX_BIT);

    }

    @Override
    public void onHeadHit(Letter letter) {
        // Gdx.app.log("NextLetterBox","collision");//check if method bein called
        // setCategoryFilter(MainClass.DESTROYED_BIT);//stops letters coliding wit brick

        if (a == BLANK_COIN) {
            //play sound
        } else if (object.getProperties().containsKey("powerup")) {//checks properties from object in tile list
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 50 / MainClass.PPM),
                    PowerUp.class));
        }


        TiledMapTileLayer.Cell[] adjacentCells = getAdjacentCells();
        for (TiledMapTileLayer.Cell cell : adjacentCells) {
            cell.setTile(tileset.getTile(BLANK_COIN));//makes box dissapear and sets new tile gets box cells from Interactiveobject classs
            a = cell.getTile().getId();
        }
       /* if ( == BLANK_COIN) { //if cell not empty replace block and play break sound


        }  else {

            //MainClass.manager.get("breakblock.filepath", Sound.class).play();

            // MainClass.manager.get("bump.filepath", Sound.class).play();

             }*/


        Hud.addScore(100);

    }
}