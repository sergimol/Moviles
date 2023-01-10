package com.example.practica1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

//import java.awt.Font;
import java.util.List;
import java.util.ListIterator;

public class LevelSelectionState extends State {
    AFont title;
    AFont regularText;

    Button BackButton;
    AImage BackButtonImage;
    Button MoneyButton;
    AImage MoneyButtonImage;
    Button botonesNiveles[][];

    GameManager manager;

    public void SetManager(GameManager m){
        manager = m;
    }

    public LevelSelectionState(GameManager m){
        manager = m;
    }
    public LevelSelectionState(Bundle savedData,GameManager m){
        manager = m;
        if (savedData != null) {
                switch (savedData.getInt("SceneType")) {
                    case 0:
                        previous = new InitialState(savedData, manager);
                        break;
                    default:
                        break;
                }
        }
    }
    @Override
    public void init(AEngine e) {
        super.init(e);

        //escena anterior tiene que ser InitialState
        if (previous == null){
            previous = new InitialState(manager);
            previous.init(e);
        }

        engine = e;

        //BackButton
        BackButtonImage = e.getGraphics().newImage(manager.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage(manager.getStyle() + "MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));

        title = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.5f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        regularText = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.7f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        botonesNiveles = new Button[2][3];

        botonesNiveles[0][0] = new Button(regularText, "4x4", e.getGraphics().getOriginalWidth() * (1 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);
        botonesNiveles[0][1] = new Button(regularText, "5x5", e.getGraphics().getOriginalWidth() * (2 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);
        botonesNiveles[0][2] = new Button(regularText, "5x10", e.getGraphics().getOriginalWidth() * (3 / 4.0f), e.getGraphics().getOriginalHeight() * 0.4f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);

        botonesNiveles[1][0] = new Button(regularText, "8x8", e.getGraphics().getOriginalWidth() * (1 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);
        botonesNiveles[1][1] = new Button(regularText, "10x10", e.getGraphics().getOriginalWidth() * (2 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);
        botonesNiveles[1][2] = new Button(regularText, "10x15", e.getGraphics().getOriginalWidth() * (3 / 4.0f), e.getGraphics().getOriginalHeight() * 0.6f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.2f, 0Xff808080, regularText.getSize(), true);

        Activity main = engine.getMainActivity();
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdView adView = main.findViewById(R.id.adView);
                adView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(AGraphics graphics) {

        //Background
        if (manager.getStyle().equals("Preset"))
            graphics.setColor(0XFFFFB23C);
        else if (manager.getStyle().equals("Red"))
            graphics.setColor(0XFFA64F59);
        else  if(manager.getStyle().equals("Blue"))
            graphics.setColor(0XFF386087);
        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());


        if (title != null) {
            String word;
            word = "Selecciona el tamaño del puzzle";
            graphics.setColor(0XFF000000);
            graphics.setFont(title, title.getSize());
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.2));
        }

        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(manager.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
        }
        if (MoneyButton != null) {
            if (!MoneyButton.getImagen().getName().equals(manager.getStyle() + "MoneyButton.png"))
                MoneyButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "MoneyButton.png"));
            MoneyButton.render(graphics);
        }


        if (botonesNiveles != null) {
            //Recorro columnas
            for (int i = 0; i < botonesNiveles.length; ++i) {
                //Recorro filas
                for (int w = 0; w < botonesNiveles[0].length; ++w) {
                    botonesNiveles[i][w].render(graphics);
                }
            }

        }
    }

    @Override
    public void handleInput() {
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();

        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous);
                    Activity main = engine.getMainActivity();
                    main.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AdView adView = main.findViewById(R.id.adView);
                            AdRequest adRequest = new AdRequest.Builder().build();
                            adView.loadAd(adRequest);
                            adView.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState(manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[0][0].click(o.x, o.y)) {
                    GameState st = new GameState(4, 4, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[0][1].click(o.x, o.y)) {
                    GameState st = new GameState(5, 5, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[0][2].click(o.x, o.y)) {
                    GameState st = new GameState(5, 10, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[1][0].click(o.x, o.y)) {
                    GameState st = new GameState(8, 8, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[1][1].click(o.x, o.y)) {
                    GameState st = new GameState(10, 10, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[1][2].click(o.x, o.y)) {
                    GameState st = new GameState(10, 15, null, manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {


        outState.putInt("SceneType", 1);
        //de haber una PrevScene para seguir con este bucle de Bundles
    }

}
