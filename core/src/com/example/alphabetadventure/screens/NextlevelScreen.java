package com.example.alphabetadventure.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.alphabetadventure.MainClass;



    public class NextlevelScreen implements Screen {
        private Viewport viewport;
        private Stage stage;

        private Game game;

        public NextlevelScreen(Game game){
            this.game = game;
            viewport = new FitViewport(MainClass.V_WIDTH, MainClass.V_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, ((MainClass) game).batch);

            Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

            Table table = new Table();
            table.center();
            table.setFillParent(true);//takes up entire stage

            Label nextLevelLabel = new Label("Level Cleared...Well Done!!", font);
            Label playNextLevelLabel = new Label("Click to Play Next Level", font);

            table.add(nextLevelLabel).expandX();
            table.row();
            table.add(playNextLevelLabel).expandX().padTop(10f);

            stage.addActor(table);
        }

        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
            if(Gdx.input.justTouched()) {
                game.setScreen(new PlayScreen((MainClass) game));
                dispose();
            }
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            stage.dispose();
        }
    }


