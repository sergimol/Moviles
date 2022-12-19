package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.util.HashMap;

public class AAudio {

    //HasMap para cargar los sonidos y buscarlos por su nombre
    HashMap<String, ASound> samplesLibrary;

    AssetManager assetManager;
    boolean AudioSystem;

    public AAudio(AssetManager am, boolean audio) {
        assetManager = am;
        samplesLibrary = new HashMap<>();                       //Inicializa el HashMap para empezar a meter samples
        AudioSystem = audio;
    }

    public ASound newSound(String sampleName, String fileName) {
        ASound sample = null;    //Creamos el sample
        try {
            sample = new ASound(fileName, assetManager, AudioSystem);
            samplesLibrary.put(sampleName, sample);                   //Lo anadimos a la libreria

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sample;


    }

    public void playSound(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).play();
        }
    }

    public void pause(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).pause();
        }
    }

    public void resume(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).resume();
        }
    }

    public void stop(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).stop();
        }
    }

    public void loop(String fileName, boolean loopCondition) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).loop(loopCondition);
        }
    }
}
