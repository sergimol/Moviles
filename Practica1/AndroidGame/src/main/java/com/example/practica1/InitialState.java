package com.example.practica1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

//import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class InitialState extends State {

    AFont title;
    AFont playButton;

    Button StoryButton;
    AImage StoryButtonImage;

    Button ArcadeButton;
    AImage ArcadeButtonImage;

    Button MoneyButton;
    AImage MoneyButtonImage;

    GameManager manager;

    AdView adView;

    public void SetManager(GameManager m){
        manager = m;
    }

    public InitialState(GameManager m){
        manager = m;
    }
    public InitialState(Bundle savedData, GameManager m){
        manager = m;
        if (savedData != null){
            Bundle PrevScene = savedData.getBundle("Scene");
            if (PrevScene != null){
                switch (PrevScene.getInt("SceneType")){
                    case 0:
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public void init(AEngine e) {
        super.init(e);
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));
        title = e.getGraphics().newFont("CuteEasterFont.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        playButton = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        //StoryButton
        StoryButtonImage = e.getGraphics().newImage(engine.getStyle() + "Story.png");
        StoryButton = new Button(StoryButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.5f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, true);
        //ArcadeButton
        ArcadeButtonImage = e.getGraphics().newImage(engine.getStyle() + "ArcadeButton.png");
        ArcadeButton = new Button(ArcadeButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f,true);
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage(engine.getStyle() + "MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f,true);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));
        //Font Button
        //myBoton = new Button(playButton, "Jugar", e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2, e.getGraphics().getOriginalWidth() * 0.3f, e.getGraphics().getOriginalHeight() * 0.1f, 0xFFFF8000, 20);
        //Image Button

        adView = new AdView(engine.getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        Activity main = engine.getMainActivity();
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                adView.setBackgroundColor(Color.RED);
                Log.d("InitialState", "Ad was loaded.");
            }
        });
    }

    @Override
    public void start() {
        engine.getAudioMusic().playSound("music");
        engine.getAudioMusic().loop("music", true);
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(AGraphics graphics) {

        //Background
        if(engine.getStyle().equals("Preset"))
            graphics.setColor(0XFFFFB23C);
        else  if(engine.getStyle().equals("Red"))
            graphics.setColor(0XFFA64F59);

        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());
        //Buttons
        if (StoryButton != null) {
            if (!StoryButton.getImagen().getName().equals(engine.getStyle() + "Story.png"))
                StoryButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "Story.png"));
            StoryButton.render(graphics);
        }

        if (ArcadeButton != null) {
            if (!ArcadeButton.getImagen().getName().equals(engine.getStyle() + "ArcadeButton.png"))
                ArcadeButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "ArcadeButton.png"));
            ArcadeButton.render(graphics);
        }
        if (MoneyButton != null) {
            if (!MoneyButton.getImagen().getName().equals(engine.getStyle() + "MoneyButton.png"))
                MoneyButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "MoneyButton.png"));
            MoneyButton.render(graphics);
        }

        //renderizar otro objeto como puede ser el boton
        String word;
        if (title != null) {
            graphics.setFont(title, 20);
            word = "NANOGRAMOS";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.2));
        }
    }

    @Override
    public void handleInput() {
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();
            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
                //FUNCIONALIDAD BOTON JUGAR
                //cambio a la siguiente escena
                //creo al siguiente escena y la añado al engine

                if (StoryButton.click(o.x, o.y)) {
                    CategorySelect st = new CategorySelect(manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }

                if (ArcadeButton.click(o.x, o.y)) {
                    LevelSelectionState st = new LevelSelectionState(null);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState(manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("SceneType", 0);
        //hay que meter la escena principal
    }

}
