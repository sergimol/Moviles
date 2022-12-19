package com.example.practica1;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.ATimer;
import com.example.androidengine.State;

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

    int wrongCount, missingCount;
    boolean showingWrong = false;
    ATimer timer;

    public GameState(int x, int y, Bundle savedData) {
        vidas = new Lives();


        if (savedData != null) {
            board = new Board();
            vidas.load(savedData);
            board.load(savedData);
        } else {
            board = new Board(x, y);
        }

        //vidas = (Lives) savedData.getSerializable("corazones");


    }

    public GameState(AssetManager assets, String level) throws IOException {
        board = new Board(assets, level);
        vidas = new Lives();
    }

    @Override
    public void init(AEngine e) {
        super.init(e);
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

        timer = e.getTimer();


        //setteo de los corazones, que se podria poner mas bonito pero me gusta factorizar todo como la foctoiria de embutidos de mi pueblo dios que buenos estan
        vidas.setHeart(e.getGraphics().newImage(engine.getStyle() + "HeartImageFull.png"));
        vidas.setContainer(e.getGraphics().newImage(engine.getStyle() + "HeartImage.png"));
        vidas.setSpacing(e.getGraphics().getCanvasAspectRelationWidth() * 0.10f);
        vidas.setPos(e.getGraphics().getCanvasAspectRelationWidth() * 0.10f, e.getGraphics().getCanvasAspectRelationHeight() * 0.90f);
        vidas.setSize(e.getGraphics().getCanvasAspectRelationHeight() * 0.15f, e.getGraphics().getCanvasAspectRelationHeight() * 0.15f);

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

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {
            } else if (o.type == AInput.InputTouchType.TOUCH_MOVE) {
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                board.handleInput(xInCanvas, yInCanvas, engine, false);
                //da igual el output
            } else if (o.type == AInput.InputTouchType.TOUCH_UP) {
                float xInCanvas = o.x;
                float yInCanvas = o.y;
                //comprobar que esa casilla es correcta y devolver un true or false para restar la vida
                if (!board.handleInput(xInCanvas, yInCanvas, engine, true)) {
                    //en el caso de ser un error
                    vidas.subtractLife();
                    if (vidas.getHearts() <= 0) {
                        engine.setState(previous);
                    }
                    System.out.println("vidas restantes: " + vidas.getHearts());
                }
                board.resetAllowChangeStatesCells();
            }

            if (o.type == AInput.InputTouchType.TOUCH_DOWN) {

                //FUNCIONALIDAD BOTON RENDIRSE
                if (SurrenderButton.click(o.x, o.y)) {
                    engine.setState(previous);
                }
                //FUNCIONALIDAD BOTON COMPROBAR
                if (CheckButton.click(o.x, o.y)) {
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
            }
        }
        engine.getInput().emptyTouchEvents();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SceneType", 2);
        outState.putInt("x", board.getxSize());
        outState.putInt("y", board.getySize());

        vidas.save(outState);
        board.save(outState);
        //outState.putSerializable("corazones", vidas);
        //vidas.metodoQueSerializa();
    }
}
