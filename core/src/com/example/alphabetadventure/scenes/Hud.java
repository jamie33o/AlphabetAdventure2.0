package com.example.alphabetadventure.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.alphabetadventure.MainClass;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;//when gameworld moves hud stays where it is so create new viewport and new camera for it
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label alphabetAdventureLabel;
    public  Button back;
    public  Button forward;
     public  Button jump;

    public Hud(SpriteBatch sb) {
        worldTimer = 300;
        timeCount =0;
        score = 0;
        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch

        viewport = new FitViewport(MainClass.V_WIDTH, MainClass.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

       //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        alphabetAdventureLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(alphabetAdventureLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();//expands to end of next label
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

      // Create textures
        Texture button1Texture = new Texture(Gdx.files.internal("up_arrow.png"));
        Texture button2Texture = new Texture(Gdx.files.internal("right arrow.png"));
        Texture button3Texture = new Texture(Gdx.files.internal("left_arrow.png"));

// Create Sprites
        Sprite button1Sprite = new Sprite(button1Texture);
        Sprite button2Sprite = new Sprite(button2Texture);
        Sprite button3Sprite = new Sprite(button3Texture);

// Create Buttons
        jump = new Button(new SpriteDrawable(button1Sprite));
        forward= new Button(new SpriteDrawable(button2Sprite));
        back = new Button(new SpriteDrawable(button3Sprite));

// Set the positions and sizes of the buttons
        back.setBounds(30,20, 30, 30);

       forward.setBounds(90, 20, 30, 30);

        jump.setBounds(630, 20, 30, 30);


// Add listeners to the buttons





// Add the buttons to the stage
        stage.addActor(back);
        stage.addActor(forward);
        stage.addActor(jump);


        stage.addActor(table);




    }

    public void update(float dt){
//countdown timer
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
        timeCount =0;

        }
    }

    public static void addScore(int value){

        score+= value;
        scoreLabel.setText(String.format("%06d",score));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
