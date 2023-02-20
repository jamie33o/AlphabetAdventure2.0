package com.example.alphabetadventure.tools;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.MenuScreen;
import com.example.alphabetadventure.screens.PlayScreen;

public class HowTo implements Screen {

    private Game game;
    private BitmapFont font;
    private SpriteBatch batch;

    MenuScreen menuScreen;
    PlayScreen screen;
    TextureRegion powerup,fireball,box,enemy,ramp;
    Stage stage;

    public HowTo(Game game,PlayScreen screen,MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
        this.screen = screen;
        this.game = game;
       font = new BitmapFont();
        font.getData().setScale(2.5f); // double the font size
        batch = new SpriteBatch();
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        powerup = new TextureRegion(screen.getAtlas().findRegion("powerUp"),0,0,60,60);
        fireball = new TextureRegion(screen.getAtlas().findRegion("loadbtn"),0,0,100,100);
        box = new TextureRegion(screen.getAtlas().findRegion("crate1"),0,0,150,150);
        enemy = new TextureRegion(screen.getAtlas().findRegion("eraser"),0,0,60,60);

// add the image and text as separate cells

        table.add(new Image(box)).pad(10);
        table.row();
        table.add(new Label("Jump to hit the bottom of the box and either a fireball or power up will come out...\nYou get 100 points for every box you brake your score is at the top of the screen..", new Label.LabelStyle(font, Color.WHITE)));
        table.row();
        table.add(new Image(powerup)).padBottom(20);
        table.row();
        table.add(new Label("Collect the power ups to use when you need more speed going over ramps...\nWhen you collect a power up, the power up button will appear above the jump button...", new Label.LabelStyle(font, Color.WHITE)));
        table.row();
        table.add(new Image(fireball)).pad(10);
        table.row();
        table.add(new Label("Collect the fireballs to use at the end of the level to knock the tower down and save the baby letter...\nWhen you collect a fireball, it will show at the top of the screen, how many you have collected...", new Label.LabelStyle(font, Color.WHITE)));
        table.row();
        table.add(new Image(enemy)).pad(10);
        table.row();
        table.add(new Label("Jump on the eraser's head to kill him...\nif the eraser runs into you it will kill you...", new Label.LabelStyle(font, Color.WHITE)));

// add the table to the stage
        stage.addActor(table);



    }

    @Override
    public void show() {
        // called when the screen becomes the current screen of the game
    }

    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glClearColor(0, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "HOW TO PLAY", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.9f, 0, 1, false);
      stage.draw();
        font.draw(batch, "BACK", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.1f, 0, 1, false);
        batch.end();

        if(Gdx.input.isTouched())
            game.setScreen(menuScreen);
        // check for touch input on "BACK" button

    }


    @Override
    public void resize(int width, int height) {
        // called when the screen is resized
    }

    @Override
    public void pause() {
        // called when the game is paused
    }

    @Override
    public void resume() {
        // called when the game is resumed
    }

    @Override
    public void hide() {
        // called when the screen is no longer the current screen of the game
    }

    @Override
    public void dispose() {
        // dispose of any resources used by the screen
        font.dispose();
        batch.dispose();
    }
}
