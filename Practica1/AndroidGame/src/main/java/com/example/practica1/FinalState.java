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
                    engine.setState(previous.getprevious());
                }
                else if(TweetButton.click(o.x, o.y)){
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Tremendo nivel me acabo de pasar en el nanogramos\uD83D\uDE0E");
                    sendIntent.setType("text/plain");

                    /*Bitmap image = engine.getGraphics().viewToBitmap();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    File f = new File(Environment.getExternalStorageDirectory() + File.separator + "canvas_temp.jpg");
                    try {
                        f.createNewFile();
                        FileOutputStream fo = new FileOutputStream(f);
                        fo.write(bytes.toByteArray());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(engine.getContext(), engine.getContext().getApplicationContext().getPackageName() + ".provider", f);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    sendIntent.setType("image/jpeg");*/
                    sendIntent.setPackage("com.twitter.android");
                    engine.getContext().startActivity(sendIntent);
                }
            }
        }
        engine.getInput().emptyTouchEvents();
    }
}

