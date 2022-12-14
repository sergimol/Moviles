package com.example.practica1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;


public class CategoryLevelSelectionState extends State {
    AFont text;
    Button BackButton;
    AImage BackButtonImage;
    Button MoneyButton;
    AImage MoneyButtonImage;
    Button levelsButtons[][];
    AImage levelUnlocked;
    AImage levelLocked;

    String categoryType;
    String categoryTypeAux;
    boolean[] unlocks;
    GameManager manager;

    public void SetManager(GameManager m) {
        manager = m;
    }

    public CategoryLevelSelectionState(String category, GameManager m) {
        manager = m;
        categoryType = category;
    }

    public CategoryLevelSelectionState(Bundle savedData, GameManager m) {
        manager = m;
        if (savedData != null) {
            categoryType = savedData.getString("Category");
        }
    }

    @Override
    public void init(AEngine e) {
        super.init(e);

        //escena anterior tiene que ser CategorySelect
        if (previous == null) {
            previous = new CategorySelect(manager);
            previous.init(e);
        }


        engine = e;
        categoryTypeAux = "_0";
        text = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        //BackButton
        BackButtonImage = e.getGraphics().newImage(manager.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage(manager.getStyle() + "MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));


        //Botones niveles
        levelsButtons = new Button[5][4];
        levelUnlocked = e.getGraphics().newImage(manager.getStyle() + "LevelUnlocked.png");
        levelLocked = e.getGraphics().newImage(manager.getStyle() + "LevelLocked.png");


        float ButtonSizeX = 0.18f;
        float ButtonSizeY = 0.18f;

        float originalScaleWidth = e.getGraphics().getCanvasAspectRelationWidth();
        float originalScaleHeight = e.getGraphics().getCanvasAspectRelationHeight();

        //leemos aqui cuantos niveles que hayan sido desbloqueados, array bool
        unlocks = manager.loadUnlocks(categoryType + categoryTypeAux, 20);

        for (int i = 0; i < 20; i++) {
            levelsButtons[i / 4][i % 4] = new Button(unlocks[i] ? levelUnlocked : levelLocked, e.getGraphics().getOriginalWidth() * ((1 + (int) (i % 4)) / 5.0f), e.getGraphics().getOriginalHeight() * ((4 + (int) (i / 4)) / 9.0f), originalScaleWidth * ButtonSizeX, originalScaleWidth * ButtonSizeY, unlocks[i]);
        }
        //((MainActivity) engine.getContext()).saveUnlocks(unlocks, categoryType + categoryTypeAux);

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

        if (text != null) {
            String word;
            graphics.setFont(text, text.getSize());
            word = "Selecciona Nivel";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
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


        if (levelsButtons != null) {
            //Recorro columnas
            for (int i = 0; i < levelsButtons.length; ++i) {
                //Recorro filas
                for (int w = 0; w < levelsButtons[0].length; ++w) {
                    if (levelsButtons[i][w].isButtonUnlocked()) {
                        if (!levelsButtons[i][w].getImagen().getName().equals(manager.getStyle() + "LevelUnlocked.png"))
                            levelsButtons[i][w].changeImage(engine.getGraphics().newImage(manager.getStyle() + "LevelUnlocked.png"));
                    } else {
                        if (!levelsButtons[i][w].getImagen().getName().equals(manager.getStyle() + "LevelLocked.png"))
                            levelsButtons[i][w].changeImage(engine.getGraphics().newImage(manager.getStyle() + "LevelLocked.png"));
                    }
                    levelsButtons[i][w].render(graphics);
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
                } else if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState(manager);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else {
                    for (int z = 0; z < levelsButtons.length; ++z) {
                        //Recorro filas
                        for (int w = 0; w < levelsButtons[0].length; ++w) {
                            if (levelsButtons[z][w].click(o.x, o.y)) {
                                try {
                                    //String p = engine.getContext().getFilesDir().getAbsolutePath();
                                    GameState st = new GameState(engine.getAssets(), categoryType, "" + z + w, manager);
                                    st.setPrevious(this);
                                    engine.setState(st);
                                    st.init(engine);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {


        outState.putString("Category", categoryType);
        outState.putInt("SceneType", 4);
    }
}
