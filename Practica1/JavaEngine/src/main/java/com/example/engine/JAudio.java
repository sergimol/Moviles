package com.example.engine;

import com.example.interfaces.IAudio;
import com.example.interfaces.ISound;

import java.io.IOException;
import java.util.HashMap;

public class JAudio implements IAudio {

    private String path = "DesktopGame/assets/";

    //HasMap para cargar los sonidos y buscarlos por su nombre
    HashMap<String, ISound> samplesLibrary;

    public JAudio() {
        samplesLibrary = new HashMap<>();                       //Inicializa el HashMap para empezar a meter samples
    }

    @Override
    public ISound newSound(String sampleName,String fileName) {
        JSound sample = new JSound(path + fileName);    //Creamos el sample
        samplesLibrary.put(sampleName, sample);                   //Lo anadimos a la libreria
        return sample;
    }

    @Override
    public void playSound(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).play();
        }
    }

    @Override
    public void pause(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).pause();
        }
    }

    @Override
    public void resume(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).resume();
        }
    }

    @Override
    public void stop(String fileName) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).stop();
        }
    }

    @Override
    public void loop(String fileName, boolean loopCondition) {
        if (samplesLibrary != null && samplesLibrary.get(fileName) != null) {
            samplesLibrary.get(fileName).loop(loopCondition);
        }
    }
}
