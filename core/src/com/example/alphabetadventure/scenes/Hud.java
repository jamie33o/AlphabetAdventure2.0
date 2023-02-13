package com.example.alphabetadventure.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.PlayScreen;

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
    private Label scoreText;
    public  Button back;
    public  Button forward;
    Image right, left, jump;


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
        scoreText = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(scoreText).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();//expands to end of next label
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

      // Create textures




        right = new Image(new Texture("right arrow.png"));
        left = new Image(new Texture("left_arrow.png"));
        jump = new Image(new Texture("up_arrow.png"));





// Set the positions and sizes of the buttons
        left.setBounds(30,20, 30, 30);

       right.setBounds(90, 20, 30, 30);

        jump.setBounds(630, 20, 30, 30);


// Add listeners to the buttons





// Add the buttons to the stage
        stage.addActor(left);
        stage.addActor(right);
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

        if (PlayScreen.endOfLevel) {

// Create Buttons
            right.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("loadbtn.png")))));
            left.setBounds(30,20, 0, 0);

            right.setBounds(75, 15, 40, 40);

            jump.setBounds(630, 20, 0, 0);


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
