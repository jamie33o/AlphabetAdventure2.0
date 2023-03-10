package com.example.alphabetadventure.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.example.alphabetadventure.AdHandler;
import com.example.alphabetadventure.MainClass;
import com.example.alphabetadventure.scenes.Hud;
import com.example.alphabetadventure.tools.HowTo;




public class MenuScreen extends PlayScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    PlayScreen screen;
    private Game game;
    public Image play,playAgain, restart,playNextLevel, instructions, exit;

    gameState currentState;
    Hud hud;

    Image powerup;
    Image fireball;
    Image box,lettera,letterf;
    Image enemy;




    public MenuScreen(final Game game ,PlayScreen screen,gameState currentState,Hud hud) {
        super((MainClass) game);

        this.hud = hud;
        this.currentState = currentState;
        this.game = game;
        this.screen = screen;
        viewport = new FitViewport(MainClass.V_WIDTH, MainClass.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MainClass) game).batch);
        Gdx.input.setInputProcessor(stage);



        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);//takes up entire stage

        Label startgame = new Label("START GAME", font);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label paused = new Label("PAUSED", font);
        Label levelComplete = new Label("LEVEL " + level+ " COMPLETED!!!", font);

        play = new Image(new TextureRegion(getAtlas().findRegion("start"), 0, 0, 100, 50));
        playAgain = new Image(new TextureRegion(getAtlas().findRegion("playagain"), 0, 0, 100, 50));

        restart = new Image(new TextureRegion(getAtlas().findRegion("resume"), 0, 0, 100, 50));
        playNextLevel = new Image(new TextureRegion(getAtlas().findRegion("nextlevel"), 0, 0, 100, 50));

        instructions = new Image(new TextureRegion(getAtlas().findRegion("howto"), 0, 0, 100, 50));
        exit = new Image(new TextureRegion(getAtlas().findRegion("exit"), 0, 0, 100, 50));

        powerup = new Image(new TextureRegion(screen.getAtlas().findRegion("powerUp"),0,0,60,60));
        fireball = new Image(new TextureRegion(screen.getAtlas().findRegion("loadbtn"),0,0,100,100));
        box = new Image(new TextureRegion(screen.getAtlas().findRegion("crate1"),0,0,150,150));
        enemy = new Image(new TextureRegion(screen.getAtlas().findRegion("eraser"),0,0,60,60));
        lettera = new Image(new TextureRegion(screen.getAtlas().findRegion("a_standing"),0,0,65,65));
        letterf = new Image(new TextureRegion(screen.getAtlas().findRegion("f_standin"),0,0,60,60));

        powerup.setBounds(600,260,50,50);
        fireball.setBounds(20,20,50,50);
        box.setBounds(600,20,50,50);
        enemy.setBounds(20,260,50,50);
        lettera.setBounds(80,130,50,50);
        letterf.setBounds(520,130,50,50);



        ((MainClass) game).handler.showAds(true);

        if (currentState == gameState.GAMEOVER) {


           table.add(gameOverLabel).expandX();
           table.row();
           table.add(playAgain).expandX();
        } else if (currentState == gameState.PAUSED) {
           table.add(paused).expandX();
           table.row();
           table.add(restart).expandX();


        } else if (currentState == gameState.NEXTLEVEL) {
           table.add(levelComplete).expandX();
           table.row();
           table.add(playNextLevel).expandX();
            endOfLevel = false;


        }else{
            table.add(startgame).expandX();
            table.row();
            table.add(play).expandX();


        }

       table.row();
            table.add(instructions).expandX();
            table.row();
            table.add(exit).expandX();




            stage.addActor(powerup);
        stage.addActor(enemy);
        stage.addActor(fireball);
        stage.addActor(box);
        stage.addActor(lettera);
        stage.addActor(letterf);


            stage.addActor(table);





    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = gameState.UNPAUSED;
                game.setScreen(new PlayScreen((MainClass) game));
                ((MainClass) game).handler.showAds(false);

                dispose();
            }
        });
        playAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = gameState.UNPAUSED;
                game.setScreen(new PlayScreen((MainClass) game));
                ((MainClass) game).handler.showAds(false);

                dispose();
            }
        });


        restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = gameState.UNPAUSED;
                game.setScreen(screen);

                ((MainClass) game).handler.showAds(false);
                dispose();
            }
        });

        playNextLevel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = gameState.UNPAUSED;
                screen.level = 2;
                game.setScreen(new PlayScreen((MainClass) game));
                ((MainClass) game).handler.showAds(false);

                dispose();
            }
        });
        instructions.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HowTo((MainClass) game,screen,MenuScreen.this));

            }
        });



        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                dispose();
            }
        });








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
