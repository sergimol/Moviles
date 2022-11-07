package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.interfaces.IAudio;
import com.example.interfaces.ISound;

import java.io.IOException;
import java.util.HashMap;

public class AAudio implements IAudio {

    //HasMap para cargar los sonidos y buscarlos por su nombre
    HashMap<String, ISound> samplesLibrary;

    AssetManager assetManager;
    SoundPool soundPool;


    public AAudio(AssetManager am) {
        assetManager = am;
        samplesLibrary = new HashMap<>();                       //Inicializa el HashMap para empezar a meter samples
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
    }

    @Override
    public ISound newSound(String sampleName, String fileName) {
        ASound sample = null;    //Creamos el sample
        try {
            int soundId = -1;
            AssetFileDescriptor assetDecriptor = assetManager.openFd(fileName);
            soundId = soundPool.load(assetDecriptor, 1);

            sample = new ASound(sampleName, soundId);
            samplesLibrary.put(sampleName, sample);                   //Lo anadimos a la libreria
        } catch (IOException e) {
            System.err.println("Seniorito el audioAndroid no funsiona :(");
            e.printStackTrace();
        }
        return sample;
    }

    @Override
    public void playSound(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            soundPool.play(((ASound) samplesLibrary.get(fileName)).getSoundId(), 1, 1, 1, 1, 1);
        }
    }

    @Override
    public void pause(String fileName) {

    }

    @Override
    public void resume(String fileName) {

    }

    @Override
    public void stop(String fileName) {

    }

    @Override
    public void loop(String fileName, boolean loopCondition) {

    }
}
