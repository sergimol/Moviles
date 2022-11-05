package com.example.engine;

import com.example.interfaces.ISound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class JSound implements ISound {

    Clip clip;
    AudioInputStream audioStream;

    public JSound(String fileName) {
        try {
            File audioFile = new File(fileName);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Seniorito el audio no funsiona :(");
            e.printStackTrace();
        }

//        try {
//            this.clip = AudioSystem.getClip();          //Inicializacion del clip
//            this.clip.open(audioStream);                //Apertura del flujo de audio del clip(?)
//            this.clip.loop(Clip.LOOP_CONTINUOUSLY);     //Setear el numero de vueltas. 0 reproduce 1 vez, 1 reproduce 2 veces...
//            this.clip.setFramePosition(0);              //Setear el audio al principio
//            this.clip.start();                          //Playea el clip
//            if(this.clip.isRunning())                   //Si
//                this.clip.stop();
//
//        } catch (Exception e) {
//            System.err.println("Seniorito el audio no funsiona :(");
//            e.printStackTrace();
//        }
    }

    @Override
    public void play() {
        this.clip.setFramePosition(0);              //Setear el audio al principio
        this.clip.start();                          //Playea el clip
    }

    @Override
    public void pause() {
        this.clip.stop();
    }

    @Override
    public void resume() {
        this.clip.start();
    }

    @Override
    public void stop() {
        this.clip.stop();
        this.clip.setFramePosition(0);              //Setear el audio al principio
    }

    @Override
    public void loop(boolean loopCondition) {
        if (loopCondition)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            clip.loop(0);
        //Si queremos implementar que algo se playee x veces habria que cambiar lo del booleano pero no nos va a hacer falta

    }
}
