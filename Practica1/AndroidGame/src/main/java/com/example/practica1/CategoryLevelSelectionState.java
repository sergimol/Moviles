package com.example.practica1;

import android.content.Context;
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

    public CategoryLevelSelectionState() {

    }

    @Override
    public void init(AEngine e) {
        engine = e;

        text = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        //BackButton
        BackButtonImage = e.getGraphics().newImage(engine.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage(engine.getStyle() + "MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));


        //Botones niveles
        levelsButtons = new Button[5][4];
        levelUnlocked = e.getGraphics().newImage(engine.getStyle() + "LevelUnlocked.png");
        levelLocked = e.getGraphics().newImage(engine.getStyle() + "LevelLocked.png");


        float ButtonSizeX = 0.18f;
        float ButtonSizeY = 0.18f;

        float originalScaleWidth = e.getGraphics().getCanvasAspectRelationWidth();
        float originalScaleHeight = e.getGraphics().getCanvasAspectRelationHeight();

        //leemos aqui cuantos niveles que hayan sido desbloqueados, array bool

        boolean[] unlocks = loadUnlocks("desbloqueos_prueba4");

        for (int i = 0; i < 20; i++) {
            levelsButtons[i / 4][i % 4] = new Button(unlocks[i] ? levelUnlocked : levelLocked, e.getGraphics().getOriginalWidth() * ((1 + (int) (i % 4)) / 5.0f), e.getGraphics().getOriginalHeight() * ((4 + (int) (i / 4)) / 9.0f), originalScaleWidth * ButtonSizeX, originalScaleWidth * ButtonSizeY);
        }


        //simular un desbloqueo cada vez que abramos esta pestaña
        int i = 0;
        while (i < unlocks.length) {
            if (!unlocks[i]) {
                unlocks[i] = true;
                i = unlocks.length;
            }
            i++;
        }
        saveUnlocks(unlocks, "desbloqueos_prueba4");

    }


    public void saveUnlocks(boolean[] unlocks, String name) {
        Context context = engine.getContext();
        try {
            FileOutputStream f = context.openFileOutput(name,
                    Context.MODE_PRIVATE);
            String text = "" + unlocks.length;
            for (int i = 0; i < unlocks.length; i++) {
                text += ("\n" + (unlocks[i] ? 1 : 0));
            }
            f.write(text.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public boolean[] loadUnlocks(String nombre) {
        Context context = engine.getContext();


        String[] a = context.fileList();

        try {
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = context.openFileInput(nombre);
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(f));

                    int n = 0;
                    String line;
                    int quantity;
                    int read;

                    line = input.readLine();
                    quantity = Integer.parseInt(line);
                    boolean[] unlocks = new boolean[quantity];

                    do {
                        line = input.readLine();
                        if (line != null) {
                            read = Integer.parseInt(line);
                            unlocks[n] = (read == 1);
                            n++;
                        }
                    }
                    while (n < quantity && line != null);

                    f.close();
                    return unlocks;
                }
            }
            return createSaveFiles();
        } catch (Exception e) {
            Log.e("guardados error", e.getMessage(), e);
            return null;
        }
    }

    public boolean[] createSaveFiles() {
        boolean[] res = new boolean[20];
        res[0] = true;
        return res;
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
        if (engine.getStyle().equals("Preset"))
            graphics.setColor(0XFFFFB23C);
        else if (engine.getStyle().equals("Red"))
            graphics.setColor(0XFFA64F59);
        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());

        if (text != null) {
            String word;
            graphics.setFont(text, 20);
            word = "Selecciona Nivel";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
        }

        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(engine.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
        }
        if (MoneyButton != null) {
            if (!MoneyButton.getImagen().getName().equals(engine.getStyle() + "MoneyButton.png"))
                MoneyButton.changeImage(engine.getGraphics().newImage(engine.getStyle() + "MoneyButton.png"));
            MoneyButton.render(graphics);
        }


        if (levelsButtons != null) {
            //Recorro columnas
            for (int i = 0; i < levelsButtons.length; ++i) {
                //Recorro filas
                for (int w = 0; w < levelsButtons[0].length; ++w) {
                    //if (!levelsButtons[i][w].getImagen().getName().equals(engine.getStyle() + "MoneyButton.png"))
                    //    levelsButtons[i][w].changeImage(engine.getGraphics().newImage(engine.getStyle() + "MoneyButton.png"));
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
                    ShopState st = new ShopState();
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (levelsButtons[0][0].click(o.x, o.y)) {
                    try {
                        String p = engine.getContext().getFilesDir().getAbsolutePath();
                        GameState st = new GameState(engine.getAssets(), "00");
                        st.setPrevious(this);
                        engine.setState(st);
                        st.init(engine);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}
