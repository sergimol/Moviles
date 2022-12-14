package com.example.practica1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.ListIterator;

public class CategorySelect extends State {

    AFont title;

    Button EasyButton;
    AImage EasyButtonImage;

    Button MediumButton;
    AImage MediumButtonImage;

    Button ForestButton;
    AImage ForestButtonImage;

    Button DesertButton;
    AImage DesertButtonImage;

    Button MoneyButton;
    AImage MoneyButtonImage;

    Button BackButton;
    AImage BackButtonImage;

    boolean[] unlocks;
    public String filenameAux;
    GameManager manager;

    public void SetManager(GameManager m){
        manager = m;
    }
    public CategorySelect(GameManager m){
        manager = m;
    }

    public CategorySelect(Bundle savedData) {
        if (savedData != null) {
            Bundle prevScene = savedData.getBundle("Scene");
            if (prevScene != null) {
                switch (prevScene.getInt("SceneType")) {
                    case 0:
                        previous = new InitialState(prevScene, manager);
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

        //escena anterior tiene que ser InitialState
        if (previous == null){
            previous = new InitialState(manager);
            previous.init(e);
        }


        engine = e;

        //Desbloqueo Categorias
        filenameAux = "categorias_0";
        unlocks = manager.loadUnlocks(filenameAux, 4);
        unlocks[0] = true;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));

        title = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        //CategoryButtons
        EasyButtonImage = e.getGraphics().newImage(manager.getStyle() + "EasyButton.png");
        EasyButton = new Button(EasyButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2.5f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[0]);
        MediumButtonImage = e.getGraphics().newImage(manager.getStyle() + "MediumButton.png");
        MediumButton = new Button(MediumButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.8f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[1]);
        ForestButtonImage = e.getGraphics().newImage(manager.getStyle() + "ForestButton.png");
        ForestButton = new Button(ForestButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.4f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[2]);
        DesertButtonImage = e.getGraphics().newImage(manager.getStyle() + "DesertButton.png");
        DesertButton = new Button(DesertButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, unlocks[3]);
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage(manager.getStyle() + "MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));
        //BackButton
        BackButtonImage = e.getGraphics().newImage(manager.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

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
    public void render(AGraphics graphics) {
        //Background
        if (manager.getStyle().equals("Preset"))
            graphics.setColor(0XFFFFB23C);
        else if (manager.getStyle().equals("Red"))
            graphics.setColor(0XFFA64F59);
        else  if(manager.getStyle().equals("Blue"))
            graphics.setColor(0XFF386087);

        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());
        //Title
        if (title != null) {
            String word;
            graphics.setFont(title, title.getSize());
            word = "Selecciona Categor??a";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
        }
        //Buttons
        if (EasyButton != null) {
            if (!EasyButton.getImagen().getName().equals(manager.getStyle() + "EasyButton.png"))
                EasyButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "EasyButton.png"));
            EasyButton.render(graphics);
        }
        if (MediumButton != null) {
            if (!MediumButton.buttonUnlocked) {
                if (!MediumButton.getImagen().getName().equals(manager.getStyle() + "BlockedCategory.png"))
                    MediumButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BlockedCategory.png"));
            } else if (!MediumButton.getImagen().getName().equals(manager.getStyle() + "MediumButton.png"))
                MediumButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "MediumButton.png"));
            MediumButton.render(graphics);
        }
        if (ForestButton != null) {
            if (!ForestButton.buttonUnlocked) {
                if (!ForestButton.getImagen().getName().equals(manager.getStyle() + "BlockedCategory.png"))
                    ForestButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BlockedCategory.png"));
            } else if (!ForestButton.getImagen().getName().equals(manager.getStyle() + "ForestButton.png"))
                ForestButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "ForestButton.png"));
            ForestButton.render(graphics);
        }
        if (DesertButton != null) {
            if (!DesertButton.buttonUnlocked) {
                if (!DesertButton.getImagen().getName().equals(manager.getStyle() + "BlockedCategory.png"))
                    DesertButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BlockedCategory.png"));
            } else if (!DesertButton.getImagen().getName().equals(manager.getStyle() + "DesertButton.png"))
                DesertButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "DesertButton.png"));
            DesertButton.render(graphics);
        }
        if (MoneyButton != null) {
            if (!MoneyButton.getImagen().getName().equals(manager.getStyle() + "MoneyButton.png"))
                MoneyButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "MoneyButton.png"));
            MoneyButton.render(graphics);
        }
        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(manager.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
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
                //creo al siguiente escena y la a??ado al engine

                if (EasyButton.click(o.x, o.y)) {
                    CategoryLevelSelectionState st = new CategoryLevelSelectionState("Easy", manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (MediumButton.click(o.x, o.y)) {
                    if (!unlocks[1]) {
                        unlocks[1] = true;  //Si entra aqui es porque lo hemos desbloqueado comprandolo
                        manager.saveUnlocks(unlocks, filenameAux);
                    }

                    CategoryLevelSelectionState st = new CategoryLevelSelectionState("Medium", manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (ForestButton.click(o.x, o.y)) {
                    if (!unlocks[2]) {
                        unlocks[2] = true;  //Si entra aqui es porque lo hemos desbloqueado comprandolo

                        manager.saveUnlocks(unlocks, filenameAux);
                    }


                    CategoryLevelSelectionState st = new CategoryLevelSelectionState("Forest", manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (DesertButton.click(o.x, o.y)) {
                    if (!unlocks[3]) {
                        unlocks[3] = true;  //Si entra aqui es porque lo hemos desbloqueado comprandolo
                        manager.saveUnlocks(unlocks, filenameAux);
                    }
                    CategoryLevelSelectionState st = new CategoryLevelSelectionState("Desert", manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState(manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (BackButton.click(o.x, o.y)) {
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
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SceneType", 5);
    }
}

