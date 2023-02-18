package com.example.alphabetadventure.tools;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.screens.MenuScreen;
import com.example.alphabetadventure.screens.PlayScreen;

public class HowTo implements Screen {

    private Game game;
    private BitmapFont font;
    private SpriteBatch batch;

    PlayScreen screen;
    public HowTo(Game game,PlayScreen screen) {
        this.screen = screen;
        this.game = game;
       font = new BitmapFont();
        font.getData().setScale(3); // double the font size
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // called when the screen becomes the current screen of the game
    }

    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "HOW TO PLAY", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.9f, 0, 1, false);
        font.draw(batch, "[GAME INSTRUCTIONS GO HERE]", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0, 1, false);
        font.draw(batch, "BACK", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.1f, 0, 1, false);
        batch.end();
        if(Gdx.input.isTouched())
            game.setScreen(new MenuScreen(game,screen, PlayScreen.gameState.PAUSED));
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
