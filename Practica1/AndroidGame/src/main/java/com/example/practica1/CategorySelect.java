package com.example.practica1;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

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

    public CategorySelect() {
    }


    @Override
    public void init(AEngine e) {
        engine = e;
        //System.out.println("Escala: " + e.getGraphics().getScale() + "Math.log(): " + Math.log(e.getGraphics().relationAspectDimension()));

        title = e.getGraphics().newFont("Larissa.ttf", 1, (int) (0.8f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        //CategoryButtons
        EasyButtonImage = e.getGraphics().newImage("EasyButton.png");
        EasyButton = new Button(EasyButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 2.5f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        MediumButtonImage = e.getGraphics().newImage("MediumButton.png");
        MediumButton = new Button(MediumButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.8f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        ForestButtonImage = e.getGraphics().newImage("BlockedCategory.png");
        ForestButton = new Button(ForestButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.4f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        DesertButtonImage = e.getGraphics().newImage("BlockedCategory.png");
        DesertButton = new Button(DesertButtonImage, e.getGraphics().getOriginalWidth() / 2, e.getGraphics().getOriginalHeight() / 1.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.6f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        //MoneyButton
        MoneyButtonImage = e.getGraphics().newImage("MoneyButton.png");
        MoneyButton = new Button(MoneyButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        MoneyButton.moveButton((int) (e.getGraphics().getOriginalWidth() - MoneyButton.getSizeX() / 2), (int) ( MoneyButton.getSizeY() / 2));
        //BackButton
        BackButtonImage = e.getGraphics().newImage("BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);
        BackButton.moveButton((int) ( BackButton.getSizeX() / 2), (int) ( BackButton.getSizeY() / 2));

    }

    @Override
    public void start() {}

    @Override
    public void update(double deltaTime) {}


    @Override
    public void render(AGraphics graphics) {
        //Background
        graphics.setColor(0XFFFFC874);
        graphics.fillRect(0, 0, graphics.getOriginalWidth(), graphics.getOriginalHeight());
        //Title
        if (title != null) {
            String word;
            graphics.setFont(title, 20);
            word = "Selecciona Categoría";
            graphics.setColor(0XFF000000);
            graphics.drawText(word, graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.25));
        }
        //Buttons
        if (EasyButton != null)
            EasyButton.render(graphics);
        if (MediumButton != null)
            MediumButton.render(graphics);
        if (ForestButton != null)
            ForestButton.render(graphics);
        if (DesertButton != null)
            DesertButton.render(graphics);
        if (MoneyButton != null)
            MoneyButton.render(graphics);
        if (BackButton != null)
            BackButton.render(graphics);
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

                if (EasyButton.click(o.x, o.y)) {
                    EasyLevelSelectionState st = new EasyLevelSelectionState();
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                if (MediumButton.click(o.x, o.y)) {

                }
                if (ForestButton.click(o.x, o.y)) {

                }
                if (DesertButton.click(o.x, o.y)) {

                }
                if (MoneyButton.click(o.x, o.y)) {
                    ShopState st = new ShopState();
                    st.setPrevious(this);
                    engine.setState(st);
                    st.init(engine);
                }
                if (BackButton.click(o.x, o.y)) {
                    engine.setState(previous);
                }
            }

            engine.getInput().emptyTouchEvents();
        }
    }

}

