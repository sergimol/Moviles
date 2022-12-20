package com.example.practica1;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ListIterator;


public class ShopState extends State {
    Button BackButton;
    AImage BackButtonImage;

    Button PresetStyleButton;
    AImage PresetStyleButtonImage;

    Button RedStyleButton;
    AImage RedStyleButtonImage;
    AImage RedStyleButtonShopImage;

    Button BlueStyleButton;
    AImage BlueStyleButtonImage;
    AImage BlueStyleButtonShopImage;


    String unlockType;
    int[] moneyUnlocks; //Guarda lo que cuesta cada desbloqueo


    AFont regularText;
    Button moneyCuantityButton;
    AImage moneyCuantityButtonImage;

    GameManager manager;

    public void SetManager(GameManager m) {
        manager = m;
    }

    public ShopState(GameManager m) {
        manager = m;
    }

    public ShopState(Bundle savedData) {
        if (savedData != null) {
            switch (savedData.getInt("SceneType")) {
                case 0:
                    previous = new InitialState(savedData, manager);
                    break;
                case 1:
                    previous = new LevelSelectionState(savedData, manager);
                    break;
                case 2:
                    previous = new GameState(savedData.getInt("x"), savedData.getInt("y"), savedData, manager);
                    break;
                default:
                    previous = new InitialState(manager);
                    break;
            }
        }
    }


    @Override
    public void init(AEngine e) {
        super.init(e);
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));

        //Desbloqueo de tienda
        unlockType = "compras_0";
        moneyUnlocks = manager.loadUnlocksINT(unlockType, 2);

        //BackButton
        BackButtonImage = e.getGraphics().newImage(manager.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

        //Preset
        PresetStyleButtonImage = e.getGraphics().newImage("PresetLevelUnlocked.png");
        PresetStyleButton = new Button(PresetStyleButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, manager.unlocks[0]);

        //RedButton
        RedStyleButtonImage = e.getGraphics().newImage("RedLevelUnlocked.png");
        RedStyleButtonShopImage = e.getGraphics().newImage("RedMoneyButton.png");
        RedStyleButton = new Button(RedStyleButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.5f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, manager.unlocks[1], moneyUnlocks[0], RedStyleButtonShopImage);

        //BlueButton
        BlueStyleButtonImage = e.getGraphics().newImage("BlueLevelUnlocked.png");
        BlueStyleButtonShopImage = e.getGraphics().newImage("BlueMoneyButton.png");
        BlueStyleButton = new Button(BlueStyleButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.2f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, manager.unlocks[2], moneyUnlocks[1], BlueStyleButtonShopImage);


        //MoneyAmount
        moneyCuantityButtonImage = e.getGraphics().newImage(manager.getStyle() + "LevelUnlocked.png");
        moneyCuantityButton = new Button(moneyCuantityButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.7f, e.getGraphics().getCanvasAspectRelationWidth() * 0.1f, true);
        moneyCuantityButton.moveButton((int) (e.getGraphics().getOriginalWidth() - moneyCuantityButton.getSizeX() / 2), (int) (moneyCuantityButton.getSizeY() / 2));
        regularText = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

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
        else if (manager.getStyle().equals("Blue"))
            graphics.setColor(0XFF386087);

        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());

        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(manager.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
        }

        if (PresetStyleButton != null)
            PresetStyleButton.render(graphics);
        if (RedStyleButton != null)
            RedStyleButton.render(graphics);
        if (BlueStyleButton != null)
            BlueStyleButton.render(graphics);
        //MoneyCuantity
        if (moneyCuantityButton != null) {
            if (!moneyCuantityButton.getImagen().getName().equals(manager.getStyle() + "LevelUnlocked.png"))
                moneyCuantityButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "LevelUnlocked.png"));
            moneyCuantityButton.render(graphics);
        }
        if (regularText != null) {
            graphics.setFont(regularText, 80);
            String word = manager.getMoney() + "";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, (int) (engine.getGraphics().getOriginalWidth() - moneyCuantityButton.getSizeX() / 2 - graphics.getFontWidth(word) / 2), (int) (moneyCuantityButton.getSizeY() / 1.7f));
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
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous);
                    manager.saveUnlocks(manager.unlocks, manager.categoryType);
                    manager.saveUnlocksINT(moneyUnlocks, unlockType);
                    manager.saveMoney();

                } else if (PresetStyleButton.click(o.x, o.y)) {
                    manager.setStyle("Preset");
                } else if (RedStyleButton.click(o.x, o.y)) {
                    //
                    if (manager.getMoney() >= RedStyleButton.moneyToUnlock) {
                        //Si entra aqui es porque lo hemos desbloqueado
                        if (!manager.unlocks[1]) {
                            //Unlockear boton
                            manager.unlocks[1] = true;
                            RedStyleButton.unlockButton();
                            manager.saveUnlocks(manager.unlocks, manager.categoryType);
                        }

                        if (RedStyleButton.moneyToUnlock == 0) {
                            manager.setStyle("Red");
                        } else {
                            //Comprarlo
                            manager.restMoney(RedStyleButton.moneyToUnlock);
                            RedStyleButton.moneyToUnlock = 0;
                            moneyUnlocks[0] = 0;
                            manager.saveUnlocksINT(moneyUnlocks, unlockType);
                        }
                    }
                } else if (BlueStyleButton.click(o.x, o.y)) {
                    //
                    if (manager.getMoney() >= BlueStyleButton.moneyToUnlock) {
                        //Si entra aqui es porque lo hemos desbloqueado
                        if (!manager.unlocks[2]) {
                            //Unlockear boton
                            manager.unlocks[2] = true;
                            BlueStyleButton.unlockButton();
                            manager.saveUnlocks(manager.unlocks, manager.categoryType);
                        }

                        if (BlueStyleButton.moneyToUnlock == 0) {
                            manager.setStyle("Blue");
                        } else {
                            //Comprarlo
                            manager.restMoney(BlueStyleButton.moneyToUnlock);
                            BlueStyleButton.moneyToUnlock = 0;
                            moneyUnlocks[1] = 0;
                            manager.saveUnlocksINT(moneyUnlocks, unlockType);
                        }
                    }
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        //
        // outState.putInt("SceneType", 3);

        //me chusta 3 escenas (sinonimo de pingo)
        //tal y como me dijo tony, nunca guardaremos el estado en Shop, saltaremos siempre a su anterior estado para ahorrarnos el backtracing diabolico
        //if it isn't the consecuences of my actions
        // | | | | | | | | | | | | | | |
        // v v v v v v v v v v v v v v v
        manager.saveUnlocks(manager.unlocks, manager.categoryType);
        manager.saveUnlocksINT(moneyUnlocks, unlockType);
        manager.saveMoney();
        previous.onSaveInstanceState(outState);
        //By Diengo

    }
}
