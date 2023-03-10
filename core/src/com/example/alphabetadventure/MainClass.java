package com.example.alphabetadventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.alphabetadventure.screens.MenuScreen;
import com.example.alphabetadventure.screens.PlayScreen;

public class MainClass extends Game {//changed to game from application adapter
	public SpriteBatch batch;//made public so all screen can have access
	//only use one spritebatch holds all images render them to screen
	public static final int V_WIDTH = 695;//was 400
	public static final int V_HEIGHT = 345;//was208

	//PPM also changes speed
	public static final float PPM = 100;//was hundres but had to lower cause of ramps

	//every fixture already has a default bit of 1
	public static final short NOTHING_BIT = 0;

	public static final short GROUND_BIT = 1;
	public static final short LETTER_BIT = 2;
	public static final short COLLECT_FIREBALL_BIT= 4;
	public static final short BOX_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;

	public static final short NEXTLETTER_BIT = 256;
	public static final short LETTER_HEAD_BIT = 512;
	public static final short POWER_UP_BIT = 1024;
	public static final short PLANKS_BIT = 2048;
	public static final short FIREBALL_BIT = 4096;

	public static final short CATAPULT_BASE_BIT = -4096;
	public static final short CATAPULT_ARM_BIT = 8192;
	public static final short DOOR_BIT = -8192;

	public static final short CATAPULT_ARM_CATCH_BIT = 16384;
	public static final short BABY_LETTER_BIT = -16384;





	//todo put in classes that need it using in static way can cause probems
	public static AssetManager manager;
	private PlayScreen playScreen;

	public AdHandler handler;

	public MainClass(AdHandler handler){
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		playScreen = new PlayScreen(this);

		playScreen.currentState = PlayScreen.gameState.STARTGAME;

		setScreen(new MenuScreen(this,playScreen,playScreen.currentState,playScreen.hud));

		//setScreen(new PlayScreen(this));//pass game itself  so it can set screens
	           //synchronized way to load should use Asynchronized
	    manager = new AssetManager();
		manager.load("sounds/catapultfired1.wav", Sound.class);
		manager.load("sounds/endoflevel.wav", Sound.class);
		manager.load("sounds/fireballhitsomething.wav", Sound.class);
		manager.load("sounds/hitbrick2.wav", Sound.class);
		manager.load("sounds/hitbrick.wav", Sound.class);
		manager.load("sounds/jump.wav", Sound.class);
		manager.load("sounds/nextletter.wav", Sound.class);
		manager.load("sounds/powerupbox.wav", Sound.class);
		manager.load("sounds/dead.wav", Sound.class);
		manager.load("sounds/jumponenemy.wav", Sound.class);

		manager.finishLoading();
		}

	@Override
	public void render () {
		super.render();//deligate render method to wat ever screen is active Playscreen
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();

	}
}
