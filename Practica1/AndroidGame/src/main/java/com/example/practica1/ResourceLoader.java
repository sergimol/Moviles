package com.example.practica1;


import com.example.androidengine.AEngine;

public class ResourceLoader {
    public void loadResources(AEngine e){
        e.getAudioFX().newSound("pop", "pop.wav");
        e.getAudioFX().newSound("tada", "tada.wav");
        e.getAudioMusic().newSound("music", "music.wav");
    }
}