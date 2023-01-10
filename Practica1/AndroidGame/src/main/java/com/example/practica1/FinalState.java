package com.example.practica1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;
import com.example.androidengine.AImage;
import com.example.androidengine.AInput;
import com.example.androidengine.State;

//import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class FinalState extends State {
    AFont title;
    Board board;

    Button BackButton;
    AImage BackButtonImage;

    Button TweetButton;
    AImage TweetButtonImage;

    GameManager manager;

    public void SetManager(GameManager m){
        manager = m;
    }

    public FinalState(Board b, GameManager m){
        manager = m;
        board = b;
    }

    @Override
    public void init(AEngine e) {
        engine = e;
        title = e.getGraphics().newFont("CuteEasterFont.ttf", 1, (int) (0.4f * (e.getGraphics().relationAspectDimension() / 10) / e.getGraphics().getScale()));
        //BackButton
        BackButtonImage = e.getGraphics().newImage(manager.getStyle() + "BackButton.png");
        BackButton = new Button(BackButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f,true);
        BackButton.moveButton((int) (BackButton.getSizeX() / 2), (int) (BackButton.getSizeY() / 2));

        TweetButtonImage = e.getGraphics().newImage(manager.getStyle() + "TweetButton.png");
        TweetButton = new Button(TweetButtonImage, 0, 0, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f, e.getGraphics().getCanvasAspectRelationWidth() * 0.15f,true);
        TweetButton.moveButton((int) (TweetButton.getSizeX() / 2), (int) (e.getGraphics().getOriginalHeight() - TweetButton.getSizeY() / 2));
        engine.getAudioFX().playSound("tada");

        manager.addMoney(10);


    }

    @Override
    public void render(AGraphics graphics) {
        if (BackButton != null) {
            if (!BackButton.getImagen().getName().equals(manager.getStyle() + "BackButton.png"))
                BackButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "BackButton.png"));
            BackButton.render(graphics);
        }
        if (TweetButton != null) {
            if (!TweetButton.getImagen().getName().equals(manager.getStyle() + "TweetButton.png"))
                TweetButton.changeImage(engine.getGraphics().newImage(manager.getStyle() + "TweetButton.png"));
            TweetButton.render(graphics);
        }

        String word;
        graphics.setColor(0xFF000000);
        graphics.setFont(title, 20);
        word = "ENHORABUENA";
        graphics.drawText(word, (int) graphics.getOriginalWidth() / 2 - graphics.getFontWidth(word) / 2, (int) (graphics.getOriginalHeight() * 0.1));

        board.renderWin(graphics);
    }

    @Override
    public void handleInput() {
        List<AInput.TouchEvent> events = engine.getInput().getTouchEvents();
        ListIterator<AInput.TouchEvent> i = events.listIterator();
        while (i.hasNext()) {
            AInput.TouchEvent o = i.next();
            if ((o.type == AInput.InputTouchType.TOUCH_DOWN)) {

                //FUNCIONALIDAD BOTON VOLVER
                if (BackButton.click(o.x, o.y)) {
                    previous.getprevious().init(engine);
                    engine.setState(previous.getprevious());
                }
                else if(TweetButton.click(o.x, o.y)){
                    String tweetUrl = "https://twitter.com/intent/tweet?text=Tremendo nivel me acabo de pasar en el nanogramos\uD83D\uDE0E";
                    Uri uri = Uri.parse(tweetUrl);
                    engine.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }
}

