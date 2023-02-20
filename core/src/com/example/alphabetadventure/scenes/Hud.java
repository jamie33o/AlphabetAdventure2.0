package com.example.alphabetadventure.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.MenuScreen;
import com.example.alphabetadventure.screens.PlayScreen;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;//when gameworld moves hud stays where it is so create new viewport and new camera for it
    public Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private  Integer score;
    private  Integer fireballcounter;
    private  Integer powerupCount;

    private Label countdownLabel;
    private Label scoreLabel;
    private  Label fireballlabel;

    private  Label powerupLabel;


    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label scoreText;
    private Label fireBallText;
    private Label powerupText;
    public Image rightImg, leftImg, jumpImg,powerupImg, menuScreen;

    PlayScreen screen;


    public Hud(SpriteBatch sb, final PlayScreen screen) {
        this.screen = screen;
        worldTimer = 300;
        timeCount =0;
        score = 0;
        fireballcounter=0;
        powerupCount = 0;
        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch

        viewport = new FitViewport(MainClass.V_WIDTH, MainClass.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);
       //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fireballlabel =new Label(String.format("%02d", fireballcounter), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        powerupLabel =new Label(String.format("%02d", powerupCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));



        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format("%01d", screen.level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreText = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fireBallText = new Label("FireBalls", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        powerupText = new Label("Power Ups", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        menuScreen = new Image(new TextureRegion(screen.getAtlas().findRegion("menu"), 0, 0, 30, 30));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(scoreText).expandX().padTop(10);

        table.add(worldLabel).expandX().padTop(10);
        table.add(fireBallText).expandX().padTop(10);
        table.add(powerupText).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();//expands to end of next label
        table.add(levelLabel).expandX();
        table.add(fireballlabel).expandX();
        table.add(powerupLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(menuScreen).expandX();

      // Create textures




        rightImg = new Image(new TextureRegion(screen.getAtlas().findRegion("right arrow"), 0, 0, 70, 60));
        leftImg = new Image(new TextureRegion(screen.getAtlas().findRegion("left_arrow"), 0, 0, 70, 60));
        jumpImg = new Image(new TextureRegion(screen.getAtlas().findRegion("up_arrow"), 0, 0, 70, 60));
        powerupImg = new Image(new TextureRegion(screen.getAtlas().findRegion("powerUp"), 0, 0, 70, 60));






// Set the positions and sizes of the buttons
        leftImg.setBounds(30,20, 30, 30);

        rightImg.setBounds(90, 20, 30, 30);

        jumpImg.setBounds(630, 20, 40, 30);

        powerupImg.setBounds(650, 60, 40, 30);



// Add listeners to the buttons



// Add the buttons to the stage
        stage.addActor(leftImg);
        stage.addActor(rightImg);
        stage.addActor(jumpImg);
        stage.addActor(powerupImg);


        stage.addActor(table);

    }

    public void update(float dt){

        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
        timeCount =0;

        }

        if(powerupCount == 0){
            powerupImg.setBounds(0,0,0,0);
        }else {
            powerupImg.setBounds(630, 60, 40, 30);

        }
        if (screen.isEndOfLevel()) {

// Create Buttons
            rightImg.setDrawable(new SpriteDrawable(new Sprite(new TextureRegion(screen.getAtlas().findRegion("loadbtn"), 0, 0, 100, 100))));
            leftImg.setBounds(30,20, 0, 0);

            rightImg.setBounds(75, 15, 40, 40);

            jumpImg.setBounds(630, 20, 0, 0);


        }


    }
    public  void addPowerUp(int value){

        powerupCount+= value;
        powerupLabel.setText(String.format("%02d",powerupCount));
    }
    public  int getPowerUpCounter(){

        return powerupCount;
    }
    public  void addFireball(int value){

        fireballcounter+= value;
        fireballlabel.setText(String.format("%02d",fireballcounter));
    }

    public  int getFireballcounter(){

       return fireballcounter;
    }
    public void addScore(int value){

        score+= value;
        scoreLabel.setText(String.format("%06d",score));
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
