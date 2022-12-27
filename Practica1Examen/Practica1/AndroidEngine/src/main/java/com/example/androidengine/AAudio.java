package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
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
    //SoundPool soundPool;


    public AAudio(AssetManager am) {
        assetManager = am;
        samplesLibrary = new HashMap<>();                       //Inicializa el HashMap para empezar a meter samples
        //soundPool = new SoundPool.Builder().setMaxStreams(5).build();
    }

    @Override
    public ISound newSound(String sampleName, String fileName) {
        ASound sample = null;    //Creamos el sample
        try {
            sample = new ASound(fileName, assetManager);
            samplesLibrary.put(sampleName, sample);                   //Lo anadimos a la libreria

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sample;

//      AssetFileDescriptor assetDecriptor = assetManager.openFd(fileName);
//      int soundId = -1;
//      soundId = soundPool.load(assetDecriptor, 1);
//      sample = mediaPlayer.setDataSource(assetDecriptor.getFileDescriptor(), assetDecriptor.getStartOffset(), assetDecriptor.getLength());
//      samplesLibrary.put(sampleName, sample);                   //Lo anadimos a la libreria

    }

    @Override
    public void playSound(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).play();
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
