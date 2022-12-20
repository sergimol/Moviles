package com.example.practica1;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.ATimer;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;


public class GameState extends State {
    Board board;
    Lives vidas;
    AFont font;

    AImage SurrenderButtonImage;
    Button SurrenderButton;

    AImage CheckButtonImage;
    Button CheckButton;

    AImage AdButtonImage;
    Button AdButton;

    int wrongCount, missingCount;
    boolean showingWrong = false;
    ATimer timer;

    String level;
    String category;

    GameManager manager;
    RewardedAd mRewardedAd;
    Activity main;

    public void SetManager(GameManager m){
        manager = m;
    }

    public GameState(int x, int y, Bundle savedData) {
        vidas = new Lives();


        if (savedData != null) {
            board = new Board();
            vidas.load(savedData);
            board.load(savedData);
            level = savedData.getString("Level");
            category = savedData.getString("Category");
        } else {
            board = new Board(x, y);
        }

        //vidas = (Lives) savedData.getSerializable("corazones");


    }

    public GameState(AssetManager assets, String category_, String level_) throws IOException {
        board = new Board(assets, category_ + level_);
        vidas = new Lives();
        level = level_;
        category = category_;
    }

    @Override
    public void init(AEngine e) {
        super.init(e);

        if (previous == null){
            //dos posibilidades o CategoryLevelSelect, en el caso de que engamos un level,
            // o El del modo random normal a saber como lo hago
            if (category != null){
                previous = new CategoryLevelSelectionState(category, manager);
            }
            else{
                previous = new LevelSelectionState(manager);
            }
            previous.init(e);
        }

        engine = e;

        font = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.3f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        board.init(e, font);

        //SurrenderButton
        SurrenderButtonImage = e.getGraphics().newImage(engine.getStyle() + "SurrenderButton.png");
        SurrenderButton = new Button(SurrenderButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.4f, e.getGraphics().getCanvasAspectRelationHeight() * 0.1f, true);
        SurrenderButton.moveButton((int) (SurrenderButton.getSizeX() / 2), (int) (SurrenderButton.getSizeY() / 2));
        //CheckButton
        CheckButtonImage = e.getGraphics().newImage(engine.getStyle() + "CheckButton.png");
        CheckButton = new Button(CheckButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.4f, e.getGraphics().getCanvasAspectRelationHeight() * 0.1f, true);
        CheckButton.moveButton((int) (e.getGraphics().getOriginalWidth() - CheckButton.getSizeX() / 2), (int) (CheckButton.getSizeY() / 2));
        //AdButton
        AdButtonImage = e.getGraphics().newImage(engine.getStyle() + "CheckButton.png");
        AdButton = new Button(AdButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.4f, e.getGraphics().getCanvasAspectRelationHeight() * 0.1f, true);
        AdButton.moveButton((int) (AdButton.getSizeX() / 2), (int) (e.getGraphics().getOriginalHeight() - AdButton.getSizeY() / 2));
        timer = e.getTimer();


        //setteo de los corazones, que se podria poner mas bonito pero me gusta factorizar todo como la foctoiria de embutidos de mi pueblo dios que buenos estan
        vidas.setHeart(e.getGraphics().newImage(engine.getStyle() + "HeartImageFull.png"));
        vidas.setContainer(e.getGraphics().newImage(engine.getStyle() + "HeartImage.png"));
        vidas.setSpacing(e.getGraphics().getCanvasAspectRelationWidth() * 0.10f);
        vidas.setPos(e.getGraphics().getCanvasAspectRelationWidth() * 0.10f, e.getGraphics().getCanvasAspectRelationHeight() * 0.90f);
        vidas.setSize(e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);

        main = engine.getMainActivity();
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(main, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("GameState", loadAdError.toString());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("GameState", "Ad was loaded.");
                    }
                });
            }
        });
        //vidas.metodoQueDesSerializa();
    }

    @Override
    public void start() {

    }


    @Override
    public void update(double deltaTime) {
        if (timer != null) {
            if (timer.isEnded() && showingWrong) {
                showingWrong = false;
                board.resetRedCells();
            }
        }

        handleInput();
    }

    @Override
    public void render(AGraphics graphics) {
        if (!showingWrong) {
            if (SurrenderButton != null) {
                if (!SurrenderButton.getImagen().getName().equals(engine.getStyle() + "SurrenderButton.png"))
                    SurrenderButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "SurrenderButton.png"));
                SurrenderButton.render(graphics);
            }
            if (CheckButton != null) {
                if (!CheckButton.getImagen().getName().equals(engine.getStyle() + "CheckButton.png"))
                    CheckButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "CheckButton.png"));
                CheckButton.render(graphics);
            }

            if (AdButton != null) {
                if (!AdButton.getImagen().getName().equals(engine.getStyle() + "CheckButton.png"))
                    AdButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "CheckButton.png"));
                AdButton.render(graphics);
            }

        } else {
            String word;
            if (font != null) {
                int auxWrongCount = wrongCount;
                int auxMissingCount = missingCount;
                System.out.println("Missing: " + missingCount + ", Wrong: " + wrongCount);

                graphics.setFont(font, 20);
                if (auxMissingCount > 1)
                    word = "Te falta " + auxMissingCount + " casillas";
                else
                    word = "Te falta " + auxMissingCount + " casilla";

                graphics.setColor(0XFFFF0000);
                graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));

                if (auxWrongCount > 1)
                    word = "Tienes mal " + auxWrongCount + " casillas";
                else
                    word = "Tienes mal " + auxWrongCount + " casilla";

                graphics.setColor(0XFFFF0000);
                graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.15));
            }
        }
        board.render(graphics);
        vidas.render(graphics);
    }

    @Override
    public void handleInput() {
        //check touch cells and buttons to play
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();

        ListIterator<AInput.TouchEvent> ev = events.listIterator();
        while (ev.hasNext()) {
            AInput.TouchEvent o = ev.next();

            if (o.type == AInput.InputTouchType.TOUCH_MOVE) {
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                board.handleInput(xInCanvas, yInCanvas, engine, false);
                //da igual el output
            }
            else if (o.type == AInput.InputTouchType.TOUCH_UP) {
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                //comprobar que esa casilla es correcta y devolver un true or false para restar la vida
                if (!board.handleInput(xInCanvas, yInCanvas, engine, true)) {
                    //en el caso de ser un error
                    vidas.subtractLife();
                    AdButton.unlockButton();
                    if (vidas.getHearts() <= 0) {
                        engine.setState(previous);
                    }
                    System.out.println("vidas restantes: " + vidas.getHearts());
                }
                board.resetAllowChangeStatesCells();
            }
            else if (o.type == AInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON RENDIRSE
                if (SurrenderButton.click(o.x, o.y)) {
                    engine.setState(previous);
                }
                //FUNCIONALIDAD BOTON COMPROBAR
                else if (CheckButton.click(o.x, o.y)) {
                    //wrongCount, missingCount
                    if (!showingWrong) {
                        int a[] = board.checkBoard();
                        wrongCount = a[0];
                        missingCount = a[1];
                        //Si has completado el puzzle
                        if (wrongCount == 0 && missingCount == 0) {
                            FinalState st = new FinalState(board);
                            st.setPrevious(this);
                            engine.setState(st);
                            st.init(engine);
                        } else {
                            timer.setTimer(2);
                            timer.startTimer();
                            showingWrong = true;
                        }
                    }
                }
                else if(AdButton.click(o.x, o.y) && vidas.getHearts() < 3){
                    main.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mRewardedAd != null){
                                mRewardedAd.show(main, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        // Handle the reward.
                                        Log.d("GameState", "The user earned the reward.");
                                        vidas.addLife();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SceneType", 2);
        outState.putInt("x", board.getxSize());
        outState.putInt("y", board.getySize());
        if (category != null) {
            outState.putString("Category", category);
            outState.putString("Level", level);
        }
        vidas.save(outState);
        board.save(outState);
        //outState.putSerializable("corazones", vidas);
        //vidas.metodoQueSerializa();
    }
}
