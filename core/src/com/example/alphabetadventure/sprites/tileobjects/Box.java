package com.example.alphabetadventure.sprites.tileobjects;

import static com.example.alphabetadventure.MainClass.manager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.screens.PlayScreen;
import com.example.alphabetadventure.sprites.Letter;
import com.example.alphabetadventure.sprites.items.CollectFireballs;
import com.example.alphabetadventure.sprites.items.NextLetter;
import com.example.alphabetadventure.sprites.items.ItemDef;
import com.example.alphabetadventure.sprites.items.PowerUp;

public class Box extends InterActiveTileObject {
    int a;

    private static TiledMapTileSet tileset;
    private final int BRICKS = 1939;//used to get the tile id of coin which is 28 its 0 indexed so go 1 lower
    private final int BACKGROUND = 0;

    public Box(PlayScreen screen, MapObject object) {
        super(screen, object);

        tileset = map.getTileSets().getTileSet("tilesetMAIN");//gets the tileset
        fixture.setUserData(this);//sets theser data to object itself
        setCategoryFilter(MainClass.BOX_BIT);


    }

    @Override
    public void onHeadHit(Letter letter) {


        if (a == BRICKS) {
            // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/hitbrick.wav", Sound.class).play();
            TiledMapTileLayer.Cell[] adjacentCells = getAdjacentCells();
            for (TiledMapTileLayer.Cell cell : adjacentCells) {
                cell.setTile(tileset.getTile(BACKGROUND));//makes box dissapear and sets new tile gets box cells from Interactiveobject classs
                //cell.getTile().getId();
                setCategoryFilter(MainClass.DESTROYED_BIT);
            }
        } else if (object.getProperties().containsKey("nextletter")) {//checks properties from object in tile list
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 30 / MainClass.PPM),
                    NextLetter.class));
            // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/hitbrick2.wav", Sound.class).play();


        } else if (object.getProperties().containsKey("powerup")) {//checks properties from object in tile list
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 30 / MainClass.PPM),
                    PowerUp.class));
            // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/hitbrick2.wav", Sound.class).play();


        }else if(object.getProperties().containsKey("fireball")){
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 30 / MainClass.PPM),//where item spawn from box
                    CollectFireballs.class));
            // manager.get("sounds/backgroundmusic1.wav", Music.class).stop();
            manager.get("sounds/hitbrick2.wav", Sound.class).play();




        }
        if(!(a == BRICKS)) {
            TiledMapTileLayer.Cell[] adjacentCells = getAdjacentCells();
            for (TiledMapTileLayer.Cell cell : adjacentCells) {
                cell.setTile(tileset.getTile(BRICKS));//makes box dissapear and sets new tile gets box cells from Interactiveobject classs
                a = cell.getTile().getId();
            }
        }
        Hud.addScore(100);

    }
}
