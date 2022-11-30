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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;


public class CategoryLevelSelectionState extends State {
    AFont text;
    Button BackButton;
    AImage BackButtonImage;
    Button MoneyButton;
    AImage MoneyButtonImage;
    Button botonesNiveles[][];
    AImage levelUnlocked;
    AImage levelLocked;

    public CategoryLevelSelectionState() {

    }

    @Override
    public void init(AEngine e) {
        engine = e;

        text = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));

        //BackButton
        BackButtonImage = e.getGraphics().newImage("BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage("MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) (MoneyButton.getSizeY() / 2));


        //Botones niveles
        botonesNiveles = new Button[5][4];
        levelUnlocked = e.getGraphics().newImage("LevelUnlocked.png");
        levelLocked = e.getGraphics().newImage("LevelLocked.png");


        float ButtonSizeX = 0.18f;
        float ButtonSizeY = 0.18f;

        float originalScaleWidth = e.getGraphics().getCanvasAspectRelationWidth();
        float originalScaleHeight = e.getGraphics().getCanvasAspectRelationHeight();

        //leemos aqui cuantos niveles que hayan sido desbloqueados, array bool

        boolean[] desbloqueos = cargardesbloqueos("desbloqueos_prueba4");

        for (int i = 0; i < 20; i++) {
            botonesNiveles[i / 4][i % 4] = new Button(desbloqueos[i] ? levelUnlocked : levelLocked, e.getGraphics().getOriginalWidth() * ((1 + (int) (i % 4)) / 5.0f), e.getGraphics().getOriginalHeight() * ((4 + (int) (i / 4)) / 9.0f), originalScaleWidth * ButtonSizeX, originalScaleWidth * ButtonSizeY);
        }


        //simular un desbloqueo cada vez que abramos esta pestaña
        int i = 0;
        while (i < desbloqueos.length){
            if (!desbloqueos[i]){
                desbloqueos[i] = true;
                i = desbloqueos.length;
            }
            i++;
        }
        guardardesbloqueos(desbloqueos, "desbloqueos_prueba4");

    }


    public void guardardesbloqueos(boolean[] desbloqueos, String nombre){
        Context context = engine.getContexto();
        try {
            FileOutputStream f = context.openFileOutput(nombre,
                    Context.MODE_PRIVATE);
            String texto = "" + desbloqueos.length;
            for (int i = 0; i < desbloqueos.length; i++ ){
                texto += ( "\n" + (desbloqueos[i]?1:0));
            }
            f.write(texto.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("guardado", e.getMessage(), e);
        }
    }

    public boolean[] cargardesbloqueos(String nombre){
        Context context = engine.getContexto();


        String[] a = context.fileList();

        try {
            for (String file : a) {
                if (file.equals(nombre)) {
                    //file exits
                    FileInputStream f = context.openFileInput(nombre);
                    BufferedReader entrada = new BufferedReader(
                            new InputStreamReader(f));

                    int n = 0;
                    String linea;
                    int cantidad;
                    int readed;

                    linea = entrada.readLine();
                    cantidad = Integer.parseInt(linea);
                    boolean desbloqueos[] = new boolean[cantidad];

                    do{
                        linea = entrada.readLine();
                        if (linea != null){
                            readed = Integer.parseInt(linea);
                            desbloqueos[n] = (readed == 1)?true:false;
                            n++;
                        }
                    }
                    while (n < cantidad && linea != null);

                    f.close();
                    return desbloqueos;
                }
            }
            return crearArchivosGuardado();
        } catch (Exception e) {
            Log.e("guardados error", e.getMessage(), e);
            return null;
        }
    }

    public boolean[] crearArchivosGuardado(){
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
        graphics.setColor(0XFFFFB23C);
        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());

        if (text != null) {
            String word;
            graphics.setFont(text, 20);
            word = "Selecciona Nivel";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
        }
        if (BackButton != null)
            BackButton.render(graphics);
        if (MoneyButton != null)
            MoneyButton.render(graphics);

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
                } else if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState();
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                } else if (botonesNiveles[0][0].click(o.x, o.y)) {
                    GameState st = new GameState(4, 4);
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
            }
        }

        engine.getInput().emptyTouchEvents();
    }
}
